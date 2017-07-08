package org.togethwy.cheetah.selector;

import org.togethwy.cheetah.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author wangtonghe
 * @date 2017/7/8 20:36
 */
public class Html {

    private Document document;

    public  Html(String text){
        if(!StringUtil.isEmpty(text)){
           this.document = Jsoup.parse(text);
        }
    }

}
