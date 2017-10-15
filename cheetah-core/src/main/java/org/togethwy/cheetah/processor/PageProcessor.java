package org.togethwy.cheetah.processor;

import org.togethwy.cheetah.CheetahResult;
import org.togethwy.cheetah.Result;
import org.togethwy.cheetah.SiteConfig;
import org.togethwy.cheetah.downloader.DownloadResult;
import org.togethwy.cheetah.downloader.JsonDataResult;
import org.togethwy.cheetah.downloader.Page;
import org.togethwy.cheetah.downloader.Request;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 处理页面接口，实现此接口完成爬虫
 *
 * @author wangtonghe
 * @date 2017/7/3 09:18
 */
public interface PageProcessor {

    /**
     * 处理页面过程
     * @param page 页面数据
     */
    void process(Page page, CheetahResult result);

    /**
     * 配置并获取页面
     */
    SiteConfig setAndGetSiteConfig();

    /**
     * 处理由API获取到的Json数据
     *
     * @param jsonData
     */
    default void processJSON(JsonDataResult jsonData,CheetahResult cheetahResult){

    }

    default Request updateJSONConfig(CheetahResult cheetahResult,SiteConfig siteConfig){
        return null;
    }




}
