package org.togethwy.cheetah.downloader;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.*;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.togethwy.cheetah.SiteConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wangtonghe
 * @date 2017/9/10 17:27
 */
public final class HttpClientHelper {

    public static CloseableHttpClient getDefaultClient(SiteConfig siteConfig) {

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setUserAgent(siteConfig.getUserAgent())
                .setRedirectStrategy(new DefaultRedirectStrategy())
                .setDefaultHeaders(convertMap2List(siteConfig.getHeaders()));
        CookieStore cookieStore = new BasicCookieStore();
        Map<String, String> cookieMap = siteConfig.getCookies();
        String domain = siteConfig.getDomain();
        cookieMap.forEach((name, value) -> {
            BasicClientCookie cookie = new BasicClientCookie(name, value);
            cookie.setDomain(domain);
            cookie.setPath("/");
            cookieStore.addCookie(cookie);
        });
        httpClientBuilder.setDefaultCookieStore(cookieStore);
        return httpClientBuilder.build();


    }

    private static List<Header> convertMap2List(Map<String, String> map) {
        List<Header> list = new ArrayList<>();
        map.forEach((k, v) -> {
            Header header = new BasicHeader(k, v);
            list.add(header);
        });
        return list;
    }
}
