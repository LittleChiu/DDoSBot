package me.yeoc.ddosbot.util.database;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;

public class MySQLCore extends DataBaseCore
{
    private static String driverName = "com.mysql.jdbc.Driver";
    //Connection connection;
    private String username;
    private String password;
    private String url;
    private HikariDataSource ds;
//    public MySQLCore(ConfigurationSection cfg)
//    {
//        this(cfg.getString("ip"), cfg.getInt("port"), cfg.getString("me.yeoc.qqhook.database"),
//                cfg.getString("username"), cfg.getString("password"));
//    }

    public MySQLCore(String host, int port, String dbname, String username,
                     String password)
    {
        url = ("jdbc:mysql://" + host + ":" + port + "/" + dbname+"?useSSL=false&serverTimezone=Asia/Shanghai");
        this.username = username;
        this.password = password;
        //创建连接池参数
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverName);
        config.addDataSourceProperty("connectionTimeout", "1000"); // 连接超时：1秒
        config.addDataSourceProperty("idleTimeout", "5000"); // 空闲超时：60秒
        config.addDataSourceProperty("maximumPoolSize", "1"); // 最大连接数：1

        try {
            ds = new HikariDataSource(config);//使用参数创建连接池
            //connection = ds.getConnection();
        }catch (Exception e){
            System.out.println("无法连接 原因: "+e.getMessage());
        }
//        try
//        {
//            Class.forName(driverName).newInstance();
//        }
//        catch (Exception e)
//        {
//            System.out.println("数据库初始化失败 请检查驱动 " + driverName + " 是否存在!");
//        }
    }

    @Override
    public Closeable getTotalConnection() {
        return ds;
    }

    @Override
    public boolean createTables(String tableName, KeyValue fields, String conditions)
            throws SQLException
    {
        String sql = "CREATE TABLE IF NOT EXISTS `" + tableName + "` ( "
                + fields.toCreateString()
                + (conditions == null ? ""
                : new StringBuilder(" , ").append(conditions).toString())
                + " ) ENGINE = MyISAM DEFAULT CHARSET=GBK;";
        //System.out.println(sql);
        return execute(sql);
    }

    @Override
    public Connection getConnection()
    {
        try
        {
            return ds.getConnection();
        }
        catch (Exception e)
        {
            System.out.println("数据库操作出错: " + e.getMessage());
            System.out.println("登录URL: " + url);
            System.out.println("登录账户: " + username);
            System.out.println("登录密码: " + password);
        }
        return null;
    }
}
