package casher.gui;

import admin.code.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddCustomer extends JFrame {

    private static final String DB_URL = "jdbc:sqlite:new_file";

    private JTextField nameField;
    private JTextField emailField;
    private JTextField mobileField;
    private JTextField addressField;
    private JTextField loyaltyPointsField;

    public AddCustomer() {
        setTitle("Add Customer");
        getContentPane().setBackground(new Color(10, 25, 47));
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Add Customer", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(10, 25, 47));
        contentPanel.setLayout(new GridBagLayout());
        add(contentPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

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

        nameField = addField(detailsPanel, detailsGbc, "Name:", 0, 0, true);
        emailField = addField(detailsPanel, detailsGbc, "Email:", 0, 1, true);
        mobileField = addField(detailsPanel, detailsGbc, "Mobile No.:", 0, 2, true);
        addressField = addField(detailsPanel, detailsGbc, "Address:", 0, 3, true);
        loyaltyPointsField = addField(detailsPanel, detailsGbc, "LoyaltyPoints:", 0, 4, true);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        JButton addButton = new JButton("Add", ImageLoader.loadImage("/images/add.png")); // استخدم ImageLoader مباشرة
        addButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        addButton.setBackground(new Color(0, 102, 204));
        addButton.setForeground(Color.WHITE);
        contentPanel.add(addButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        backButton.setBackground(new Color(192, 57, 43));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(120, 30));
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel.setBackground(new Color(10, 25, 47));
        southPanel.add(backButton);
        add(southPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addCustomer());
        backButton.addActionListener(e -> dispose());

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

    private void addCustomer() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String mobile = mobileField.getText().trim();
        String address = addressField.getText().trim();
        String loyaltyPoints = loyaltyPointsField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || mobile.isEmpty() || address.isEmpty() || loyaltyPoints.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO customers (Name, Email, Phone, Address, LoyaltyPoints) VALUES (?, ?, ?, ?, ?)")) {

            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, mobile);
            statement.setString(4, address);
            statement.setString(5, loyaltyPoints);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Customer added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error while adding customer: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddCustomer frame = new AddCustomer();
            frame.setVisible(true);
        });
    }
}
