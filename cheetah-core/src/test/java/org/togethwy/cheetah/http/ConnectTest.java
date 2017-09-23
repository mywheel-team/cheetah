package org.togethwy.cheetah.http;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * @author wangtonghe
 * @date 2017/9/10 12:23
 */
public class ConnectTest {

    @Test
    public void test() throws IOException{
        URL url = new URL("http://www.baidu.com");  //构建一个URL资源对象
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();//建立与指向资源的连接
        connection.setRequestMethod("GET");  //设置请求方法

        /**
         * 建立连接并获取资源
         */
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line ;
        while ((line=in.readLine())!=null){
            sb.append(line);
        }
        System.out.println(sb.toString());
    }

    @Test
    public void test2()throws IOException{
        URL url = new URL("http://www.baidu.com");
        HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();
        urlConnection.connect();
        String type = urlConnection.getContentType();
        String encoding = urlConnection.getContentEncoding();
        int length = urlConnection.getContentLength();
        long date = urlConnection.getDate();

        long expiration = urlConnection.getExpiration();
        long modified = urlConnection.getLastModified();
        System.out.println("length:"+length);
        System.out.println("encoding:"+encoding);
        System.out.println("date:"+new Date(date));
        System.out.println("modified:"+new Date(modified));
    }


}
