package org.togethwy.cheetah;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.togethwy.cheetah.processor.PageProcessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * cheetah 定时器
 *
 * @author wangtonghe
 * @date 2017/10/22 16:45
 */
public class CheetahTimer {

    private static final String PLAN_METHOD_NAME = "plan";


    private static final Logger logger = LoggerFactory.getLogger(CheetahTimer.class);


    private Timer timer = new Timer();

    private Map<Class<? extends PageProcessor>, Date> planMap = new HashMap<>();

    public CheetahTimer add(Class<? extends PageProcessor> crawler, Date date) {
        planMap.put(crawler, date);
        return this;
    }

    public void plan() {
        CountDownLatch countDownLatch = new CountDownLatch(planMap.size());
        planMap.forEach((clazz, date) -> {
            try {
                Constructor proConstructor = clazz.getConstructor();
                Object pageObj = proConstructor.newInstance();
                Method method = clazz.getMethod(PLAN_METHOD_NAME);
                timer(countDownLatch,pageObj, method, date);
                countDownLatch.await();
            } catch (Exception e) {
                logger.error("CheetahTimer plan  error ", e);
            }
        });
    }

    private void timer(CountDownLatch latch,Object obj, Method method, Date date) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    method.invoke(obj);
                    latch.countDown();
                } catch (Exception e) {
                    logger.error("Cheetah timer run  error ", e);
                }
            }
        }, date);

    }


}
