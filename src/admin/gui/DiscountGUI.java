package admin.gui;

import admin.code.storemanagerhome_f;


import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscountGUI extends JFrame {

    private storemanagerhome_f storemanagerhome_f;

    private JTextField productIDField, startDateField, endDateField, percentageField, discountIDField;
    private JTextArea outputArea;

    public DiscountGUI() {
        storemanagerhome_f = new storemanagerhome_f();
        setTitle("Discount Manager");
        setSize(500, 600);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        Color frameColor = new Color(0, 0, 41);
        Color bgColor = new Color(218, 244, 255);
        Color btnColor = new Color(193, 143, 255);

        productIDField = new JTextField(20);
        startDateField = new JTextField(20);
        endDateField = new JTextField(20);
        percentageField = new JTextField(20);
        discountIDField = new JTextField(20);

        productIDField.setBorder(BorderFactory.createTitledBorder("Product ID"));
        startDateField.setBorder(BorderFactory.createTitledBorder("Start Date (yyyy-mm-dd)"));
        endDateField.setBorder(BorderFactory.createTitledBorder("End Date (yyyy-mm-dd)"));
        percentageField.setBorder(BorderFactory.createTitledBorder("Discount Percentage"));
        discountIDField.setBorder(BorderFactory.createTitledBorder("Discount ID to Delete"));

        JButton addButton = new JButton("Add Discount");
        JButton updateButton = new JButton("Update Discount");
        JButton deleteButton = new JButton("Delete Discount");
        JButton showActiveButton = new JButton("Show Active Discounts");

        addButton.setBackground(btnColor);
        updateButton.setBackground(btnColor);
        deleteButton.setBackground(btnColor);
        showActiveButton.setBackground(btnColor);

        outputArea = new JTextArea(15, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(productIDField);
        add(startDateField);
        add(endDateField);
        add(percentageField);
        add(addButton);
        add(updateButton);
        add(showActiveButton);
        add(discountIDField);
        add(deleteButton);
        add(scrollPane);

        addButton.addActionListener(e -> addDiscount());
        updateButton.addActionListener(e -> updateDiscount());
        deleteButton.addActionListener(e -> deleteDiscount());
        showActiveButton.addActionListener(e -> showActiveDiscounts());

        getContentPane().setBackground(frameColor);
        showDiscounts();
    }

    private void addDiscount() {
        try {
            storemanagerhome_f.addDiscount(
                    Integer.parseInt(productIDField.getText()),
                    startDateField.getText(),
                    endDateField.getText(),
                    Double.parseDouble(percentageField.getText())
            );
            showMessage("Discount added successfully.");
            showDiscounts();
        } catch (SQLException e) {
            showMessage("Error: " + e.getMessage());
        }
    }

    private void updateDiscount() {
        try {
            storemanagerhome_f.updateDiscount(
                    Integer.parseInt(productIDField.getText()),
                    startDateField.getText(),
                    endDateField.getText(),
                    Double.parseDouble(percentageField.getText())
            );
            showMessage("Discount updated successfully.");
            showDiscounts();
        } catch (SQLException e) {
            showMessage("Error: " + e.getMessage());
        }
    }

    private void deleteDiscount() {
        try {
            storemanagerhome_f.deleteDiscount(Integer.parseInt(discountIDField.getText()));
            showMessage("Discount deleted successfully.");
            showDiscounts();
        } catch (SQLException e) {
            showMessage("Error: " + e.getMessage());
        }
    }

    private void showDiscounts() {
        try {
            ResultSet rs = storemanagerhome_f.getAllDiscounts();
            displayResults(rs);
        } catch (SQLException e) {
            showMessage("Error: " + e.getMessage());
        }
    }

    private void showActiveDiscounts() {
        try {
            ResultSet rs = storemanagerhome_f.getActiveDiscounts();
            displayResults(rs);
        } catch (SQLException e) {
            showMessage("Error: " + e.getMessage());
        }
    }

    private void displayResults(ResultSet rs) throws SQLException {
        StringBuilder output = new StringBuilder();
        while (rs.next()) {
            output.append("DiscountID: ").append(rs.getInt("DiscountID")).append("\n")
                    .append("ProductID: ").append(rs.getInt("ProductID")).append("\n")
                    .append("StartDate: ").append(rs.getString("StartDate")).append("\n")
                    .append("EndDate: ").append(rs.getString("EndDate")).append("\n")
                    .append("Percentage: ").append(rs.getDouble("Percentage")).append("\n")
                    .append("--------------------------\n");
        }
        outputArea.setText(output.toString());
    }

    private void showMessage(String message) {
        outputArea.setText(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DiscountGUI().setVisible(true));
    }
}
