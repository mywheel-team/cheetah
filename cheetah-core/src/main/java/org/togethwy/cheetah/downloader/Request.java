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

    public Request(String url,Map<String,String> paramMap){
        this.url = url;
        this.paramMap = paramMap;
    }

    public Request( String url, Map<String, String> paramMap,RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
        this.url = url;
        this.paramMap = paramMap;
    }

    public Request(Request request){
        this.requestMethod = request.getRequestMethod();
        this.url = request.getUrl();
        this.paramMap = request.getParamMap();
    }

    /**
     * 请求方法
     */
    private RequestMethod requestMethod;

    /**
     * 请求url
     */
    private String url;

    /**
     * 请求参数
     */
    private Map<String,String> paramMap;

    /**
     * 请求cookie
     */
    private Map<String,String> cookies = new HashMap<>();

    /**
     * 请求header
     */
    private Map<String,String> header = new HashMap<>();

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

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
