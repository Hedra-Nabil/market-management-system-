package admin.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EmployeeSupplierView extends JFrame {

    private JTable employeeTable;
    private JTable supplierTable;
    private JTextField employeeSearchField;
    private JTextField supplierSearchField;

    private Connection connection;

    public EmployeeSupplierView() {
        setTitle("Employee and Supplier Viewer");
       // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);


        connectToDatabase();


        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);


        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainPanel.add(topPanel, BorderLayout.NORTH);


        JPanel employeePanel = new JPanel(new BorderLayout());
        JLabel employeeLabel = new JLabel("Search Employee:");
        employeeSearchField = new JTextField(15);
        JButton employeeSearchButton = new JButton("Search");
        JPanel employeeSearchPanel = new JPanel();
        employeeSearchPanel.add(employeeLabel);
        employeeSearchPanel.add(employeeSearchField);
        employeeSearchPanel.add(employeeSearchButton);
        employeePanel.add(employeeSearchPanel, BorderLayout.NORTH);


        employeeTable = new JTable(new DefaultTableModel(
                new Object[][]{}, new String[]{"ID", "Name", "Position"}
        ));
        employeePanel.add(new JScrollPane(employeeTable), BorderLayout.CENTER);

        topPanel.add(employeePanel);


        JPanel supplierPanel = new JPanel(new BorderLayout());
        JLabel supplierLabel = new JLabel("Search Supplier:");
        supplierSearchField = new JTextField(15);
        JButton supplierSearchButton = new JButton("Search");
        JPanel supplierSearchPanel = new JPanel();
        supplierSearchPanel.add(supplierLabel);
        supplierSearchPanel.add(supplierSearchField);
        supplierSearchPanel.add(supplierSearchButton);
        supplierPanel.add(supplierSearchPanel, BorderLayout.NORTH);


        supplierTable = new JTable(new DefaultTableModel(
                new Object[][]{}, new String[]{"ID", "Name", "Email"}
        ));
        supplierPanel.add(new JScrollPane(supplierTable), BorderLayout.CENTER);

        topPanel.add(supplierPanel);


        JPanel bottomPanel = new JPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        JButton showAllButton = new JButton("Show All");
        JButton clearButton = new JButton("Clear Tables");
        JButton exitButton = new JButton("Exit");

        bottomPanel.add(showAllButton);
        bottomPanel.add(clearButton);
        bottomPanel.add(exitButton);


        employeeSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchEmployee();
            }
        });

        supplierSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchSupplier();
            }
        });

        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAllData();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTables();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }


    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:new_file");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error connecting to database: " + e.getMessage());
        }
    }


    private void searchEmployee() {
        String searchValue = employeeSearchField.getText();
        String query = "SELECT * FROM employees WHERE name LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + searchValue + "%");
            ResultSet rs = statement.executeQuery();
            DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id"), rs.getString("name"), rs.getString("department")});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error searching employees: " + e.getMessage());
        }
    }


    private void searchSupplier() {
        String searchValue = supplierSearchField.getText();
        String query = "SELECT * FROM suppliers WHERE name LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + searchValue + "%");
            ResultSet rs = statement.executeQuery();
            DefaultTableModel model = (DefaultTableModel) supplierTable.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id"), rs.getString("name"), rs.getString("company")});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error searching suppliers: " + e.getMessage());
        }
    }


    private void showAllData() {
        String employeeQuery = "SELECT * FROM employees";
        String supplierQuery = "SELECT * FROM suppliers";
        try {

            try (PreparedStatement statement = connection.prepareStatement(employeeQuery);
                 ResultSet rs = statement.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
                model.setRowCount(0);
                while (rs.next()) {
                    model.addRow(new Object[]{rs.getInt("EmployeeID"), rs.getString("Name"), rs.getString("Position")});
                }
            }

            try (PreparedStatement statement = connection.prepareStatement(supplierQuery);
                 ResultSet rs = statement.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) supplierTable.getModel();
                model.setRowCount(0);
                while (rs.next()) {
                    model.addRow(new Object[]{rs.getInt("SupplierID"), rs.getString("Name"), rs.getString("Email")});
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error showing data: " + e.getMessage());
        }
    }


    private void clearTables() {
        ((DefaultTableModel) employeeTable.getModel()).setRowCount(0);
        ((DefaultTableModel) supplierTable.getModel()).setRowCount(0);
    }

    public static void main(String[] args) {
        new EmployeeSupplierView();
    }
}
