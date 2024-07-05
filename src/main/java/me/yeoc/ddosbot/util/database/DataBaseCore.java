package me.yeoc.ddosbot.util.database;


import java.io.Closeable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DataBaseCore {
    public abstract boolean createTables(String tableName, KeyValue fields,
                                         String conditions) throws SQLException;

    public boolean execute(String sql) throws SQLException {
        Group<Connection, Statement> group = getStatement();
        boolean execute = group.getB().execute(sql);
        group.getB().close();
        group.getA().close();
        return execute;
    }

    public Group<Group<Connection,Statement>,ResultSet> executeQuery(String sql) throws SQLException {
        Group<Connection, Statement> group = getStatement();
        ResultSet resultSet = group.getB().executeQuery(sql);
        return new Group<>(group,resultSet);
    }

    public int executeUpdate(String sql) throws SQLException {
        Group<Connection, Statement> group = getStatement();
        int i = group.getB().executeUpdate(sql);
        group.getB().close();
        group.getA().close();

        return i;
    }

    public abstract Connection getConnection();

    public abstract Closeable getTotalConnection();

    private Group<Connection,Statement> getStatement() throws SQLException {
        Connection connection = getConnection();
        return new Group<>(connection,connection.createStatement());
    }



    public static class Group<A,B>{
        public Group(A a, B b) {
            this.a = a;
            this.b = b;
        }

        public A getA() {
            return a;
        }

        public B getB() {
            return b;
        }

        A a;
        B b;

    }
}
