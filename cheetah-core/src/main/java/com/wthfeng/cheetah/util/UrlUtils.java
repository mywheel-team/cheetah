package com.wthfeng.cheetah.util;

/**
 * @author wangtonghe
 * @date 2017/7/8 21:06
 */
public class UrlUtils {

    /**
     * 进行url地址转化
     * @param url url地址
     * @param refer url地址来自哪个页面
     * @return url绝对地址
     */
    public static  String canonicalizeUrl(String url,String refer){
        if(url.startsWith("http")||url.startsWith("https")){
            return url;
        }
        if(url.startsWith("/")){
            int pos = refer.lastIndexOf("/");
            if(pos>refer.indexOf("//")+1){
                refer = refer.substring(0,pos);
            }
        }
        return refer+url;

    }
}
