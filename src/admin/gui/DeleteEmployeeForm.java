package admin.gui;

import admin.code.deleteEmployee;
import admin.code.deleteEmployee;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class DeleteEmployeeForm extends JFrame {

    private JTextField nameField;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private Connection connection;

    public DeleteEmployeeForm() {
        setTitle("Delete Employee Form");
        setResizable(false);
        setSize(800, 400);
        setLocationRelativeTo(null);

        // إنشاء الاتصال بقاعدة البيانات
        //connectToDatabase();

        // اللوحة الرئيسية
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0, 0, 35));

        // عنوان النافذة
        JLabel titleLabel = new JLabel("Delete Employee Form", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 0, 50));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // لوحة الفورم
        JPanel formPanel = createDeleteEmployeeForm();
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createDeleteEmployeeForm() {
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(0, 0, 50));
        formPanel.setLayout(new BorderLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // لوحة الإدخال
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(0, 0, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel nameLabel = new JLabel("Employee Name:");
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

        // ترتيب الحقول
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

        // إعداد الجدول
        String[] columnNames = {"ID", "Name", "Position", "Salary", "Hire Date", "Phone"};
        tableModel = new DefaultTableModel(columnNames, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(employeeTable);
        formPanel.add(tableScrollPane, BorderLayout.CENTER);

        // إضافة الأحداث
        searchButton.addActionListener(e -> searchEmployee());
        deleteButton.addActionListener(e -> deleteEmployee());
        cancelButton.addActionListener(e -> dispose());

        return formPanel;
    }



    private void searchEmployee() {
        String employeeName = nameField.getText().trim();
        if (employeeName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Employee Name!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        deleteEmployee deleteEmployee = new deleteEmployee();
        try {
            ResultSet resultSet = deleteEmployee.searchEmployeeByName(employeeName);

            tableModel.setRowCount(0); // مسح البيانات السابقة

            boolean found = false;
            while (resultSet.next()) {
                found = true;
                Object[] row = {
                        resultSet.getInt("EmployeeID"),
                        resultSet.getString("Name"),
                        resultSet.getString("Position"),
                        resultSet.getDouble("Salary"),
                        resultSet.getString("HireDate"),
                        resultSet.getString("Phone")
                };
                tableModel.addRow(row); // إضافة البيانات الجديدة
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "No employees found with the given name!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error while searching employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void deleteEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String employeeIdStr = tableModel.getValueAt(selectedRow, 0).toString();
        int employeeId = Integer.parseInt(employeeIdStr);

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete employee ID: " + employeeId + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            deleteEmployee deleteEmployee = new deleteEmployee();
            try {
                if (deleteEmployee.deleteEmployeeById(employeeId)) {
                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error while deleting employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DeleteEmployeeForm().setVisible(true));
    }
}