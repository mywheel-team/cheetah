package org.togethwy.cheetah;

import org.togethwy.cheetah.downloader.Request;
import org.togethwy.cheetah.util.StringUtils;
import org.togethwy.cheetah.util.UrlUtils;

import java.util.*;

/**
 * @author wangtonghe
 * @date 2017/10/15 22:30
 */
public class CheetahResult {

    CheetahResult(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    private String currentUrl;

    /**
     * 是否过滤本次抓取结果
     */
    private boolean isSkip;

    /**
     * 本次抓取结果是否用jsonAPI处理
     */
    private boolean isStartJsonAPI;

    private List<Map<String, Object>> results = new ArrayList<>();

    private List<String> fileResults = new ArrayList<>();

    private Set<Request> waitRequests = new HashSet<>();


    public void addWaitRequest(List<String> urls) {
        for (String s : urls) {
            if (StringUtils.isEmpty(s) || s.equals("#") || s.startsWith("javascript:")) {
                continue;
            }
            s = UrlUtils.canonicalizeUrl(s, currentUrl);
            this.waitRequests.add(new Request(s));
        }
    }

    public boolean isSkip() {
        return isSkip;
    }

    public void setSkip(boolean skip) {
        isSkip = skip;
    }

    public void addWaitRequest(String request) {
        List<String> requests = new ArrayList<>();
        requests.add(request);
        addWaitRequest(requests);
    }

    Set<Request> getWaitRequests() {
        return waitRequests;
    }

    List<String> getWaitRequestsAsString(){
        List<String> waitUrls = new ArrayList<>();
        waitRequests.forEach(request -> waitUrls.add(request.getUrl()));
        return waitUrls;
    }


    public void putFileResult(String fileUrl) {
        fileResults.add(fileUrl);
    }

    public void putFileResults(List<String> fileUrls) {
        fileResults.addAll(fileUrls);
    }

    public void putField(String key, Object value) {
        if (results.size() == 0) {
            results.add(new HashMap<>());
        }
        results.get(0).put(key, value);
    }

    public void putResult(Map<String, Object> result) {
        results.add(result);

    }

    public void putResults(List<Map<String, Object>> results) {
        this.results.addAll(results);
    }

    public List<String> getFileResults() {
        return fileResults;
    }

    public List<Map<String, Object>> getResults() {
        return results;
    }

    public boolean isStartJsonAPI() {
        return isStartJsonAPI;
    }

    public void setStartJsonAPI(boolean startJsonAPI) {
        isStartJsonAPI = startJsonAPI;
    }
}
