package org.togethwy.cheetah.downloader;

import org.togethwy.cheetah.selector.Html;
import org.togethwy.cheetah.util.StringUtils;
import org.togethwy.cheetah.util.UrlUtils;

import java.util.List;

/**
 * 下载的html页面格式的结果类
 * @author wangtonghe
 * @date 2017/7/8 16:14
 */
public class Page extends DownloadResult{


    private Html html;

    public Html getHtml() {
        if(getRawText()==null){
            return null;
        }
        this.html =  new Html(getRawText());
        return this.html;
    }


    /**
     * 当前页面url
     */
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public void addWaitRequest(String... requests){
        //TODO: synchronized  同步锁
        for (String s : requests) {
            if (StringUtils.isEmpty(s) || s.equals("#") || s.startsWith("javascript:")) {
                break;
            }
            s = UrlUtils.canonicalizeUrl(s, getUrl());
            this.waitRequests.add(new Request(s));
        }
    }

}
