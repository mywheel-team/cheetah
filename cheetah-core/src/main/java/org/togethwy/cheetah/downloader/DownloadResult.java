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


    private List<Result> resultList = new ArrayList<>();

    public List<Result> getResults() {
        return resultList;
    }

    public void addResultList(List<Map<String, Object>> results) {
        results.forEach(eachMap -> {
            Result result = new Result();
            result.setResult(eachMap);
            resultList.add(result);
        });
    }

    /**
     * 状态码，标注是否成功
     */
    private int statusCode;

    /**
     * 此次爬获取的url列表
     */
    Set<Request> waitRequests = new HashSet<>();


    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 将获取到的待爬url加入到待爬列表，留给子类实现
     *
     * @param request
     */
    public void addWaitRequest(String request) {
        List<String> requests = new ArrayList<>();
        requests.add(request);
        addWaitRequest(requests);
    }


    public abstract void addWaitRequest(List<String> requests);

    public Set<Request> getWaitRequests() {
        return waitRequests;
    }

    public void addResult(Map<String, Object> retMap) {
        Result result = new Result();
        result.setResult(retMap);
        resultList.add(result);
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
