package admin.gui;

import admin.code.addEmployee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;


public class AddEmployeeForm extends JFrame {


    private JTextField nameField, positionField, salaryField, hireDateField, phoneField;

    public AddEmployeeForm() {
        setTitle("Form Add Employee");
        setResizable(false);
        setSize(800, 500);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0, 0, 50));



        JLabel titleLabel = new JLabel("Add Employee Form", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 0, 50));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
       // headerPanel.add(timeLabel, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = createEmployeeForm();
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);

    }

    private JPanel createEmployeeForm() {
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(0, 0, 50));
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // الحقول
        JLabel nameLabel = new JLabel("Employee Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);

        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel positionLabel = new JLabel("Position:");
        positionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        positionLabel.setForeground(Color.WHITE);

        positionField = new JTextField(20);
        positionField.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel salaryLabel = new JLabel("Salary:");
        salaryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        salaryLabel.setForeground(Color.WHITE);

        salaryField = new JTextField(20);
        salaryField.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel hireDateLabel = new JLabel("Hire Date (YYYY-MM-DD):");
        hireDateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        hireDateLabel.setForeground(Color.WHITE);

        hireDateField = new JTextField(20);
        hireDateField.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 16));
        phoneLabel.setForeground(Color.WHITE);

        phoneField = new JTextField(20);
        phoneField.setFont(new Font("Arial", Font.PLAIN, 14));

        // الأزرار
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this::saveEmployee);
        saveButton.setForeground(new Color(224, 224, 224));
        saveButton.setBackground(new Color(128, 128, 192));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        cancelButton.setForeground(new Color(224, 224, 224));
        cancelButton.setBackground(new Color(235, 0, 0));

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetFields());
        resetButton.setForeground(new Color(224, 224, 224));
        resetButton.setBackground(new Color(128, 128, 192));

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(positionLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(positionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(salaryLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(salaryField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(hireDateLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(hireDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(phoneLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(saveButton, gbc);

        gbc.gridx = 1;
        formPanel.add(cancelButton, gbc);

        gbc.gridx = 2;
        formPanel.add(resetButton, gbc);

        return formPanel;
    }

    private void saveEmployee(ActionEvent e) {
        String name = nameField.getText().trim();
        String position = positionField.getText().trim();
        String salary = salaryField.getText().trim();
        String hireDate = hireDateField.getText().trim();
        String phone = phoneField.getText().trim();


        if (name.isEmpty() || position.isEmpty() || salary.isEmpty() || hireDate.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        addEmployee db = new addEmployee ();
        try {
            if (db.addEmp(name, position, salary, hireDate, phone)) {
                JOptionPane.showMessageDialog(this, "Employee saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                nameField.setText("");
                positionField.setText("");
                salaryField.setText("");
                hireDateField.setText("");
                phoneField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Employee already exists!", "Duplicate", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saving employee: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void resetFields() {
        nameField.setText("");
        positionField.setText("");
        salaryField.setText("");
        hireDateField.setText("");
        phoneField.setText("");
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddEmployeeForm().setVisible(true));
    }
}
