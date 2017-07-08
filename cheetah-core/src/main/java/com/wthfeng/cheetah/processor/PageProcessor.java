package com.wthfeng.cheetah.processor;

import com.wthfeng.cheetah.Config;
import com.wthfeng.cheetah.downloader.Page;

/**
 * 处理页面接口，实现此接口完成爬虫
 *
 * @author wangtonghe
 * @date 2017/7/3 09:18
 */
public interface PageProcessor {

    /**
     * 处理页面过程
     * @param page
     */
    void process(Page page);

    /**
     * 配置页面
     */
    Config getConfig();


}
