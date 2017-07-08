package com.wthfeng.cheetah;

import java.util.HashMap;
import java.util.Map;

/**
 * 爬虫配置类
 *
 * @author wangtonghe
 * @date 2017/7/8 16:55
 */
public class Config {

    /**
     * 域名
     */
    private String domain;

    /**
     * userAgent
     */
    private String userAgent;

    /**
     * charset
     */
    private String charset;

    /**
     * cookie
     */
    private Map<String,String> cookies = new HashMap<>();

    /**
     * header
     */
    private Map<String,String> headers = new HashMap<>();

    /**
     * startUrl
     */
    private String startUrl;

    /**
     * 线程休眠时间，单位毫秒
     */
    private int threadSleep;

    /**
     * 开启线程个数
     */
    private int threadNum;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Config setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }


    public String getStartUrl() {
        return startUrl;
    }

    public Config setStartUrl(String startUrl) {
        this.startUrl = startUrl;
        return this;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public Config setCookies(Map<String,String> cookies) {
        this.cookies = cookies;
        return this;
    }

    public Config addCookie(String key,String value){
        this.cookies.put(key,value);
        return this;
    }

    public Config addHeader(String key ,String value){
        this.headers.put(key,value);
        return this;
    }

    public String getDomain() {
        return domain;
    }

    public Config setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Config setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public Config setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public int getThreadSleep() {
        return threadSleep;
    }

    public Config setThreadSleep(int threadSleep) {
        this.threadSleep = threadSleep;
        return this;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public Config setThreadNum(int threadNum) {
        this.threadNum = threadNum;
        return this;
    }
}
