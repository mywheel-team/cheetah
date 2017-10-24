package org.togethwy.cheetah.selector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.togethwy.cheetah.CheetahTimer;
import org.togethwy.cheetah.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangtonghe
 * @date 2017/10/24 08:21
 */
public class RegexSelector implements Selector2 {

    private static final Logger logger = LoggerFactory.getLogger(CheetahTimer.class);

    private Pattern pattern;

    public RegexSelector(String regexStr) {
        if(StringUtils.isEmpty(regexStr)){
            logger.error("RegexSelector regexStr not be empty");
            throw new  IllegalArgumentException("RegexSelector regexStr not be empty");
        }
        pattern = Pattern.compile(regexStr,Pattern.CASE_INSENSITIVE);
    }

    @Override
    public String select(String text) {
        return selectByRegex(text);
    }

    private String selectByRegex(String text){
      Matcher matcher =  pattern.matcher(text);
      if(matcher.find()){
         return matcher.group();
      }
      return null;
    }
}
