package admin.gui;

import admin.code.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UserAccountView extends JFrame {
    private JTextField nameField;
    private JComboBox<String> positionComboBox;
    private JButton searchButton, createUserButton, deleteUserButton;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JTextField usernameField, passwordField;
    private JComboBox<String> roleComboBox;
    private int selectedEmployeeID = 0;
    private JComboBox<String> dataSourceComboBox;

    private static final Color BUTTON_COLOR = new Color(65, 105, 225);
    private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 14);

    public UserAccountView() {
        setTitle("User Account Management");
        setSize(1000, 800);
        setLocationRelativeTo(null);
       // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(0, 0, 50));

        // إعداد اللوحة العلوية
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(new Color(0, 0, 50));

        nameField = new JTextField(15);
        positionComboBox = new JComboBox<>();
        positionComboBox.addItem(""); // خيار افتراضي فارغ
        searchButton = createButton("Search");

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JTextField(15);
        JLabel roleLabel = new JLabel("Role:");
        roleComboBox = new JComboBox<>(new String[]{"Manager", "Warehouse", "Cashier"});

        createUserButton = createButton("Create User");

        // ComboBox لاختيار مصدر البيانات
        dataSourceComboBox = new JComboBox<>(new String[]{"Employees", "User Accounts"});
        dataSourceComboBox.setFont(DEFAULT_FONT);

        panel.add(new JLabel("Employee Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Position:"));
        panel.add(positionComboBox);
        panel.add(new JLabel("Data Source:"));
        panel.add(dataSourceComboBox);
        panel.add(searchButton);

        // إعداد الجدول
        String[] columns = {"EmployeeID", "Name", "Position", "Salary", "Hire Date", "Phone"};
        tableModel = new DefaultTableModel(columns, 0);
        employeeTable = new JTable(tableModel);
        styleTable(employeeTable);

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setPreferredSize(new Dimension(950, 400));

        // إعداد الحقول والأزرار السفلية
        JPanel userPanel = new JPanel(new FlowLayout());
        userPanel.setBackground(new Color(0, 0, 50));
        userPanel.add(usernameLabel);
        userPanel.add(usernameField);
        userPanel.add(passwordLabel);
        userPanel.add(passwordField);
        userPanel.add(roleLabel);
        userPanel.add(roleComboBox);
        userPanel.add(createUserButton);

        deleteUserButton = createButton("Delete User");
        deleteUserButton.setEnabled(false);
        userPanel.add(deleteUserButton);

        // ترتيب المكونات
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(userPanel, BorderLayout.SOUTH);

        // الأحداث
        searchButton.addActionListener(e -> searchEmployeeOrUser());
        createUserButton.addActionListener(e -> createUserAccount());
        deleteUserButton.addActionListener(e -> deleteUserAccount());
        employeeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = employeeTable.getSelectedRow();
                if (row != -1) {
                    selectedEmployeeID = (int) employeeTable.getValueAt(row, 0);
                    deleteUserButton.setEnabled(true);
                }
            }
        });

        loadPositions();
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(DEFAULT_FONT);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void styleTable(JTable table) {
        table.setFont(DEFAULT_FONT);
        table.setBackground(Color.WHITE);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(BUTTON_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
    }

    private void loadPositions() {
        Employee employee = new Employee();
        try {
            ResultSet resultSet = employee.getPositions();
            while (resultSet.next()) {
                positionComboBox.addItem(resultSet.getString("Position"));
            }
        } catch (SQLException ex) {
            showError("Error while loading positions: " + ex.getMessage());
        }
    }

    private void searchEmployeeOrUser() {
        String dataSource = (String) dataSourceComboBox.getSelectedItem();
        if ("Employees".equals(dataSource)) {
            searchEmployee();
        } else if ("User Accounts".equals(dataSource)) {
            searchUserAccounts();
        }
    }

    private void searchEmployee() {
        String name = nameField.getText().trim();
        String position = (String) positionComboBox.getSelectedItem();
        String dataSource = (String) dataSourceComboBox.getSelectedItem();

        try {
            Employee employee = new Employee();
            ResultSet resultSet;

            if ("Employees".equals(dataSource)) {
                // عرض جميع الموظفين بناءً على الاسم والوظيفة
                resultSet = employee.searchEmployeeByNameAndPosition(name, position);
            } else if ("User Accounts".equals(dataSource)) {
                // عرض الموظفين الذين لديهم حسابات فقط
                resultSet = employee.searchUsersWithAccounts(name);
            } else {
                showError("Invalid data source selected.");
                return;
            }

            // تحديث الجدول بالبيانات
            tableModel.setRowCount(0); // مسح البيانات القديمة
            while (resultSet.next()) {
                tableModel.addRow(new Object[]{
                        resultSet.getInt("EmployeeID"),
                        resultSet.getString("Name"),
                        resultSet.getString("Position"),
                        resultSet.getDouble("Salary"),
                        resultSet.getString("HireDate"),
                        resultSet.getString("Phone")
                });
            }
        } catch (SQLException ex) {
            showError("Error while searching employees: " + ex.getMessage());
        }
    }


    private void searchUserAccounts() {
        String employeeName = nameField.getText().trim();

        Employee employee = new Employee();
        try {
            ResultSet resultSet = employee.searchUsersWithAccounts(employeeName);
            populateTable(resultSet);
        } catch (SQLException ex) {
            showError("Error while searching user accounts: " + ex.getMessage());
        }
    }



    private void populateTable(ResultSet resultSet) throws SQLException {
        tableModel.setRowCount(0);
        while (resultSet != null && resultSet.next()) {
            Object[] row = {
                    resultSet.getInt("EmployeeID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Position"),
                    resultSet.getDouble("Salary"),
                    resultSet.getString("HireDate"),
                    resultSet.getString("Phone")
            };
            tableModel.addRow(row);
        }

        if (tableModel.getRowCount() == 0) {
            showError("No records found with the given criteria!");
        }
    }

    private void createUserAccount() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String role = (String) roleComboBox.getSelectedItem();

        if (selectedEmployeeID != 0) {
            Employee employee = new Employee();
            try {
                boolean success = employee.createUser(username, password, role, selectedEmployeeID);
                showInfo(success ? "User account created successfully!" : "Error while creating user account!");
            } catch (SQLException ex) {
                showError("Error while creating user account: " + ex.getMessage());
            }
        } else {
            showError("Please select an employee first!");
        }
    }

    private void deleteUserAccount() {
        if (selectedEmployeeID != 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user account?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Employee employee = new Employee();
                try {
                    boolean success = employee.deleteUserAccount(selectedEmployeeID);
                    showInfo(success ? "User account deleted successfully!" : "Error while deleting user account!");
                    if (success) {
                        tableModel.setRowCount(0);
                    }
                } catch (SQLException ex) {
                    showError("Error while deleting user account: " + ex.getMessage());
                }
            }
        } else {
            showError("Please select an employee first!");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserAccountView().setVisible(true));
    }
}
