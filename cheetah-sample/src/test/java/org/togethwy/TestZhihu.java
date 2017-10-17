package org.togethwy;

import org.junit.Test;

/**
 * @author wangtonghe
 * @date 2017/10/17 11:02
 */
public class TestZhihu {

    @Test
    public void test() {
        String curPage = "https://www.zhihu.com/people/han-bao-bao-84-66/followers?page=2";
        String page =curPage.substring(curPage.lastIndexOf("?page=")+6);
        String url = curPage.replace(page, String.valueOf(Integer.parseInt(page)+1));
        System.out.println(url);
    }
}
