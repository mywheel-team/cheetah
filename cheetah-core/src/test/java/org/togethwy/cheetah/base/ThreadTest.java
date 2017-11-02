package org.togethwy.cheetah.base;

import org.junit.Test;

/**
 * @author wangtonghe
 * @date 2017/10/11 09:04
 */
public class ThreadTest {

    @Test
    public void test(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("开启线程");
            }
        });

    }

    @Test
    public void test2(){


            ExceptionThread runnable = new ExceptionThread();
            Thread d =new  Thread(runnable);
            d.setUncaughtExceptionHandler((t,e)->{
                System.out.println("handle exception");
            });
            d.start();


    }


}

class ExceptionThread implements Runnable{

    @Override
    public void run() {
        throw new RuntimeException();
    }
}
