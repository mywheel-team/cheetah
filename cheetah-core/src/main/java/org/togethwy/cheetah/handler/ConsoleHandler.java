package org.togethwy.cheetah.handler;

import org.togethwy.cheetah.Result;

import java.util.List;
import java.util.Map;

/**
 * @author wangtonghe
 * @date 2017/5/3 21:47
 */
public class ConsoleHandler implements Handler {
    @Override
    public void handle(List<Result> results) {

        results.forEach(result->{
            for(Map.Entry<String,Object> entry:result.getResult().entrySet() ){
                System.out.println(entry.getKey()+":\t"+entry.getValue());
            }
        });

    }

    @Override
    public void destory() {

    }

    @Override
    public void setDomain(String domain) {

    }
}
