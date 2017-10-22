package org.togethwy.cheetah.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangtonghe
 * @date 2017/10/19 17:43
 */
public class RedisPool {

    private static Lock poolLock = new ReentrantLock();


    private static int DEFAULT_POOL_MAX_REDIS_NUM = 100;

    private static JedisPool jedisPool;

    private static JedisPool initPool(String host, int port) {
        if (jedisPool == null) {
            try {
                poolLock.lock();
                JedisPoolConfig config = new JedisPoolConfig();
                config.setMaxTotal(DEFAULT_POOL_MAX_REDIS_NUM);
                jedisPool = new JedisPool(config, host, port);

            } finally {
                poolLock.unlock();
            }
        }
        return jedisPool;
    }

   public static Jedis getJedisFromPool(String host, int port) {
        if (jedisPool == null) {
            initPool(host, port);
        }
        return jedisPool.getResource();

    }

}
