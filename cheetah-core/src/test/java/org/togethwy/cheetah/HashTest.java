package org.togethwy.cheetah;

import org.junit.Test;

/**
 * @author wangtonghe
 * @date 2017/10/17 21:45
 */
public class HashTest {

    @Test
    public void test(){
        String str = "https://www.zhihu.com/people/xilaganlan/followers";
        String str2 = "https://www.zhihu.com/people/guang-yong-6/followers";
        String str3 = "https://www.zhihu.com/people/geng-bao-27/followers";
        String str4 = "https://www.zhihu.com/people/geng-bao-27/follower";
        System.out.println(str.hashCode());
        System.out.println(str2.hashCode());
        System.out.println(str3.hashCode());
        System.out.println(str4.hashCode());

    }
}
