package org.togethwy.cheetah.http;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author wangtonghe
 * @date 2017/9/10 12:23
 */
public class ConnectTest {

    @Test
    public void test() throws IOException{
        URL url = new URL("http://www.baidu.com");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        if(connection.getResponseCode()!=200){
            System.out.println("链接失败");

        }
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line ;
        while ((line=in.readLine())!=null){
            sb.append(line);
        }
        System.out.println(sb.toString());
    }
}
