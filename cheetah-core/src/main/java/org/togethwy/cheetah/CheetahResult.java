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

    CheetahResult(String currentUrl){
        this.currentUrl= currentUrl;
    }

    private String currentUrl;

    private List<Map<String, Object>> results = new ArrayList<>();

    private List<String> fileResults = new ArrayList<>();

    private Set<Request> waitRequests = new HashSet<>();


    public void addWaitRequest(List<String> urls) {
        for (String s : urls) {
            if (StringUtils.isEmpty(s) || s.equals("#") || s.startsWith("javascript:")) {
                break;
            }
            s = UrlUtils.canonicalizeUrl(s, currentUrl);
            this.waitRequests.add(new Request(s));
        }
    }

    public void addWaitRequest(String request) {
        List<String> requests = new ArrayList<>();
        requests.add(request);
        addWaitRequest(requests);
    }

    public Set<Request> getWaitRequests() {
        return waitRequests;
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


}
