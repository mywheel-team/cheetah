package org.togethwy.cheetah.handler;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.togethwy.cheetah.CheetahResult;
import org.togethwy.cheetah.Result;
import org.togethwy.cheetah.redis.RedisHelper;

import java.util.List;

/**
 * @author wangtonghe
 * @date 2017/10/11 15:34
 */
public class RedisHandler implements Handler {

    private static final Logger logger = LoggerFactory.getLogger(RedisHandler.class);


    private String key;

    private RedisHelper redisHelper;

    public RedisHandler(String host, int port, String key) {
        this.redisHelper = new RedisHelper(host, port);
        this.key = key;
    }

    /**
     * redis 结果处理器
     * @param host 域名
     * @param key  key
     */
    public RedisHandler(String host,String key){
        this.redisHelper = new RedisHelper(host);
        this.key = key;

    }

    @Override
    public void handle(CheetahResult cheetahResult) {

        cheetahResult.getResults().forEach(result -> {
            System.out.println("redis:"+result);
            redisHelper.insert(key, JSON.toJSONString(result));

        });
    }

    @Override
    public void destory() {
        redisHelper.close();

    }

    @Override
    public void setDomain(String domain) {

    }
}
