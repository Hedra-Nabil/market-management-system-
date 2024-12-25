package wearhouse.code;

import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
public class allOperations {

    public void performCheck(String batchID, JTextField vendorIDField, JTextField expiryDateField, JSpinner stockSpinner,
                             JTextField nameField, JTextField priceField, JComboBox<String> categoryComboBox, JTextArea descriptionArea) {
        try (Connection conn = DriverManager.getConnection(connection.DB_URL)) {
            String query = "SELECT p.Name, p.Price, p.Stock, p.ExpirationDate, p.Description, c.CategoryName, s.Name AS SupplierName " +
                    "FROM Products p " +
                    "LEFT JOIN Categories c ON p.CategoryName = c.CategoryName " +
                    "LEFT JOIN Suppliers s ON p.SupplierID = s.SupplierID " +
                    "WHERE p.ProductID = ?";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, batchID);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {

                    nameField.setText(rs.getString("Name"));
                    priceField.setText(String.valueOf(rs.getDouble("Price")));
                    stockSpinner.setValue(rs.getInt("Stock"));
                    expiryDateField.setText(rs.getString("ExpirationDate"));
                    descriptionArea.setText(rs.getString("Description"));
                    categoryComboBox.setSelectedItem(rs.getString("CategoryName"));
                    vendorIDField.setText(rs.getString("SupplierName"));
                } else {
                    JOptionPane.showMessageDialog(null, "Batch ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void performUpdate(String batchID, String vendorID, int quantity, double price, String expiryDate, String description) {
        Connection conn = null;
        try {
            conn = connection.connect();
            String sql = "UPDATE Products SET Stock = ?, Price = ?, ExpirationDate = ?, Description = ? WHERE ProductID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, quantity);
            pstmt.setDouble(2, price);
            pstmt.setString(3, expiryDate);
            pstmt.setString(4, description);
            pstmt.setString(5, batchID);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Product updated successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update product.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection(conn);
        }
    }

    public void loadCategories(JComboBox<String> categoryComboBox) {
        try (Connection conn = DriverManager.getConnection(connection.DB_URL)) {

            String query = "SELECT CategoryName FROM Categories";

            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);


                categoryComboBox.removeAllItems();


                while (rs.next()) {
                    categoryComboBox.addItem(rs.getString("CategoryName"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addVendor(String supplierID, String name, String phone, String email, String address, JFrame frame) {
        Connection conn = null;
        try {

            conn = DriverManager.getConnection(connection.DB_URL);


            String checkQuery = "SELECT * FROM Suppliers WHERE SupplierID = ? OR Name = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, supplierID);
                checkStmt.setString(2, name);

                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {

                    JOptionPane.showMessageDialog(frame, "Supplier already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {

                    String insertQuery = "INSERT INTO Suppliers (SupplierID, Name, Phone, Email, Address) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                        insertStmt.setString(1, supplierID);
                        insertStmt.setString(2, name);
                        insertStmt.setString(3, phone);
                        insertStmt.setString(4, email);
                        insertStmt.setString(5, address);

                        int rowsAffected = insertStmt.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(frame, "Vendor Added Successfully!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Failed to add vendor.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {

            if (conn != null) {
                connection.closeConnection(conn);
            }
        }}

        public static void showLowStockProducts(int minQuantity, DefaultTableModel tableModel) {

            Connection conn = null;
            try {
                conn = DriverManager.getConnection(connection.DB_URL);


                String query = "SELECT ProductID, Name, CategoryName, SupplierID, Stock, Description FROM Products WHERE Stock <= ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, minQuantity);
                ResultSet rs = stmt.executeQuery();


                tableModel.setRowCount(0);


                while (rs.next()) {
                    Object[] row = new Object[]{
                            rs.getInt("ProductID"),
                            rs.getString("Name"),
                            rs.getString("CategoryName"),
                            rs.getInt("SupplierID"),
                            rs.getInt("Stock"),
                            rs.getString("Description")
                    };
                    tableModel.addRow(row);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (conn != null) {
                    connection.closeConnection(conn);
                }

            }
        }

    public void searchProductByID(String productID, DefaultTableModel tableModel) {
        Connection conn = null;
        try {

            conn = DriverManager.getConnection(connection.DB_URL);

            String query = "SELECT ProductID, Name, CategoryName, SupplierID, Price, Stock, ExpirationDate, Description FROM Products WHERE ProductID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, productID);
            ResultSet rs = stmt.executeQuery();


            tableModel.setRowCount(0);

            if (rs.next()) {

                Object[] row = new Object[]{
                        rs.getInt("ProductID"),
                        rs.getString("Name"),
                        rs.getString("CategoryName"),
                        rs.getInt("SupplierID"),
                        rs.getDouble("Price"),
                        rs.getInt("Stock"),
                        rs.getString("ExpirationDate"),
                        rs.getString("Description")
                };
                tableModel.addRow(row);
            } else {
                JOptionPane.showMessageDialog(null, "Product not found", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (conn != null) {
                connection.closeConnection(conn);
            }

        }
    }


    public static void searchVendor(String supplierID, JTable table) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connection.DB_URL);

            String query = "SELECT SupplierID, Name, Phone, Email, Address FROM Suppliers WHERE SupplierID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, supplierID);
                ResultSet rs = stmt.executeQuery();

                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);

                if (rs.next()) {

                    model.addRow(new Object[]{
                            rs.getString("SupplierID"),
                            rs.getString("Name"),
                            rs.getString("Phone"),
                            rs.getString("Email"),
                            rs.getString("Address")
                    });
                } else {

                    JOptionPane.showMessageDialog(null, "Supplier not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {

            if (conn != null) {
                connection.closeConnection(conn);
            }
        }
    }
    public static void showAllVendors(JTable table) {
        Connection conn = null;
        try {

            conn = DriverManager.getConnection(connection.DB_URL);

            String query = "SELECT SupplierID, Name, Phone, Email, Address FROM Suppliers";
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);


                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);

                while (rs.next()) {

                    model.addRow(new Object[]{
                            rs.getString("SupplierID"),
                            rs.getString("Name"),
                            rs.getString("Phone"),
                            rs.getString("Email"),
                            rs.getString("Address")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {

            if (conn != null) {
                connection.closeConnection(conn);
            }
        }
    }

}