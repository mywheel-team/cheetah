package org.togethwy.cheetah.handler;

import org.togethwy.cheetah.Result;
import org.togethwy.cheetah.util.FileUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * 文件下载处理器
 * @author wangtonghe
 * @date 2017/10/12 21:23
 */
public class FileDownloadHandler implements Handler{
    private String path;

    private static String HTTP_PREFIX = "http:";

    private String domain;


    public FileDownloadHandler(String path) {
        this.path = path;
    }

    //TODO 文件下载处理添加cookie等
    @Override
    public void handle(List<Result> results) {

        results.forEach((result -> {
            Set<String> originUrls = result.getFileResults();

            Set<String> newUrls = new HashSet<>();
            originUrls.forEach(url -> newUrls.add(doOriginUrl(url,domain)));

            FileUtils.batchDownloadFile(newUrls, path);
        }));
    }

    @Override
    public void destory() {

    }

    @Override
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * 处理源路径
     *
     * @param originUrl
     * @return
     */
    private static String doOriginUrl(String originUrl,String domain) {
        if (originUrl.startsWith("http")) {
            return originUrl;
        } else if (originUrl.startsWith("//")) {
            return HTTP_PREFIX + originUrl;
        }else if(originUrl.startsWith("/")){
            return domain+originUrl;
        }
        return null;


    }
}
