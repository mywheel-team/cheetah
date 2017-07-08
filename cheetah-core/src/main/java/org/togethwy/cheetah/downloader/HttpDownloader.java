package org.togethwy.cheetah.downloader;


import org.togethwy.cheetah.Config;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Http下载器
 *
 * @author wangtonghe
 * @date 2017/7/8 20:08
 */
public class HttpDownloader implements Downloader {

    private Logger logger = LoggerFactory.getLogger(HttpDownloader.class);


    /**
     * 下载文件简易实现
     * //TODO 添加cookie及header、代理
     * @param request 页面请求
     * @param config 下载全局配置及信息
     * @return
     */
    @Override
    public Page download(Request request, Config config) {
        String url = request.getUrl();
        logger.debug("download url is :{}", url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        Page page = new Page();
        try {
            response = httpClient.execute(httpGet);
            page.setStatusCode(response.getStatusLine().getStatusCode());
            page.setUrl(url);
            page.setRawHtml(EntityUtils.toString(response.getEntity(), config.getCharset()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                EntityUtils.consumeQuietly(response.getEntity());
            }
        }
        return page;
    }
}
