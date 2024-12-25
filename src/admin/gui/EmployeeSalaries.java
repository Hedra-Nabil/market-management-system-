package admin.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class EmployeeSalaries extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> sortComboBox;
    private JTextField searchField;
    private JButton searchButton;
    private JButton clearButton;
    private JLabel totalSalaryLabel;

    public EmployeeSalaries() {
        setTitle("Employee Salaries");
        setResizable(false);
        setSize(1100, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0, 0, 35));


        JLabel titleLabel = new JLabel("Employee Salaries", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 0, 50));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);


        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(50, 50, 70));
        tableModel = new DefaultTableModel(new String[]{"Employee ID", "Name", "Position", "Salary", "Hire Date"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.CENTER);


        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(50, 50, 70));
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));


        searchField = new JTextField(15);
        searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setBackground(new Color(0, 128, 128));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(e -> searchEmployee());


        JButton showAllButton = new JButton("Show All");
        showAllButton.setFont(new Font("Arial", Font.BOLD, 14));
        showAllButton.setBackground(new Color(0, 128, 128));
        showAllButton.setForeground(Color.WHITE);
        showAllButton.addActionListener(e -> loadAllSalaries());


        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setBackground(new Color(246, 74, 74));
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(e -> loadAllSalaries());
        controlPanel.add(new JLabel("Search by Name:"));
        controlPanel.add(searchField);
        controlPanel.add(searchButton);
        controlPanel.add(showAllButton);
        controlPanel.add(clearButton);


        sortComboBox = new JComboBox<>(new String[]{
                "Sort by Salary (Ascending)",
                "Sort by Salary (Descending)",
                "Sort by Hire Date (Ascending)",
                "Sort by Position (A-Z)"
        });
        sortComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        sortComboBox.addActionListener(e -> sortTable());
        controlPanel.add(new JLabel("Sort:"));
        controlPanel.add(sortComboBox);

        mainPanel.add(controlPanel, BorderLayout.SOUTH);


        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(new Color(40, 40, 60));
        sidePanel.setLayout(new BorderLayout());
        sidePanel.setPreferredSize(new Dimension(200, 600));

        JLabel sidePanelTitle = new JLabel("Summary", JLabel.CENTER);
        sidePanelTitle.setFont(new Font("Arial", Font.BOLD, 20));
        sidePanelTitle.setForeground(Color.WHITE);
        sidePanelTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        sidePanel.add(sidePanelTitle, BorderLayout.NORTH);

        totalSalaryLabel = new JLabel("Total Salary: $0.00", JLabel.CENTER);
        totalSalaryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        totalSalaryLabel.setForeground(Color.WHITE);
        sidePanel.add(totalSalaryLabel, BorderLayout.CENTER);

        mainPanel.add(sidePanel, BorderLayout.EAST);

        add(mainPanel);


        loadAllSalaries();
    }

    private void loadAllSalaries() {
        loadData("SELECT * FROM Employees");
    }

    private void searchEmployee() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name to search.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String query = "SELECT * FROM Employees WHERE Name LIKE ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:new_file");
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + searchTerm + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                updateTable(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable(ResultSet rs) throws SQLException {
        tableModel.setRowCount(0);

        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            row.add(rs.getInt("EmployeeID"));
            row.add(rs.getString("Name"));
            row.add(rs.getString("Position"));
            row.add(rs.getDouble("Salary"));
            row.add(rs.getString("HireDate"));
            tableModel.addRow(row);
        }
    }

    private void sortTable() {
        String selectedSort = (String) sortComboBox.getSelectedItem();
        String query = "SELECT * FROM Employees";

        switch (selectedSort) {
            case "Sort by Salary (Ascending)":
                query += " ORDER BY Salary ASC";
                break;
            case "Sort by Salary (Descending)":
                query += " ORDER BY Salary DESC";
                break;
            case "Sort by Hire Date (Ascending)":
                query += " ORDER BY HireDate ASC";
                break;
            case "Sort by Position (A-Z)":
                query += " ORDER BY Position ASC";
                break;
        }

        loadData(query);
    }

    private void loadData(String query) {
        tableModel.setRowCount(0);
        double totalSalary = 0.0;

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:new_file");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("EmployeeID"));
                row.add(rs.getString("Name"));
                row.add(rs.getString("Position"));
                row.add(rs.getDouble("Salary"));
                row.add(rs.getString("HireDate"));

                totalSalary += rs.getDouble("Salary");
                tableModel.addRow(row);
            }

            totalSalaryLabel.setText(String.format("Total Salary: $%.2f", totalSalary));

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeeSalaries().setVisible(true));
    }
}
