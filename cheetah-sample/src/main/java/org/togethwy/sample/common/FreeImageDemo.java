package org.togethwy.sample.common;

import org.togethwy.cheetah.Cheetah;
import org.togethwy.cheetah.CheetahResult;
import org.togethwy.cheetah.SiteConfig;
import org.togethwy.cheetah.downloader.Page;
import org.togethwy.cheetah.handler.ConsoleHandler;
import org.togethwy.cheetah.handler.FileDownloadHandler;
import org.togethwy.cheetah.processor.PageProcessor;

import java.util.List;

/**
 * 免费图片网站 http://cn.freeimages.com 爬虫demo
 * @author wangtonghe
 * @date 2017/10/12 21:11
 */
public class FreeImageDemo implements PageProcessor {

    private SiteConfig siteConfig = SiteConfig.create();


    @Override
    public void process(Page page, CheetahResult cheetahResult) {
        List<String> nextUrls = page.getHtml().$("#content .homepage-tags").get(0).getLinks();
        cheetahResult.addWaitRequest(nextUrls);

        List<String> fileUrls = page.getHtml().$("#content .listing-primary .listing-data ul").getImgUrls();
        fileUrls.forEach(url->{
            cheetahResult.putFileResult(url.replace("small","large"));
        });

        List<String> nextPage = page.getHtml().$("#content .listing-primary .pagination").getLinks();
        cheetahResult.addWaitRequest(nextPage);

    }

    @Override
    public SiteConfig setAndGetSiteConfig() {
        this.siteConfig.setDomain("http://cn.freeimages.com")
                .setStartUrl("http://cn.freeimages.com")
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Accept-Encoding", "gzip, deflate, sdch, br")
                .addHeader("Accept-Language", "zh-CN, zh; q=0.8, en; q=0.6")
                .setThreadSleep(2000)
                .setThreadNum(3)
                .setStartJSONAPI(false);
        return siteConfig;
    }

    public static void main(String[] args) {
        Cheetah.create(new FreeImageDemo())
                .setHandler(new FileDownloadHandler("/Users/wangtonghe/workspace/data/java/cheeath/freeimage"))
                .setHandler(new ConsoleHandler())
                .run();
    }
}
