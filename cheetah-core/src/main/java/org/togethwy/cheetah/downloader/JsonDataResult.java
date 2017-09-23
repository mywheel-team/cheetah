package org.togethwy.cheetah.downloader;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.togethwy.cheetah.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * json格式数据
 *
 * @author wangtonghe
 * @date 2017/9/23 13:54
 */
public class JsonDataResult extends DownloadResult {

    private String jsonUrl;

    public String getJsonUrl() {
        return jsonUrl;
    }

    public void setJsonUrl(String jsonUrl) {
        this.jsonUrl = jsonUrl;
    }

    private final static String DEFAULT_DATA_FIELD_NAME = "data";

    private Logger logger = LoggerFactory.getLogger(HttpDownloader.class);


    public Map<String, Object> parseMap() {
        Map<String, Object> result = new HashMap<>();
        try {
            result = JSON.parseObject(rawText);
        } catch (Exception e) {
            logger.error("json格式转化错误", e);
        }
        return result;

    }

    public List<Map> parseList() {
        List<Map> result = new ArrayList<>();
        try {
            result = JSON.parseArray(rawText, Map.class);
        } catch (Exception e) {
            logger.error("json格式转化错误", e);
        }
        return result;

    }

    /**
     * 解析json数据
     * 用于解析`{'data':[]}`格式json数据
     *
     * @param dataField
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> parseListFromMap(String dataField) {
        List<Map<String, Object>> retMap = new ArrayList<>();
        try {
            Map<String, Object> jsonMap = JSON.parseObject(rawText);
            retMap = (List<Map<String, Object>>) jsonMap.get(dataField);
        } catch (Exception e) {
            logger.error("json格式转化错误", e);
        }
        return retMap;
    }

    public List<Map<String, Object>> parseListFromMap() {
        return parseListFromMap(DEFAULT_DATA_FIELD_NAME);
    }


    @Override
    public void addWaitRequest(String ... requests) {
        for (String s : requests) {
            if (StringUtils.isEmpty(s) || s.equals("#") || s.startsWith("javascript:")) {
                break;
            }
            this.waitRequests.add(new Request(s));
        }
    }
}
