package wearhouse.code;

import javax.swing.*;
import java.sql.*;
public class connection{ ;
    public static final String DB_URL = "jdbc:sqlite:new_file";
public static Connection connect() {
    Connection conn = null;
    try {
        conn = DriverManager.getConnection(DB_URL);
        System.out.println("Connection successfully.");
    } catch (SQLException e) {
        System.out.println("Connection failed: " + e.getMessage());
    }
    return conn;
}

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Connection closed successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to close connection: " + e.getMessage());
        }
    }
}


