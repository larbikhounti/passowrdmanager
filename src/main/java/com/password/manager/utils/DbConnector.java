package com.password.manager.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector  {
   static Connection connection = null;


    public static Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                createConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    private static void createConnection() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@//192.168.2.22:1521/xe",
                "system", "123456");
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
    public static boolean checkConnection() {
        try {
            Connection conn = DbConnector.getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("Database connection is active.");
                return true;
            } else {

                System.out.println("Database connection is not active.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error checking database connection: " + e.getMessage());
        }
        return false;
    }
}
