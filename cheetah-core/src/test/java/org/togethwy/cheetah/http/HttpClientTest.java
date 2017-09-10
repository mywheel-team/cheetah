package org.togethwy.cheetah.http;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author wangtonghe
 * @date 2017/9/10 14:07
 */
public class HttpClientTest {
    private String userAgent = "Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.36 Safari/536.5";

    @Test
    public void test() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        System.out.println(response.getStatusLine());
        String html = EntityUtils.toString(response.getEntity());
        System.out.println(html);
        response.close();

    }

    @Test
    public void useBuilder()throws IOException{
        String url = "http://localhost:3000/test";
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(1000)
                .setConnectTimeout(2000).setSocketTimeout(10000)
                .build();

        BasicCookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient client = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setDefaultCookieStore(cookieStore)
                .setUserAgent(userAgent)
                .setRedirectStrategy(new DefaultRedirectStrategy())
                .build();
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response =  client.execute(httpGet);
        if(response.getStatusLine().getStatusCode()==200){
            System.out.println("ok");
        }
        List<Cookie> cookies = cookieStore.getCookies();
        if(cookies.isEmpty()){
            System.out.println("cookie is null");

        }else{
            System.out.println("cookies:");
            cookies.forEach(c->System.out.println(c.toString()));
        }


    }
}
