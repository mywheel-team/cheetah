package org.togethwy.cheetah.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.togethwy.cheetah.Result;
import org.togethwy.cheetah.elasticsearch.ESHelper;

import java.net.UnknownHostException;
import java.util.List;

/**
 * @author wangtonghe
 * @date 2017/5/8 08:51
 */
public class ElasticHandler implements Handler {

    private static final Logger logger = LoggerFactory.getLogger(ElasticHandler.class);



    private String index;

    private String type;

    private ESHelper esHelper;


    public ElasticHandler(String host,int port,String cluster,String index,String type) {

        try {
            this.esHelper = new ESHelper(cluster,host,port);
            this.index = index;
            this.type = type;
        } catch (UnknownHostException e) {
           logger.error("create ElasticHandler error",e);
        }
    }


    @Override
    public void handle(List<Result> results) {
        results.forEach(result->{
            esHelper.insert(index,type,result.getResult());

        });
    }



    @Override
    public void destory() {
        esHelper.close();
    }

    @Override
    public void setDomain(String domain) {

    }
}
