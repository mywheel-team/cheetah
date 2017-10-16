package org.togethwy.cheetah.downloader;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.togethwy.cheetah.SiteConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wangtonghe
 * @date 2017/9/23 11:54
 */
public class JsonDownloader implements Downloader {

    private Logger logger = LoggerFactory.getLogger(JsonDownloader.class);


    @Override
    public JsonDataResult download(Request request, SiteConfig siteConfig) {

        List<Map<String, String>> list = new ArrayList<>();
        CloseableHttpResponse response = null;
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setUserAgent(siteConfig.getUserAgent());
        CloseableHttpClient httpClient = httpClientBuilder.build();

        JsonDataResult downloadResult = new JsonDataResult();
        try {
            if (request.getRequestMethod() == RequestMethod.GET) {  //get请求
                response = handleGet(httpClient, request);
            } else if (request.getRequestMethod() == RequestMethod.POST) {
                response = handlePost(httpClient, request);
            }
            if (response == null) {
                return null;
            }
            String jsonData = EntityUtils.toString(response.getEntity());
            downloadResult.setRawText(jsonData);
            downloadResult.setUrl(request.getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                EntityUtils.consumeQuietly(response.getEntity());
            }
        }
        return downloadResult;
    }


    private CloseableHttpResponse handleGet(CloseableHttpClient httpClient, Request request) throws IOException {
        String url = request.getUrl();
        logger.debug("download url is :{}", url);
        HttpGet httpMethod = new HttpGet(url);

        return httpClient.execute(httpMethod);

    }

    private CloseableHttpResponse handlePost(CloseableHttpClient httpClient, Request request) throws IOException {
        String url = request.getUrl();
        logger.debug("download url is :{}", url);
        HttpPost httpPost = new HttpPost(url);

        //装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Map<String,String> map = request.getParamMap();
        if(map!=null){
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        return httpClient.execute(httpPost);


    }
}
