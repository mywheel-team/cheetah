package org.togethwy.cheetah.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author wangtonghe
 * @date 2017/9/23 23:15
 */
public class MySqlDAO {


    private Connection connection;

    private String tableName;

    public MySqlDAO(String host, String port, String user, String password, String database,String table) throws Exception {

        this.tableName = table;
        String url = "jdbc:mysql://" + host + ":" + port + database;
        Class.forName("com.mysql.jdbc.Driver");
        this.connection= DriverManager.getConnection(url, user, password);
        String sql = "create ";

    }

    public MySqlDAO(MySqlConfig mySqlConfig) throws Exception {
        new MySqlDAO(mySqlConfig.getHost(), mySqlConfig.getPort(), mySqlConfig.getUser(),
                mySqlConfig.getPassword(), mySqlConfig.getDatabase(),mySqlConfig.getTableName());
    }

    public void insert(){
        try {
            Statement statement =  connection.createStatement();
            String sql = "insert into ";
//            statement.executeUpdate()

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
