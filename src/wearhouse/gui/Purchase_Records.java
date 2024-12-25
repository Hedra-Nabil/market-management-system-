package wearhouse.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Purchase_Records extends JFrame {
    private JTextField purchaseIdField, supplierIdField, employeeIdField, totalAmountField, purchaseDateField;
    private JTable purchaseDetailsTable;
    private JButton addButton, updateButton, deleteButton, saveButton;
    private DefaultTableModel model;
    private Connection connection;

    public Purchase_Records() {
        // Setting up the frame
        setTitle("Purchase Form");
        setSize(600, 400);
       // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Setting background color of the frame
        getContentPane().setBackground(new Color(0, 0, 41)); // Color of the frame

        // Setting up the connection
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:new_file");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Setting up the fields and buttons with custom colors
        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.setBackground(new Color(0, 0, 41)); // Background color of the panel

        // Labels
        JLabel purchaseIdLabel = new JLabel("Purchase ID:");
        purchaseIdLabel.setForeground(Color.WHITE); // Label text color
        panel.add(purchaseIdLabel);
        purchaseIdField = new JTextField();
        purchaseIdField.setEditable(false);
        panel.add(purchaseIdField);

        JLabel supplierIdLabel = new JLabel("Supplier ID:");
        supplierIdLabel.setForeground(Color.WHITE);
        panel.add(supplierIdLabel);
        supplierIdField = new JTextField();
        panel.add(supplierIdField);

        JLabel employeeIdLabel = new JLabel("Employee ID:");
        employeeIdLabel.setForeground(Color.WHITE);
        panel.add(employeeIdLabel);
        employeeIdField = new JTextField();
        panel.add(employeeIdField);

        JLabel totalAmountLabel = new JLabel("Total Amount:");
        totalAmountLabel.setForeground(Color.WHITE);
        panel.add(totalAmountLabel);
        totalAmountField = new JTextField();
        panel.add(totalAmountField);

        JLabel purchaseDateLabel = new JLabel("Purchase Date:");
        purchaseDateLabel.setForeground(Color.WHITE);
        panel.add(purchaseDateLabel);
        purchaseDateField = new JTextField();
        panel.add(purchaseDateField);

        // Table for PurchaseDetails
        String[] columns = {"Product ID", "Quantity", "Price"};
        model = new DefaultTableModel(columns, 0);
        purchaseDetailsTable = new JTable(model);
        purchaseDetailsTable.setBackground(new Color(0, 0, 41)); // Table background color
        purchaseDetailsTable.setForeground(Color.WHITE); // Table text color
        JScrollPane tableScroll = new JScrollPane(purchaseDetailsTable);

        // Buttons
        addButton = new JButton("Add Detail");
        updateButton = new JButton("Update Detail");
        deleteButton = new JButton("Delete Detail");
        saveButton = new JButton("Save Purchase");

        // Custom button colors
        addButton.setBackground(new Color(193, 143, 255)); // Button color
        addButton.setForeground(Color.WHITE); // Button text color
        updateButton.setBackground(new Color(193, 143, 255));
        updateButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(255, 0, 0));
        deleteButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(0, 255, 0));
        saveButton.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0, 0, 41)); // Background color of the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);

        // Adding components to the frame
        add(panel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(e -> addDetail());
        updateButton.addActionListener(e -> updateDetail());
        deleteButton.addActionListener(e -> deleteDetail());
        saveButton.addActionListener(e -> savePurchase());

        setVisible(true);
    }

    private void addDetail() {
        // Get data from input fields and add to the table
        String productId = JOptionPane.showInputDialog("Enter Product ID:");
        String quantity = JOptionPane.showInputDialog("Enter Quantity:");
        String price = JOptionPane.showInputDialog("Enter Price:");

        if (productId != null && quantity != null && price != null) {
            model.addRow(new Object[]{productId, quantity, price});
        }
    }

    private void updateDetail() {
        // Update selected row in the table
        int row = purchaseDetailsTable.getSelectedRow();
        if (row != -1) {
            String productId = JOptionPane.showInputDialog("Enter Product ID:", model.getValueAt(row, 0));
            String quantity = JOptionPane.showInputDialog("Enter Quantity:", model.getValueAt(row, 1));
            String price = JOptionPane.showInputDialog("Enter Price:", model.getValueAt(row, 2));

            model.setValueAt(productId, row, 0);
            model.setValueAt(quantity, row, 1);
            model.setValueAt(price, row, 2);
        }
    }

    private void deleteDetail() {
        // Delete selected row from the table
        int row = purchaseDetailsTable.getSelectedRow();
        if (row != -1) {
            model.removeRow(row);
        }
    }

    private void savePurchase() {
        // Save purchase and details to the database
        try {
            // Insert purchase
            String sql = "INSERT INTO Purchases (SupplierID, EmployeeID, TotalAmount, PurchaseDate) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, Integer.parseInt(supplierIdField.getText()));
            stmt.setInt(2, Integer.parseInt(employeeIdField.getText()));
            stmt.setDouble(3, Double.parseDouble(totalAmountField.getText()));
            stmt.setString(4, purchaseDateField.getText());
            stmt.executeUpdate();

            // Get the generated PurchaseID
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int purchaseId = generatedKeys.getInt(1);
                purchaseIdField.setText(String.valueOf(purchaseId));

                // Insert purchase details
                for (int i = 0; i < model.getRowCount(); i++) {
                    int productId = Integer.parseInt(model.getValueAt(i, 0).toString());
                    int quantity = Integer.parseInt(model.getValueAt(i, 1).toString());
                    double price = Double.parseDouble(model.getValueAt(i, 2).toString());

                    String detailSql = "INSERT INTO PurchaseDetails (PurchaseID, ProductID, Quantity, Price) VALUES (?, ?, ?, ?)";
                    PreparedStatement detailStmt = connection.prepareStatement(detailSql);
                    detailStmt.setInt(1, purchaseId);
                    detailStmt.setInt(2, productId);
                    detailStmt.setInt(3, quantity);
                    detailStmt.setDouble(4, price);
                    detailStmt.executeUpdate();
                }

                JOptionPane.showMessageDialog(this, "Purchase saved successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving purchase.");
        }
    }

    public static void main(String[] args) {
        new Purchase_Records();
    }
}
