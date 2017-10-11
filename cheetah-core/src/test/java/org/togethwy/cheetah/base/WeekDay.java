package org.togethwy.cheetah.base;

import java.util.EnumSet;

/**
 * @author wangtonghe
 * @date 2017/10/11 10:39
 */
public enum  WeekDay {
    SUN("星期日"),MON("星期一"),TUS("星期二"),WED("星期三"),THU("星期四"),FRI("星期五"),SAT("星期六");

    private String value;

    private WeekDay(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

class Test{
    public static void main(String[] args) {

        for(WeekDay day : WeekDay.values()){
            System.out.println(day.getValue());
        }



    }
}
