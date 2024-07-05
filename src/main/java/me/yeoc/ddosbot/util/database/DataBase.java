package me.yeoc.ddosbot.util.database;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBase {
    public DataBaseCore getDataBaseCore() {
        return dataBaseCore;
    }

    private final DataBaseCore dataBaseCore;

    public DataBase(DataBaseCore core) {
        dataBaseCore = core;
    }

    public static DataBase create(String ip, int port, String table, String user, String password) {
        return new DataBase(new MySQLCore(ip, port, table, user, password));
    }

    public boolean close() {
        try {
            dataBaseCore.getTotalConnection().close();
            return true;
        } catch (Exception e) {
        }
        return false;
    }
    public String dbSelectFirst(String tableName, String fields, KeyValue selConditions) {
        String sql = "SELECT " + fields + " FROM " + tableName + " WHERE "
                + selConditions.toWhereString() + " LIMIT 1";
        try {
            DataBaseCore.Group<DataBaseCore.Group<Connection, Statement>, ResultSet> group = dataBaseCore.executeQuery(sql);
            ResultSet dbresult = group.getB();
            String s = null;
            if (dbresult.next()) {
                s = dbresult.getString(fields);
            }
            group.getB().close();
            group.getA().getB().close();
            group.getA().getA().close();
            return s;
        } catch (Exception e) {
            sqlerr(sql, e);
        }
        return null;
    }
    public void sqlerr(String sql, Exception e) {
        System.out.println("数据库操作出错: " + e.getMessage());
        System.out.println("SQL查询语句: " + sql);
    }

//    public boolean copyTo(DataBaseCore db) {
//        try {
//            String src = dataBaseCore.getConnection().getMetaData().getURL();
//            String des = db.getConnection().getMetaData().getURL();
//            ResultSet rs = dataBaseCore.getConnection().getMetaData().getTables(null,
//                    null, "%", null);
//            List<String> tables = new LinkedList<String>();
//            while (rs.next()) {
//                tables.add(rs.getString("TABLE_NAME"));
//            }
//            rs.close();
//            int s = 0;
//            for (String table : tables) {
//                System.out.println("开始复制源数据库中的表 " + table + " ...");
//                if (!table.toLowerCase().startsWith("sqlite_autoindex_")) {
//                    System.out.println("清空目标数据库中的表 " + table + " ...");
//                    db.execute("DELETE FROM " + table);
//                    rs = dataBaseCore.executeQuery("SELECT * FROM " + table);
//                    int n = 0;
//                    String query = "INSERT INTO " + table + " VALUES (";
//                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//                        query = query + "?, ";
//                    }
//                    query = query.substring(0, query.length() - 2) + ")";
//                    PreparedStatement ps = db.getConnection().prepareStatement(query);
//                    long time = System.currentTimeMillis();
//                    while (rs.next()) {
//                        n++;
//                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//                            ps.setObject(i, rs.getObject(i));
//                        }
//                        ps.addBatch();
//                        if (n % 100 == 0) {
//                            ps.executeBatch();
//                        }
//                        if (System.currentTimeMillis() - time > 500L) {
//                            System.out.println("已复制 " + n + " 条记录...");
//                            time = System.currentTimeMillis();
//                        }
//                    }
//                    System.out.println("数据表 " + table + " 复制完成 共 " + n + " 条记录...");
//                    s += n;
//                    ps.executeBatch();
//                    rs.close();
//                }
//            }
//            System.out.println("成功从 " + src + " 复制 " + s + " 条数据到 " + des + " ...");
//            db.getConnection().close();
//            dataBaseCore.getConnection().close();
//            return true;
//        } catch (SQLException e) {
//        }
//        return false;
//    }

}
