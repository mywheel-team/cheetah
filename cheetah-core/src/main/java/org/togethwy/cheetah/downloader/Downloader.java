package org.togethwy.cheetah.downloader;

import org.togethwy.cheetah.SiteConfig;

/**
 * @author wangtonghe
 * @date 2017/7/8 13:53
 */
public interface Downloader {

    /**
     * 下载页面
     *
     * @param request    页面请求
     * @param siteConfig 下载全局配置及信息
     * @return
     */
    DownloadResult download(Request request, SiteConfig siteConfig);
}
