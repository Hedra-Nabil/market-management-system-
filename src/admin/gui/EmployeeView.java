package admin.gui;

import admin.code.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EmployeeView extends JFrame {
    private JTextField nameField;
    private JComboBox<String> positionComboBox;
    private JButton searchButton;
    private JTable employeeTable;
    private DefaultTableModel tableModel;

    private static final Color BUTTON_COLOR = new Color(65, 105, 225);
    private static final Color TEXT_COLOR = Color.BLACK;
    private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 14);

    public EmployeeView() {
        setTitle("Employee Management");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(0, 0, 50));


        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(new Color(0, 0, 50));

        nameField = new JTextField(15);
        positionComboBox = new JComboBox<>();
        positionComboBox.addItem("");
        searchButton = new JButton("Search");


        nameField.setFont(DEFAULT_FONT);
        positionComboBox.setFont(DEFAULT_FONT);
        searchButton.setFont(DEFAULT_FONT);
        searchButton.setBackground(BUTTON_COLOR);
        searchButton.setForeground(Color.WHITE);
        searchButton.setBorderPainted(false);
        searchButton.setFocusPainted(false);

        panel.add(new JLabel("Employee Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Position:"));
        panel.add(positionComboBox);
        panel.add(searchButton);


        String[] columns = {"EmployeeID", "Name", "Position", "Salary", "Hire Date", "Phone"};
        tableModel = new DefaultTableModel(columns, 0);
        employeeTable = new JTable(tableModel);
        employeeTable.setFont(DEFAULT_FONT);
        employeeTable.setBackground(Color.WHITE);
        employeeTable.setRowHeight(25);
        employeeTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        employeeTable.getTableHeader().setBackground(Color.WHITE);
        employeeTable.getTableHeader().setForeground(Color.BLACK);


        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setPreferredSize(new Dimension(950, 400));
        scrollPane.setBackground(new Color(0, 0, 50));


        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchEmployee();
            }
        });


        loadPositions();
    }


    private void loadPositions() {
        Employee employee = new Employee();
        try {
            ResultSet resultSet = employee.getPositions();
            while (resultSet.next()) {
                positionComboBox.addItem(resultSet.getString("Position"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error while loading positions: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }


    private void searchEmployee() {
        String employeeName = nameField.getText().trim();
        String position = positionComboBox.getSelectedItem().toString();

        Employee employee = new Employee();
        try {
            ResultSet resultSet = employee.searchEmployeeByNameAndPosition(employeeName, position);

            tableModel.setRowCount(0);

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
                tableModel.addRow(row);
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "No employees found with the given criteria!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error while searching employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EmployeeView().setVisible(true);
            }
        });
    }
}
