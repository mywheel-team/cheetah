package org.togethwy.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author wangtonghe
 * @date 2017/12/12 12:51
 */
public class DateTime {


    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd");


        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        System.out.println(hour);

        calendar.add(Calendar.DAY_OF_MONTH,1);
        String time = df.format(calendar.getTime())+" 00:00:00";

        System.out.println(time);
    }
}
