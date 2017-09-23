package org.togethwy.cheetah.http;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author wangtonghe
 * @date 2017/9/22 08:28
 */
public class URLTest {

    public static void main(String[] args) throws IOException {
        URL url = new URL("http://www.baidu.com");  //构建一个URL资源对象
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();//建立与指向资源的连接
        connection.setRequestMethod("GET");  //设置请求方法
        connection.connect();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        System.out.println(sb.toString());

    }

    @Test
    public void test2() throws Exception{
        String url = "/people/zhang-jia-wei/following";
        String refer = "https://www.zhihu.com/people/zhang-jia-wei/followers";

        URL tmpUrl = new URL(refer);
        String domain = tmpUrl.getHost();
        System.out.println(tmpUrl.getProtocol()+"://"+domain+url);

    }
}
