package org.togethwy.cheetah.downloader;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求类
 * @author wangtonghe
 * @date 2017/7/8 15:57
 */
public class Request {

    public Request(String url) {
        this.url = url;
    }

    /**
     * 请求url
     */
    private String url;



    /**
     * 请求cookie
     */
    private Map<String,String> cookies = new HashMap<>();

    /**
     * 请求header
     */
    private Map<String,String> header = new HashMap<>();


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }
}
