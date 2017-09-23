package org.togethwy.cheetah.downloader;


import org.togethwy.cheetah.SiteConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
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
     * @param request 页面请求
     * @param siteConfig 下载全局配置及信息
     * @return
     */
    @Override
    public DownloadResult download(Request request, SiteConfig siteConfig) {
        String url = request.getUrl();
        logger.debug("download url is :{}", url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClientHelper.getDefaultClient(siteConfig);
        HttpGet httpGet = new HttpGet(url);
        DownloadResult downloadResult = new Page();
        try {
            response = httpClient.execute(httpGet);
            downloadResult.setStatusCode(response.getStatusLine().getStatusCode());
            downloadResult.setUrl(url);
            downloadResult.setRawText(EntityUtils.toString(response.getEntity(), siteConfig.getCharset()));
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
