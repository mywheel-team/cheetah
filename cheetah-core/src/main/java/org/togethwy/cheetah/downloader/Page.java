package org.togethwy.cheetah.downloader;

import org.togethwy.cheetah.selector.CheetahNodes;

/**
 * 下载的html页面格式的结果类
 *
 * @author wangtonghe
 * @date 2017/7/8 16:14
 */
public class Page extends DownloadResult {


    private CheetahNodes html;

    public CheetahNodes getHtml() {
        if (getRawText() == null) {
            return null;
        }
        html = new CheetahNodes(getRawText());
        return html;
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


}
