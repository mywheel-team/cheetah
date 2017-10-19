package org.togethwy.cheetah.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * redis帮助类
 *
 * TODO 目前当开启断点重爬后，由于单个jedis在多线程同时使用同一个redis实例会报错
 *
 * @author wangtonghe
 * @date 2017/10/11 12:14
 */
public class RedisHelper {

    private static Lock poolLock = new ReentrantLock();


    public static int DEFAULT_REDIS_PORT = 6379;

    private Jedis jedis;

    public RedisHelper(String host, int port) {
        jedis =  RedisPool.getJedisFromPool(host,port);
    }

    public RedisHelper(String host) {
        jedis = RedisPool.getJedisFromPool(host,DEFAULT_REDIS_PORT);
    }

    public void add2Set(String key, String ... value) {
        jedis.sadd(key, value);
    }

    public void add2Set(String key,List<String> list){
        jedis.sadd(key,list.toArray(new String[0]));

    }

    public static RedisHelper getInstance(String host, int port){
        return new RedisHelper(host,port);

    }

    public Set<String> getAllFromSet(String key){
       return jedis.smembers(key);
    }

    public void remAllSet(String key){
        long num = jedis.scard(key);
        for(int i=0;i<num;i++){
            jedis.spop(key);

        }
    }

    public String popFromSet(String key) {
        return jedis.spop(key);
    }

    public void removeFromSet(String key,String value){
        jedis.srem(key,value);
    }

    public boolean isEmptySet(String key){
        return jedis.scard(key)==0;
    }

    public void add2List(String key,String value){
        jedis.lpush(key,value);
    }

    public Set<String> getByKey(String key) {
        return jedis.smembers(key);
    }


    public void close() {
        if (jedis != null) {
            jedis.close();
        }
    }


}
