package org.togethwy.cheetah.downloader;

import org.togethwy.cheetah.Result;

import java.util.*;

/**
 * 下载结果类
 *
 * @author wangtonghe
 * @date 2017/9/23 13:37
 */
public abstract class DownloadResult {


    /**
     * 字符串格式的结果
     */
    String rawText;

    /**
     * 状态码，标注是否成功
     */
    private int statusCode;


    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

}
