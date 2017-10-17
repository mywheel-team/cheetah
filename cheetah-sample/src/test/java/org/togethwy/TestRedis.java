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
        Set<String> musics = redisHelper.getByKey("r_redis");
        List<Map<String,Object>> list = new ArrayList<>();
        musics.forEach(mu->{
            System.out.println(mu);
            Map<String,Object> map = JSON.parseObject(mu);
            list.add(map);
        });
        list.forEach(music->{
            System.out.println(music);
        });





    }
}
