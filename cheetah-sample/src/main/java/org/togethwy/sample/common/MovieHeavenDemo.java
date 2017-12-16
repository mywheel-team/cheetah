package org.togethwy.sample.common;

import org.togethwy.cheetah.CheetahResult;
import org.togethwy.cheetah.SiteConfig;
import org.togethwy.cheetah.downloader.Page;
import org.togethwy.cheetah.processor.PageProcessor;
import org.togethwy.cheetah.selector.Selectable;

import java.util.List;

/**
 * 电影天堂爬虫示例
 * @author wangtonghe
 * @date 2017/11/2 21:38
 */
public class MovieHeavenDemo implements PageProcessor{
    @Override
    public void process(Page page, CheetahResult result) {
        Selectable content  = page.getHtml().$(".bd2 > .bd3 ");

      Selectable contentDiv  = content.$(".bd3r .co_area2 .co_content8 ul");
      List<String> contUrls = contentDiv.getLinks(".*/html/gndy.*\\.html$");

        if(contUrls.size()==0){
            String name = content.$(".bd3r .co_area2 .title_all h1 font").getValue();
            String movieUrls = contentDiv.$("#Zoom span table").getLinks().get(0);
            result.putField("name",name);
            result.putField("url",movieUrls);
        }

        List<String> menuUrls = content.$(".bd3l").getLinks(".*/html/gndy.*\\.html$");

        List<String>  nextUrls = content.$(".bd3r .co_area2 .co_content8 .x").getLinks("^list.*\\.html$");

        result.addWaitRequest(contUrls);
        result.addWaitRequest(menuUrls);
        result.addWaitRequest(nextUrls);
        result.setSkip(true);
    }

    @Override
    public SiteConfig setAndGetSiteConfig() {
        return null;
    }

    public static void main(String[] args) {

    }
}
