package org.togethwy.cheetah.regex;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangtonghe
 * @date 2017/10/23 21:28
 */
public class RegexTest {

    @Test
    public void test() {
        String line = "this is a cat and cat";

        String patStr = "this";
        Pattern pattern = Pattern.compile(patStr);
        Matcher matcher = pattern.matcher(line);
        System.out.println(matcher.lookingAt());
    }

    @Test
    public void test2() {
        String url = "http://www.ygdy8.net/html/gndy/dyzz/index.html2";
        boolean is = url.matches(".*/html/gndy.*\\.html$");
        System.out.println(is);
    }

    @Test
    public void test3() {
        String url = "list_7_2.html";
        boolean is = url.matches("^list.*\\.html$");
        System.out.println(is);

    }

}
