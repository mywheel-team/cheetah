package org.togethwy.cheetah.handler;

import org.togethwy.cheetah.CheetahResult;
import org.togethwy.cheetah.mysql.MySqlConfig;
import org.togethwy.cheetah.mysql.MySqlDAO;

/**
 * @author wangtonghe
 * @since 2018/3/31 22:06
 */
public class MysqlHandler implements Handler {

    MySqlDAO mySqlDAO;

    public MysqlHandler(MySqlConfig mySqlConfig) {
        try {
            mySqlDAO = new MySqlDAO(mySqlConfig);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void handle(CheetahResult result) {
        result.getResults().forEach(e -> mySqlDAO.insert(e));
    }

    @Override
    public void destory() {
        mySqlDAO.destory();
    }

    @Override
    public void setDomain(String domain) {

    }
}
