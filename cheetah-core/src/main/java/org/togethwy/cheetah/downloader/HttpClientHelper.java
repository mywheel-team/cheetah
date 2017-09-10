package org.togethwy.cheetah.downloader;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.*;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.togethwy.cheetah.Config;
import sun.net.www.http.HttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wangtonghe
 * @date 2017/9/10 17:27
 */
public final class HttpClientHelper {

    public  static CloseableHttpClient getDefaultClient(Config config){

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setUserAgent(config.getUserAgent())
                .setRedirectStrategy(new DefaultRedirectStrategy())
                .setDefaultHeaders(convertMap2List(config.getHeaders()));
        CookieStore cookieStore = new BasicCookieStore();
        Map<String,String> cookieMap = config.getCookies();
        cookieMap.forEach((name,value)->{
            Cookie cookie = new BasicClientCookie(name,value);
            cookieStore.addCookie(cookie);
        });
        httpClientBuilder.setDefaultCookieStore(cookieStore);
        return httpClientBuilder.build();



    }

    private  static List<Header> convertMap2List(Map<String,String> map){
        List<Header> list = new ArrayList<>();
        map.forEach((k,v)->{
            Header header = new BasicHeader(k,v);
            list.add(header);
        });
        return list;
    }
}
