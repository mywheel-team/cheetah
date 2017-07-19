package org.togethwy.cheetah.example;

import org.togethwy.cheetah.Config;
import org.togethwy.cheetah.Crawler;
import org.togethwy.cheetah.downloader.Page;
import org.togethwy.cheetah.handler.ConsoleHandler;
import org.togethwy.cheetah.processor.PageProcessor;
import org.togethwy.cheetah.selector.Html;

import java.util.List;

/**
 * 豆瓣电影爬虫demo
 * @author wangtonghe
 * @date 2017/7/8 16:44
 */
public class DoubanMovieDemo implements PageProcessor{

    private Config config = Config.create();

    @Override
    public void process(Page page) {

        List<String> tabs = page.getHtml().$(".article table.tagCol").get(0).getLinks();
        page.addWaitRequest(tabs);

        List<String> subjects = page.getHtml().$("#wrapper #content .article .item .pl2").getLinks();
        page.addWaitRequest(subjects);

        List<String> next = page.getHtml().$(".paginator").getLinks();
        page.addWaitRequest(next);

        String name = page.getHtml().$("#content h1 span[property=v:itemreviewed]").getValue();

        Html info = page.getHtml().$("#info");

        String author = info.get(0).$("span .attrs a").getValue();
        List<String> actor_list = info.$("span.actor .attrs a").getAll();
        actor_list = actor_list.size() == 0 ? null : actor_list;

        List<String> category_list = info.$("span[property=v:genre]").getAll();
        category_list = category_list.size() == 0 ? null : category_list;

        String country = info.$("span.pl:contains(制片国家/地区)").get(0).nextNodeText();
        String lang = info.$("span.pl:contains(语言)").get(0).nextNodeText();
        String date = info.$("span[property=v:initialReleaseDate]").getValue();

        String mark = page.getHtml().$("#interest_sectl .rating_num").getValue();

        page.setResult("name", name);
        page.setResult("author", author);
        page.setResult("actor", actor_list);
        page.setResult("lang", lang);
        page.setResult("country", country);
        page.setResult("category", category_list);
        if (date != null && date.indexOf("(") > 0) {
            date = date.substring(0, date.indexOf("("));
        }
        page.setResult("date", date);
        page.setResult("mark", mark);


    }

    @Override
    public Config getConfig() {
        this.config.setDomain("https://movie.douban.com")
                .setStartUrl("https://movie.douban.com/tag")
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36")
                .addCookie("bid","PI0P2w4aMDI")
                .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Accept-Encoding","gzip, deflate, sdch, br")
                .addHeader("Accept-Language","zh-CN, zh; q=0.8, en; q=0.6")
                .setThreadSleep(2000)
                .setThreadNum(3);
        return config;
    }

    public static void main(String[] args) {
        Crawler.create(new DoubanMovieDemo()).setHandler(new ConsoleHandler()).run();
    }
}
