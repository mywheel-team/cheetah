package org.togethwy.cheetah.mysql;

/**
 * @author wangtonghe
 * @date 2017/9/23 23:24
 */
public final class MySqlConfig {

    private static String DEFAULT_DATABASE = "cheetah";

    private static String DEFAULT_TABLE = "tab_cheetah";

    private String host;

    private String port;

    private String user;

    private String password;


    private String database;

    private String tableName;


    public MySqlConfig(String host, String port, String user, String password, String database, String tableName) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;
        this.tableName = tableName;
    }

    public MySqlConfig(String host, String port, String user, String password) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = MySqlConfig.DEFAULT_DATABASE;
        this.tableName = MySqlConfig.DEFAULT_TABLE;
    }

    public MySqlConfig(String host, String port, String user, String password, String database) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;
        this.tableName = MySqlConfig.DEFAULT_TABLE;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
