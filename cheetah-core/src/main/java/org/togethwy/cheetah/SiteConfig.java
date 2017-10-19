package org.togethwy.cheetah;

import org.togethwy.cheetah.downloader.Request;
import org.togethwy.cheetah.downloader.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * 爬虫配置类
 *
 * @author wangtonghe
 * @date 2017/7/8 16:55
 */
public class SiteConfig {


    public static SiteConfig create(){
        return new SiteConfig();
    }

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


    /**
     * json格式API请求地址
     */
    private String jsonAPIUrl;

    /**
     * 是否使用jsonAPI
     */
    private boolean isStartJSONAPI;

    /**
     * 开启中断重启（从上次中断点重新爬）
     */
    private boolean openBreakRestart;


    public boolean isStartJSONAPI() {
        return isStartJSONAPI;
    }

    public SiteConfig setStartJSONAPI(boolean startJSONAPI) {
        isStartJSONAPI = startJSONAPI;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public SiteConfig setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public String getJsonAPIUrl() {
        return jsonAPIUrl;
    }

    public SiteConfig setJsonAPIUrl(String jsonAPIUrl) {
        this.jsonAPIUrl = jsonAPIUrl;
        return this;
    }

    public String getStartUrl() {
        return startUrl;
    }

    public SiteConfig setStartUrl(String startUrl) {
        this.startUrl = startUrl;
        return this;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public SiteConfig setCookies(Map<String,String> cookies) {
        this.cookies = cookies;
        return this;
    }

    public SiteConfig addCookie(String key, String value){
        this.cookies.put(key,value);
        return this;
    }

    public SiteConfig addHeader(String key , String value){
        this.headers.put(key,value);
        return this;
    }

    public String getDomain() {
        return domain;
    }

    public SiteConfig setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public SiteConfig setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public SiteConfig setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public int getThreadSleep() {
        return threadSleep;
    }

    public SiteConfig setThreadSleep(int threadSleep) {
        this.threadSleep = threadSleep;
        return this;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public SiteConfig setThreadNum(int threadNum) {
        this.threadNum = threadNum;
        return this;
    }

    public boolean isBreakRestart() {
        return openBreakRestart;
    }

    public SiteConfig openBreakRestart(boolean openBreakRestart) {
        this.openBreakRestart = openBreakRestart;
        return this;
    }
}
