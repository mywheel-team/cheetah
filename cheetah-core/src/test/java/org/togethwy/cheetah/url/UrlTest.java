package org.togethwy.cheetah.url;

import org.junit.Test;

/**
 * @author wangtonghe
 * @date 2017/9/23 18:11
 */
public class UrlTest {


    @Test
    public void test() {
        String url = "https://movie.douban.com/j/new_search_subjects?sort=T&range=0,10&tags=&start=0";
        String numStr = url.substring(url.lastIndexOf("start="));
        int num = Integer.parseInt(numStr.substring(6));
        String newUrl = url.replace(numStr, "start=" + (num + 10));
        System.out.println(newUrl);


    }
}
