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
}
