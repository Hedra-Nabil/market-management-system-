package ImplementationDB;

import java.sql.*;

public class SQL {
    // رابط قاعدة البيانات SQLite
    private static final String DB_URL = "jdbc:sqlite:new_file";

    // الاتصال بقاعدة البيانات
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Connection established successfully.");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }

    // التحقق من تسجيل الدخول باستخدام اسم المستخدم وكلمة المرور وتحديد الدور
    public static String login(String username, String password) {
        String sql = "SELECT * FROM UserAccounts WHERE Username = ? AND Password = ?";
        String role = null;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            // إذا كان هناك سجل مطابق، يتم استخراج الدور
            if (rs.next()) {
                role = rs.getString("Role");  // استرجاع الدور من قاعدة البيانات
                System.out.println("Login successful! Role: " + role);
            } else {
                System.out.println("Invalid username or password.");
            }

        } catch (SQLException e) {
            System.out.println("Login failed: " + e.getMessage());
        }

        return role;  // إرجاع الدور
    }

    // إغلاق الاتصال بقاعدة البيانات
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

public static Boolean checkDatabaseConnection() {
    try (Connection conn = connect()) {
        if (conn != null) {
            return true; // قاعدة البيانات متصلة
        } else {
            return false; // قاعدة البيانات غير متصلة
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false; // في حالة حدوث خطأ في الاتصال
    }
}
}
