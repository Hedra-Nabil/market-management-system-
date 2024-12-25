package admin.gui;

import casher.code.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DeleteCustomerForm extends JFrame {

    private JTextField nameField;
    private JTable customerTable;
    private DefaultTableModel tableModel;

    public DeleteCustomerForm() {
        setTitle("Delete Customer Form");
        setResizable(false);
        setSize(800, 400);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0, 0, 35));

        // Title
        JLabel titleLabel = new JLabel("Delete Customer Form", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 0, 50));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = createDeleteCustomerForm();
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createDeleteCustomerForm() {
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(0, 0, 50));
        formPanel.setLayout(new BorderLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(0, 0, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel nameLabel = new JLabel("Customer Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);

        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 18));
        searchButton.setBackground(new Color(0, 128, 0));
        searchButton.setForeground(Color.WHITE);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 18));
        deleteButton.setBackground(new Color(128, 0, 0));
        deleteButton.setForeground(Color.WHITE);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 18));
        cancelButton.setBackground(new Color(0, 0, 128));
        cancelButton.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 2;
        inputPanel.add(searchButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        inputPanel.add(deleteButton, gbc);

        gbc.gridx = 1;
        inputPanel.add(cancelButton, gbc);

        formPanel.add(inputPanel, BorderLayout.NORTH);


        String[] columnNames = {"ID", "Name", "Mobile", "Address"};
        tableModel = new DefaultTableModel(columnNames, 0);
        customerTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(customerTable);
        formPanel.add(tableScrollPane, BorderLayout.CENTER);


        searchButton.addActionListener(e -> searchCustomer());
        deleteButton.addActionListener(e -> deleteCustomer());
        cancelButton.addActionListener(e -> dispose());

        return formPanel;
    }

    private void searchCustomer() {
        String customerName = nameField.getText().trim();
        if (customerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Customer Name!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:new_file");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM customers WHERE name LIKE ?")) {

            statement.setString(1, "%" + customerName + "%");
            ResultSet resultSet = statement.executeQuery();

            tableModel.setRowCount(0);

            boolean found = false;
            while (resultSet.next()) {
                found = true;
                Object[] row = {
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("mobile"),
                        resultSet.getString("address")
                };
                tableModel.addRow(row);
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "No customers found with the given name!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error while searching customer: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void deleteCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String customerId = tableModel.getValueAt(selectedRow, 0).toString();

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete customer ID: " + customerId + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            Customer customer = new Customer(null, null, null, null);
            if (customer.deleteCustomerById(customerId)) {
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Customer deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting customer!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DeleteCustomerForm().setVisible(true));
    }
}
