package org.togethwy.sample;

import org.togethwy.cheetah.CheetahTimer;
import org.togethwy.sample.common.ZhihuDemo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangtonghe
 * @date 2017/10/22 17:25
 */
public class TimerDemo {

    public static void main(String[] args) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d H:m:s");
        // 指定一个日期
        try {
            Date date = dateFormat.parse("2017-11-31 00:00:00");
            new CheetahTimer().add(ZhihuDemo.class,date).plan();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
