package casher.gui;

import admin.code.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateCustomer extends JFrame {

    private static final String DB_URL = "jdbc:sqlite:new_file";

    private JTextField nameField;
    private JTextField emailField;
    private JTextField mobileField;
    private JTextField addressField;
    private JTextField loyaltyPointsField;

    public UpdateCustomer() {
        setTitle("Update Customer");
        getContentPane().setBackground(new Color(10, 25, 47));
        setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Update Customer", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(10, 25, 47));
        contentPanel.setLayout(new GridBagLayout());
        add(contentPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Customer Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        JLabel customerNameLabel = new JLabel("Customer Name:");
        customerNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        customerNameLabel.setForeground(Color.WHITE);
        contentPanel.add(customerNameLabel, gbc);

        gbc.gridx = 1;
        JTextField customerNameField = new JTextField(20);
        customerNameField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        contentPanel.add(customerNameField, gbc);

        gbc.gridx = 2;
        JButton checkButton = new JButton("Check", ImageLoader.loadImage("/images/check-mark.png"));
        checkButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        checkButton.setBackground(new Color(0, 153, 76));
        checkButton.setForeground(Color.WHITE);
        checkButton.setPreferredSize(new Dimension(150, 40));
        contentPanel.add(checkButton, gbc);

        // Customer details panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridBagLayout());
        detailsPanel.setBackground(new Color(67, 84, 138));
        contentPanel.add(detailsPanel, gbc);

        GridBagConstraints detailsGbc = new GridBagConstraints();
        detailsGbc.insets = new Insets(5, 5, 5, 5);
        detailsGbc.fill = GridBagConstraints.HORIZONTAL;

        // Fields in details panel
        nameField = addField(detailsPanel, detailsGbc, "Name:", 0, 0, true);
        emailField = addField(detailsPanel, detailsGbc, "Email:", 0, 1, true);
        mobileField = addField(detailsPanel, detailsGbc, "Mobile No.:", 0, 2, true);
        addressField = addField(detailsPanel, detailsGbc, "Address:", 0, 3, true);
        loyaltyPointsField = addField(detailsPanel, detailsGbc, "LoyaltyPoints:", 0, 4, true);

        // Update button
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        JButton updateButton = new JButton("Update", ImageLoader.loadImage("/images/update-icon.png"));
        updateButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        updateButton.setBackground(new Color(0, 102, 204));
        updateButton.setForeground(Color.WHITE);
        contentPanel.add(updateButton, gbc);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        backButton.setBackground(new Color(192, 57, 43));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(120, 30));
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel.setBackground(new Color(10, 25, 47));
        southPanel.add(backButton);
        add(southPanel, BorderLayout.SOUTH);

        // Action Listeners
        checkButton.addActionListener(e -> checkCustomer(customerNameField));
        updateButton.addActionListener(e -> updateCustomer(customerNameField));
        backButton.addActionListener(e -> dispose());

        // Frame settings
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private JTextField addField(JPanel panel, GridBagConstraints gbc, String label, int x, int y, boolean isEditable) {
        gbc.gridx = x;
        gbc.gridy = y;
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        jLabel.setForeground(Color.WHITE);
        panel.add(jLabel, gbc);

        gbc.gridx = x + 1;
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        textField.setEditable(isEditable);
        panel.add(textField, gbc);

        return textField;
    }

    private void checkCustomer(JTextField customerNameField) {
        String customerName = customerNameField.getText().trim();
        if (customerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Customer Name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM customers WHERE Name = ?")) {

            statement.setString(1, customerName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nameField.setText(resultSet.getString("Name"));
                emailField.setText(resultSet.getString("Email"));
                mobileField.setText(resultSet.getString("Phone"));
                addressField.setText(resultSet.getString("Address"));
                loyaltyPointsField.setText(resultSet.getString("LoyaltyPoints"));
                JOptionPane.showMessageDialog(this, "Customer data loaded.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Customer not found.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error while checking customer: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCustomer(JTextField customerNameField) {
        String customerName = customerNameField.getText().trim();
        if (customerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Customer Name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE customers SET Email = ?, Phone = ?, Address = ?, LoyaltyPoints = ? WHERE Name = ?")) {

            statement.setString(1, emailField.getText().trim());
            statement.setString(2, mobileField.getText().trim());
            statement.setString(3, addressField.getText().trim());
            statement.setString(4, loyaltyPointsField.getText().trim());
            statement.setString(5, customerName);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Customer data updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No customer found to update.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error while updating customer: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UpdateCustomer frame = new UpdateCustomer();
            frame.setVisible(true);
        });
    }
}
