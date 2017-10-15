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

    @Test
    public void test2() {
        String url = "http://music.163.com/weapi/v1/resource/comments/R_SO_4_5051245?csrf_token=";
        String numStr = url.substring(url.lastIndexOf("R_SO_4_")+7, url.lastIndexOf("?"));
        String newStr = "111111";
        String newUrl = url.replace(numStr, newStr);
        System.out.println(newUrl);


    }
}
