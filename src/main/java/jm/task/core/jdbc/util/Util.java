package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private final String URL =  "jdbc:mysql://localhost:3306/test_db?useSSL=false";
    private final String USER = "root";
    private final String PASSWORD = "root";

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            System.out.println("Connection error\n" + ex);
        }
        return connection;
    }

}
