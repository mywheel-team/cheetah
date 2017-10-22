package org.togethwy;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.togethwy.cheetah.redis.RedisHelper;

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
        Set<String> items = redisHelper.getByKey("music163");
        List<Map<String,Object>> list = new ArrayList<>();
        items.forEach(item->{
            System.out.println(item);
            Map<String,Object> map = JSON.parseObject(item);
        });
        System.out.println("一共有："+items.size()+"条数据");






    }
}
