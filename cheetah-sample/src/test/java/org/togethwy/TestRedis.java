package org.togethwy;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.togethwy.cheetah.redis.RedisHelper;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * @author wangtonghe
 * @date 2017/10/11 17:16
 */
public class TestRedis {
//    private Jedis jedis;
//
//    @Before
//    public void before(){
//        jedis = new Jedis("127.0.0.1");
//
//    }

    @Test
    public void test(){
        RedisHelper redisHelper = new RedisHelper("127.0.0.1");
        Set<String> zhihus = redisHelper.getByKey("zhihu_new");
//        List<Map<String,Object>> list = new ArrayList<>();
//        zhihus.forEach(mu->{
//            System.out.println(mu);
//            Map<String,Object> map = JSON.parseObject(mu);
//        });
        System.out.println("一共有："+zhihus.size()+"条数据");






    }
}
