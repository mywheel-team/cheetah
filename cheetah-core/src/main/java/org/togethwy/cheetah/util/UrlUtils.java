package org.togethwy.cheetah.util;

import org.togethwy.cheetah.downloader.Request;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wangtonghe
 * @date 2017/7/8 21:06
 */
public class UrlUtils {

    /**
     * 进行url地址转化
     *
     * @param url   url地址
     * @param refer url地址来自哪个页面
     * @return url绝对地址
     */
    public static String canonicalizeUrl(String url, String refer) {
        if (url.startsWith("http") || url.startsWith("https")) {
            return url;
        }
        String retUrl = "";
        if (url.startsWith("/")) {
            try {
                URL tmpUrl = new URL(refer);
                String domain = tmpUrl.getHost();
                retUrl =  tmpUrl.getProtocol() + "://" + domain + url;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return retUrl;
    }


    public static Set<Request> Str2Request(List<String> reqUrls, String domain) {
        Set<Request> requests = new HashSet<>();
        for (String s : reqUrls) {
            if (StringUtils.isEmpty(s) || s.equals("#") || s.startsWith("javascript:")) {
                break;
            }
            s = UrlUtils.canonicalizeUrl(s, domain);
            requests.add(new Request(s));
        }
        return requests;
    }
}
