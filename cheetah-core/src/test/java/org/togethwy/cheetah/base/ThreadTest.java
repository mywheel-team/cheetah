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
}
