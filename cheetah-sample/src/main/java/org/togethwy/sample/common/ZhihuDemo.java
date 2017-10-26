package org.togethwy.sample.common;

import org.togethwy.cheetah.Cheetah;
import org.togethwy.cheetah.CheetahResult;
import org.togethwy.cheetah.SiteConfig;
import org.togethwy.cheetah.downloader.Page;
import org.togethwy.cheetah.handler.ConsoleHandler;
import org.togethwy.cheetah.handler.ElasticHandler;
import org.togethwy.cheetah.handler.RedisHandler;
import org.togethwy.cheetah.processor.PageProcessor;
import org.togethwy.cheetah.selector.Selectable;
import org.togethwy.cheetah.util.StringUtils;

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
    public void process(Page page, CheetahResult cheetahResult) {


        Selectable main = page.getHtml().$(".Profile-mainColumn");

        Selectable listInfo = main.$(".ProfileMain-header ul li");

        String name = page.getHtml().$(".ProfileHeader-title .ProfileHeader-name").getValue();
        if (StringUtils.isEmpty(name)) {
            cheetahResult.setSkip(true);
            return;
        }
        Map<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("answerNum", listInfo.get(1).$(".Tabs-meta").getValue());
        result.put("quesNum", listInfo.get(2).$(".Tabs-meta").getValue());
        result.put("article", listInfo.get(3).$(".Tabs-meta").getValue());

        Selectable sideInfo = page.getHtml().$(".Profile-main .Profile-sideColumn");


        Selectable follow = sideInfo.$(".FollowshipCard .FollowshipCard-counts a");

        int followNum = Integer.parseInt(follow.get(0).$(".NumberBoard-value").getValue());
        int followerNum =  Integer.parseInt(follow.get(1).$(".NumberBoard-value").getValue());

        result.put("following", followNum);
        result.put("follower", followerNum);
        result.put("siteUrl", page.getUrl());

        cheetahResult.putResult(result);


        List<String> links = main.$(".List > div").get(1).$(".List-item .ContentItem-head .Popover").getLinks();
        String curPage = page.getUrl();
        if(curPage.contains("?page=")){
            String pageNum =curPage.substring(curPage.lastIndexOf("?page=")+6);
            String url = curPage.replace(pageNum, String.valueOf(Integer.parseInt(pageNum)+1));
            cheetahResult.addWaitRequest(url);
            cheetahResult.setSkip(true);
        }else{
            if(followerNum>0){
                cheetahResult.addWaitRequest(curPage+"?page=2");
            }
        }


        links.forEach(link -> {
            String newLink = link + "/followers";
            cheetahResult.addWaitRequest(newLink);
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
                .openBreakRestart(true)
                .setBreakRedisConfig("127.0.0.1");
        return siteConfig;
    }

    @Override
    public void plan() {
        Cheetah.create(new ZhihuDemo())
                .setHandler(new ConsoleHandler())
                .setHandler(new ElasticHandler("127.0.0.1", 9300, "wth-elastic", "zhihu_new", "user_data"))
                .setHandler(new RedisHandler("127.0.0.1","zhihu_new"))
                .run();
    }


    public static void main(String[] args) {

        Cheetah.create(new ZhihuDemo())
                .setHandler(new ConsoleHandler())
//                .setHandler(new ElasticHandler("127.0.0.1", 9300, "wth-elastic", "zhihu_new", "user_data"))
//                .setHandler(new RedisHandler("127.0.0.1","zhihu_new"))
                .run();

    }

}
