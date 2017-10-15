package org.togethwy.cheetah.handler;

import org.togethwy.cheetah.CheetahResult;
import org.togethwy.cheetah.Result;

import java.util.List;
import java.util.Map;

/**
 * @author wangtonghe
 * @date 2017/5/3 21:47
 */
public class ConsoleHandler implements Handler {
    @Override
    public void handle(CheetahResult cheetahResult) {

        cheetahResult.getResults().forEach(result->{
            result.forEach((k,v)->{
                System.out.println(k+":\t"+v);
            });
        });
        cheetahResult.getFileResults().forEach(System.out::println);

    }

    @Override
    public void destory() {

    }

    @Override
    public void setDomain(String domain) {

    }
}
