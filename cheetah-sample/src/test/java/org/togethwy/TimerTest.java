package org.togethwy;

import org.junit.Test;
import org.togethwy.cheetah.Cheetah;
import org.togethwy.cheetah.CheetahTimer;
import org.togethwy.cheetah.handler.ConsoleHandler;
import org.togethwy.cheetah.handler.RedisHandler;
import org.togethwy.sample.common.ZhihuDemo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

/**
 * @author wangtonghe
 * @date 2017/10/22 16:46
 */
public class TimerTest {


    @Test
    public void test() {
        Timer timer = new Timer();
        long cur = System.currentTimeMillis() + 5000;

        Date date = new Date(cur);
        System.out.println(date);


        CountDownLatch countDownLatch = new CountDownLatch(1);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Cheetah.create(new ZhihuDemo())
                        .setHandler(new ConsoleHandler())
                        .setHandler(new RedisHandler("127.0.0.1", "zhihu_new_2"))
                        .run();
                countDownLatch.countDown();
            }
        }, date);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束");

    }


    @Test
    public void test2() throws Exception {
        Class<ZhihuDemo> demoClass = ZhihuDemo.class;

        Constructor demo = demoClass.getConstructor();
        Object obj = demo.newInstance();
        Method method = demoClass.getMethod("plan");
        method.invoke(obj);

    }

    @Test
    public void test3(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d H:m:s");
        // 指定一个日期
        try {
            Date date = dateFormat.parse("2017-10-22 19:30:00");
            new CheetahTimer().add(ZhihuDemo.class,date).plan();

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


}
