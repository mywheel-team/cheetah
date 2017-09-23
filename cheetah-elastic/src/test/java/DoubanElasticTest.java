

import org.togethwy.cheetah.SiteConfig;
import org.togethwy.cheetah.Cheetah;
import org.togethwy.cheetah.downloader.Page;
import org.togethwy.cheetah.handler.ConsoleHandler;
import org.togethwy.cheetah.processor.PageProcessor;
import org.togethwy.cheetah.selector.Html;
import org.togethwy.cheetah.util.StringUtils;
import org.togethwy.handler.ElasticHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wangtonghe
 * @date 2017/5/8 09:53
 */
public class DoubanElasticTest implements PageProcessor {

    private SiteConfig setting;

    @Override
    public void process(Page page) {
        List<String> tabs= page.getHtml().$(".article table.tagCol").get(0).getLinks();
        page.addWaitRequest(tabs);

        List<String> subjects = page.getHtml().$("#wrapper #content .article .item .pl2").getLinks();
        page.addWaitRequest(subjects);

        List<String> next = page.getHtml().$(".paginator").getLinks();
        page.addWaitRequest(next);

        String name = page.getHtml().$("#content h1 span[property=v:itemreviewed]").getValue();

        Html info = page.getHtml().$("#info");

        String author =info.get(0).$("span .attrs a").getValue();
        List<String> actor_list = info.$("span.actor .attrs a").getAll();
        actor_list=actor_list.size()==0?null:actor_list;

        List<String> category_list = info.$("span[property=v:genre]").getAll();
        category_list =category_list.size()==0?null:category_list;

        String country = info.$("span.pl:contains(制片国家/地区)").get(0).nextNodeText();
        String lang = info.$("span.pl:contains(语言)").get(0).nextNodeText();
        String date = info.$("span[property=v:initialReleaseDate]").get(0).getValue();

        String markText = page.getHtml().$("#interest_sectl .rating_num").getValue();

        Double mark =null;


        if(!StringUtils.isEmpty(markText)){
            mark = Double.parseDouble(markText);
        }
        Date dateTime = null;

        try {
            if(date!=null&&date.indexOf("(")>0){
                date = date.substring(0,date.indexOf("("));
                dateTime = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        page.setResult("name",name);
        page.setResult("author",author);
        page.setResult("category",category_list);
        page.setResult("actor",actor_list);
        page.setResult("lang",lang);
        page.setResult("country",country);
        page.setResult("date",dateTime);
        page.setResult("mark",mark);

    }

    @Override
    public SiteConfig getSiteConfig() {
        setting= SiteConfig.create().setStartUrl("https://movie.douban.com/tag");
        setting.setDomain("https://movie.douban.com");
        setting.setThreadSleep(2000);
        setting.setThreadNum(3);
        return setting;
    }



    public static void main(String[] args) {
        Cheetah.create(new DoubanElasticTest())
                .setHandler(new ElasticHandler("bee4","movie"))
                .setHandler(new ConsoleHandler())
                .run();
    }
}
