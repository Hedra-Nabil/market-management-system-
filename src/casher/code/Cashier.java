package casher.code;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Cashier {

    private static final String DB_URL = "jdbc:sqlite:new_file";

    public List<String[]> fetchSales() throws SQLException {
        List<String[]> receipts = new ArrayList<>();
        String query = "SELECT saleid, customerid, totalamount, saledate FROM sales";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String[] row = new String[4];
                row[0] = rs.getString("saleid");
                row[1] = rs.getString("customerid");
                row[2] = rs.getString("totalamount");
                row[3] = rs.getString("saledate");
                receipts.add(row);
            }
        }

        return receipts;
    }

    public List<String[]> fetchDiscounts() throws SQLException {
        List<String[]> coupons = new ArrayList<>();
        String query = "SELECT productid, startdate, enddate, percentage FROM Discounts";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String[] row = new String[4];
                row[0] = rs.getString("productid");
                row[1] = rs.getString("startdate");
                row[2] = rs.getString("enddate");
                row[3] = rs.getString("percentage");
                coupons.add(row);
            }
        }

        return coupons;
    }
}
