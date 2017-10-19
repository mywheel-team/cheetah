package org.togethwy.cheetah.redis;

import org.junit.Test;

/**
 * @author wangtonghe
 * @date 2017/10/19 16:10
 */
public class RedisTest {

    @Test
    public void test(){
        RedisHelper helper = new RedisHelper("127.0.0.1");
        RedisHelper helper2= new RedisHelper("127.0.0.1");
        helper.add2Set("test","1");
        helper2.add2Set("test","2");
    }

    @Test
    public void test2(){
        RedisHelper helper =new RedisHelper("127.0.0.1");
        helper.remAllSet("wait_url_key_doubanmoviedemo");
    }


}
