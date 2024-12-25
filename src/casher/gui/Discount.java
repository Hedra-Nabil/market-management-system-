package casher.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Discount extends JFrame {

    private static final String DATABASE_URL = "jdbc:sqlite:new_file";

    private JButton showActiveButton;
    private JTextArea outputArea;

    public Discount() {
        setTitle("Discount Manager");
        setSize(500, 600); // Frame size
        setLayout(new FlowLayout());
        setLocationRelativeTo(null); // Center the frame

        // Define colors
        Color frameColor = new Color(0, 0, 41); // Dark blue
        Color bgColor = new Color(218, 244, 255); // Light blue
        Color btnColor = new Color(193, 143, 255); // Button color
        Color textColor = new Color(0, 0, 0); // Text color

        // Initialize components
        showActiveButton = new JButton("Show Active Discounts");

        showActiveButton.setBackground(btnColor);
        showActiveButton.setForeground(Color.WHITE);

        outputArea = new JTextArea(15, 40);
        outputArea.setEditable(false);
        outputArea.setBackground(bgColor);
        outputArea.setForeground(textColor);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Add components to the frame
        add(showActiveButton);
        add(scrollPane);

        // Add action listeners
        showActiveButton.addActionListener(e -> showActiveDiscounts());

        getContentPane().setBackground(frameColor);
        showDiscounts(); // Initial display of discounts
    }

    private void showDiscounts() {
        StringBuilder output = new StringBuilder();
        String query = "SELECT * FROM Discounts";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                output.append("DiscountID: ").append(rs.getInt("DiscountID")).append("\n");
                output.append("ProductID: ").append(rs.getInt("ProductID")).append("\n");
                output.append("StartDate: ").append(rs.getString("StartDate")).append("\n");
                output.append("EndDate: ").append(rs.getString("EndDate")).append("\n");
                output.append("Percentage: ").append(rs.getDouble("Percentage")).append("\n");
                output.append("--------------------------\n");
            }

            outputArea.setText(output.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            showMessage("Error displaying discounts.");
        }
    }

    private void showActiveDiscounts() {
        StringBuilder output = new StringBuilder();
        String query = "SELECT * FROM Discounts WHERE EndDate >= CURRENT_DATE";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                output.append("DiscountID: ").append(rs.getInt("DiscountID")).append("\n");
                output.append("ProductID: ").append(rs.getInt("ProductID")).append("\n");
                output.append("StartDate: ").append(rs.getString("StartDate")).append("\n");
                output.append("EndDate: ").append(rs.getString("EndDate")).append("\n");
                output.append("Percentage: ").append(rs.getDouble("Percentage")).append("\n");
                output.append("--------------------------\n");
            }

            outputArea.setText(output.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            showMessage("Error displaying active discounts.");
        }
    }

    private void showMessage(String message) {
        outputArea.setText(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Discount().setVisible(true));
    }
}
