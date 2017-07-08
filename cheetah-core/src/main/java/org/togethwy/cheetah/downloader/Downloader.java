package org.togethwy.cheetah.downloader;

import org.togethwy.cheetah.Config;

/**
 * @author wangtonghe
 * @date 2017/7/8 13:53
 */
public interface Downloader {

    /**
     * 下载页面
     * @param request 页面请求
     * @param config 下载全局配置及信息
     * @return
     */
    Page download(Request request,Config config);
}
