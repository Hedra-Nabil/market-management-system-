package admin.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MarketAnalysis extends JFrame {
    private JLabel totalSalesLabel, totalPurchasesLabel, stockValueLabel, profitLabel;
    private JLabel totalEmployeesLabel, totalSuppliersLabel, totalCustomersLabel;
    private JTable salesTable;
    private JButton refreshButton;

    public MarketAnalysis() {
        setTitle("Market Analysis");
        setSize(900, 700);

        setLocationRelativeTo(null);


        Color backgroundColor = new Color(40, 45, 50);
        Color headerColor = new Color(70, 130, 180);
        Color textColor = Color.WHITE;
        Color buttonColor = new Color(0, 123, 255);


        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(backgroundColor);
        add(mainPanel);


        JLabel titleLabel = new JLabel("Market Analysis Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(headerColor);
        mainPanel.add(titleLabel, BorderLayout.NORTH);


        JPanel summaryPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        summaryPanel.setBackground(backgroundColor);
        summaryPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(headerColor), "Summary"));
        ((javax.swing.border.TitledBorder) summaryPanel.getBorder()).setTitleColor(headerColor);

        totalSalesLabel = createLabel("Total Sales: ", textColor);
        totalPurchasesLabel = createLabel("Total Purchases: ", textColor);
        stockValueLabel = createLabel("Stock Value: ", textColor);
        profitLabel = createLabel("Profit: ", textColor);
        totalEmployeesLabel = createLabel("Employees: ", textColor);
        totalSuppliersLabel = createLabel("Suppliers: ", textColor);
        totalCustomersLabel = createLabel("Customers: ", textColor);

        summaryPanel.add(totalSalesLabel);
        summaryPanel.add(totalPurchasesLabel);
        summaryPanel.add(stockValueLabel);
        summaryPanel.add(profitLabel);
        summaryPanel.add(totalEmployeesLabel);
        summaryPanel.add(totalSuppliersLabel);
        summaryPanel.add(totalCustomersLabel);

        mainPanel.add(summaryPanel, BorderLayout.WEST);


        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(backgroundColor);
        salesTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(salesTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(headerColor), "Sales Details"));
        ((javax.swing.border.TitledBorder) tableScrollPane.getBorder()).setTitleColor(headerColor);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        mainPanel.add(tablePanel, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        refreshButton = new JButton("Refresh Data");
        refreshButton.setBackground(buttonColor);
        refreshButton.setForeground(textColor);
        refreshButton.setFocusPainted(false);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPanel.add(refreshButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);


        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData();
            }
        });


        refreshData();
    }

    private JLabel createLabel(String text, Color textColor) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(textColor);
        return label;
    }

    private void refreshData() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:new_file")) {

            String salesQuery = "SELECT SUM(TotalAmount) AS TotalSales FROM Sales";
            try (PreparedStatement stmt = conn.prepareStatement(salesQuery);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    totalSalesLabel.setText("Total Sales: " + rs.getDouble("TotalSales"));
                }
            }


            String purchasesQuery = "SELECT SUM(TotalAmount) AS TotalPurchases FROM Purchases";
            try (PreparedStatement stmt = conn.prepareStatement(purchasesQuery);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    totalPurchasesLabel.setText("Total Purchases: " + rs.getDouble("TotalPurchases"));
                }
            }


            String stockQuery = "SELECT SUM(Price * Stock) AS StockValue FROM Products";
            try (PreparedStatement stmt = conn.prepareStatement(stockQuery);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stockValueLabel.setText("Stock Value: " + rs.getDouble("StockValue"));
                }
            }


            double totalSales = Double.parseDouble(totalSalesLabel.getText().split(": ")[1]);
            double totalPurchases = Double.parseDouble(totalPurchasesLabel.getText().split(": ")[1]);
            profitLabel.setText("Profit: " + (totalSales - totalPurchases));


            String employeesQuery = "SELECT COUNT(*) AS TotalEmployees FROM Employees";
            try (PreparedStatement stmt = conn.prepareStatement(employeesQuery);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    totalEmployeesLabel.setText("Employees: " + rs.getInt("TotalEmployees"));
                }
            }


            String suppliersQuery = "SELECT COUNT(*) AS TotalSuppliers FROM Suppliers";
            try (PreparedStatement stmt = conn.prepareStatement(suppliersQuery);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    totalSuppliersLabel.setText("Suppliers: " + rs.getInt("TotalSuppliers"));
                }
            }


            String customersQuery = "SELECT COUNT(*) AS TotalCustomers FROM Customers";
            try (PreparedStatement stmt = conn.prepareStatement(customersQuery);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    totalCustomersLabel.setText("Customers: " + rs.getInt("TotalCustomers"));
                }
            }


            String salesDetailsQuery = "SELECT SaleID, TotalAmount, SaleDate FROM Sales";
            try (PreparedStatement stmt = conn.prepareStatement(salesDetailsQuery);
                 ResultSet rs = stmt.executeQuery()) {
                salesTable.setModel(buildTableModel(rs));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage());
        }
    }

    private static javax.swing.table.TableModel buildTableModel(ResultSet rs) throws SQLException {
        java.sql.ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnNames[i] = metaData.getColumnName(i + 1);
        }
        java.util.Vector<String[]> data = new java.util.Vector<>();
        while (rs.next()) {
            String[] row = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                row[i] = rs.getString(i + 1);
            }
            data.add(row);
        }
        return new javax.swing.table.DefaultTableModel(data.toArray(new Object[0][]), columnNames);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MarketAnalysis().setVisible(true));
    }
}
