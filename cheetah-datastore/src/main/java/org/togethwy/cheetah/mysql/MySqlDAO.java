package org.togethwy.cheetah.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author wangtonghe
 * @date 2017/9/23 23:15
 */
public class MySqlDAO {


    private Connection connection;


    protected Statement statement;

    private String tableName;

    private String port;

    private String user;

    private String password;


    public MySqlDAO(String host, String port, String user, String password, String database, String table) throws Exception {

        this.tableName = table;
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
        Class.forName("com.mysql.jdbc.Driver");
        this.connection = DriverManager.getConnection(url, user, password);
        this.statement = connection.createStatement();

    }

    public MySqlDAO(MySqlConfig mySqlConfig) throws Exception {
        this.tableName = mySqlConfig.getTableName();
        String url = "jdbc:mysql://" + mySqlConfig.getHost() + ":" + mySqlConfig.getPort() + "/"
                + mySqlConfig.getDatabase() + "?useUnicode=true&characterEncoding=utf-8";

        Class.forName("com.mysql.jdbc.Driver");
        this.connection = DriverManager.getConnection(url, mySqlConfig.getUser(), mySqlConfig.getPassword());
        this.statement = connection.createStatement();

    }

    public void insert(Map<String, Object> map) {
        String sql = "";
        try {
            String name = (String) map.get("name");
            String singer = (String) map.get("singer");
            String album = (String) map.get("album");
            String origin = (String) map.get("origin");
            String cover = (String) map.get("cover");
            String createDate = ((LocalDate) map.get("create_date")).format(DateTimeFormatter.ISO_LOCAL_DATE);
            String link = (String) map.get("link");
            int commentNum = (Integer) map.get("commentNum");
            int music_id = Integer.parseInt((String) map.get("music_id"));
            sql = "INSERT INTO music(music_id,name, singer, album, origin, cover, create_date, link,comment_num) " +
                    "VALUES(" + music_id + ",'" + name + "','" + singer + "','" + album + "','" + origin + "','" + cover + "','"
                    + createDate + "','" + link + "','" + commentNum + "')";

            statement.execute(sql);


        } catch (Exception e) {
            System.out.println("sql:" + sql);
            e.printStackTrace();
        }
    }

    public void destory() {
        try {
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();

        }


    }
}
