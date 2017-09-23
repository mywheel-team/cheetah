package org.togethwy.cheetah.downloader;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
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
public class JsonDownloader implements Downloader{

    private  Logger logger = LoggerFactory.getLogger(JsonDownloader.class);


    @Override
    public JsonDataResult download(Request request, SiteConfig siteConfig) {
        String url = request.getUrl();
        logger.debug("download url is :{}", url);
        List<Map<String, String>> list = new ArrayList<>();
        CloseableHttpResponse response = null;
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setUserAgent(siteConfig.getUserAgent());
        CloseableHttpClient httpClient = httpClientBuilder.build();
        HttpGet httpGet = new HttpGet(url);
        JsonDataResult downloadResult = new JsonDataResult();
        try {
            response = httpClient.execute(httpGet);
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
}
