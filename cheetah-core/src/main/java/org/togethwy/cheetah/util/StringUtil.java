package org.togethwy.cheetah.util;

import java.util.Random;

/**
 * @author wangtonghe
 * @date 2017/5/15 21:51
 */
public final class StringUtil {

    public static  String getRandomStr(int num){
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();

    }
    public static boolean isEmpty(String str){
        return str==null||"".equals(str.trim());
    }



}
