package org.togethwy.thread;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author wangtonghe
 * @date 2017/12/9 23:39
 */
public class ConnectionDriver {

    public static Connection getConnection() {
        //连接类，得到和目标数据库连接的对象
        Connection con = null;
        //加载驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //加载驱动类
            con = DriverManager.getConnection("jdbc:mysql://localhost/people", "root", "wangtonghe");
            //获取与目标数据库的连接，参数（"jdbc:mysql://localhost/数据库名"，"root"，"数据库密码"；
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return con;

    }
}
