package org.togethwy.cheetah.processor;

import org.togethwy.cheetah.downloader.JsonDataResult;

/**
 * json API格式处理器
 *
 * @author wangtonghe
 * @date 2017/9/23 17:48
 */
public interface JsonProcessor {

    /**
     * 处理由API获取到的Json数据
     *
     * @param jsonData
     */
    void processJSON(JsonDataResult jsonData);

    /**
     * 更新jsonUrl
     *
     * @param jsonUrl
     */
    default void updateJsonUrl(String jsonUrl){

    }

}
