package org.togethwy.cheetah.redis;

import org.togethwy.cheetah.handler.RedisHandler;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

/**
 * redis帮助类
 * @author wangtonghe
 * @date 2017/10/11 12:14
 */
public class RedisHelper {

    private static int DEFAULT_REDIS_PORT = 6379;

    private Jedis jedis;

    private String key;


    public RedisHelper(String host,int port) {
        jedis = new Jedis(host,port);
    }

    public RedisHelper(String host){
        jedis = new Jedis(host,RedisHelper.DEFAULT_REDIS_PORT);
    }

    public void insert(String key,String value){
        jedis.sadd(key,value);

    }



    public void close(){
        if(jedis!=null){
            jedis.close();
        }
    }



}
