package com.wthfeng.cheetah;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * slf4j日志测试
 * @author wangtonghe
 * @date 2017/7/8 11:32
 */
public class LogTest {

    private static final Logger logger = LoggerFactory.getLogger(LogTest.class);

    private void  test(){
        String str = "hello";
        logger.debug("this is: {} ",str);
        System.out.println(str);
    }

    public static void main(String[] args) {
        LogTest logTest = new LogTest();
        logTest.test();
    }

}
