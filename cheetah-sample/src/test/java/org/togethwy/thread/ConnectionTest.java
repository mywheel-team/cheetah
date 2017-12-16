package org.togethwy.thread;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangtonghe
 * @date 2017/12/9 23:49
 */
public class ConnectionTest {

    static ConnectionPool pool = new ConnectionPool(10);

    private static final int threadNum = 20;

    static CountDownLatch latch = new CountDownLatch(threadNum);

    static CountDownLatch start = new CountDownLatch(1);


    public static void main(String[] args) {
        AtomicInteger getters = new AtomicInteger();
        AtomicInteger notgeters = new AtomicInteger();
        long startTime = System.currentTimeMillis();
        System.out.println("start:"+startTime);
        for (int i = 0; i < threadNum; i++) {
            Thread t = new Thread(new ConnectRuner(getters, notgeters));
            t.start();
        }
        start.countDown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis()-startTime);
        System.out.println("not got is :" + notgeters.get());
        System.out.println("got is :" + getters.get());


    }

    static class ConnectRuner implements Runnable {

        private AtomicInteger gets;
        private AtomicInteger notget;

        public ConnectRuner(AtomicInteger gets, AtomicInteger notget) {
            this.gets = gets;
            this.notget = notget;
        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 20; i++) {

                Connection connection = null;
                try {
                    connection = pool.getConnection(3000);
                    if (connection == null) {
                        System.out.println("超时未获得连接连接：");
                        notget.incrementAndGet();
                    } else {
                        System.out.println("得到连接：" + connection);
                        connection.createStatement();
                        TimeUnit.MILLISECONDS.sleep(500);
                        gets.incrementAndGet();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        pool.release(connection);
                    }
                }
            }
            latch.countDown();
        }

    }
}
