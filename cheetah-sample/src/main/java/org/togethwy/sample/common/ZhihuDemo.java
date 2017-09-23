package org.togethwy.sample.common;

import org.togethwy.cheetah.Cheetah;
import org.togethwy.cheetah.SiteConfig;
import org.togethwy.cheetah.downloader.Page;
import org.togethwy.cheetah.handler.ConsoleHandler;
import org.togethwy.cheetah.processor.PageProcessor;
import org.togethwy.cheetah.selector.Html;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangtonghe
 * @date 2017/9/23 18:57
 */
public class ZhihuDemo implements PageProcessor {

    private SiteConfig siteConfig = SiteConfig.create();

    @Override
    public void process(Page page) {

        Map<String, Object> result = new HashMap<>();

        Html main = page.getHtml().$(".Profile-mainColumn");

        Html listInfo = main.$(".ProfileMain-header ul li");
        result.put("name", page.getHtml().$(".ProfileHeader-title .ProfileHeader-name").getValue());
        result.put("answerNum", listInfo.get(1).$(".Tabs-meta").getValue());
        result.put("quesNum", listInfo.get(2).$(".Tabs-meta").getValue());
        result.put("article", listInfo.get(3).$(".Tabs-meta").getValue());

        Html sideInfo = page.getHtml().$(".Profile-main .Profile-sideColumn");


        Html follow = sideInfo.$(".FollowshipCard .FollowshipCard-counts a");

        result.put("following", follow.get(0).$(".NumberBoard-value").getValue());
        result.put("follower", follow.get(1).$(".NumberBoard-value").getValue());
        result.put("siteUrl", page.getUrl());

        page.addResult(result);


        List<String> links =  main.$(".List > div").get(1).$(".List-item .Popover").getLinks();

        links.forEach(link -> {
            String newLink = link + "/followers";
            page.addWaitRequest(newLink);
        });


    }

    @Override
    public SiteConfig setAndGetSiteConfig() {
        this.siteConfig.setDomain("https://www.zhihu.com")
                .setStartUrl("https://www.zhihu.com/people/zhang-jia-wei/followers")
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
        Cheetah.create(new ZhihuDemo()).setHandler(new ConsoleHandler()).run();
    }
}
