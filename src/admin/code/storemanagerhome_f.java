package admin.code;

import java.sql.*;

public class storemanagerhome_f {
        private static final String DATABASE_URL = "jdbc:sqlite:new_file";

        public void addDiscount(int productID, String startDate, String endDate, double percentage) throws SQLException {
            String query = "INSERT INTO Discounts (ProductID, StartDate, EndDate, Percentage) VALUES (?, ?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setInt(1, productID);
                pstmt.setString(2, startDate);
                pstmt.setString(3, endDate);
                pstmt.setDouble(4, percentage);
                pstmt.executeUpdate();
            }
        }

        public void updateDiscount(int productID, String startDate, String endDate, double percentage) throws SQLException {
            String query = "UPDATE Discounts SET StartDate = ?, EndDate = ?, Percentage = ? WHERE ProductID = ?";
            try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, startDate);
                pstmt.setString(2, endDate);
                pstmt.setDouble(3, percentage);
                pstmt.setInt(4, productID);
                pstmt.executeUpdate();
            }
        }

        public void deleteDiscount(int discountID) throws SQLException {
            String query = "DELETE FROM Discounts WHERE DiscountID = ?";
            try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setInt(1, discountID);
                pstmt.executeUpdate();
            }
        }

        public ResultSet getAllDiscounts() throws SQLException {
            String query = "SELECT * FROM Discounts";
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(query);
        }

        public ResultSet getActiveDiscounts() throws SQLException {
            String query = "SELECT * FROM Discounts WHERE EndDate >= CURRENT_DATE";
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(query);
        }
    }


