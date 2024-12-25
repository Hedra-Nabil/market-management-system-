package admin.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.table.TableModel;
import java.awt.print.*;

import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;

public class OperationsReport extends JFrame {
    private JLabel fromDateLabel, toDateLabel, rowCountLabel, salesRowCountLabel, purchasesRowCountLabel;
    private JComboBox<String> fromDayComboBox, fromMonthComboBox, fromYearComboBox;
    private JComboBox<String> toDayComboBox, toMonthComboBox, toYearComboBox;
    private JButton printButton, clearButton, exitButton, generateButton;
    private JTable salesTable, purchasesTable;
    private JPanel mainPanel, tablePanel, buttonPanel, rowCountPanel, datePanel;

    public OperationsReport() {
        setTitle("Operations Report");
        setSize(1000, 700);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(40, 45, 50);
        Color headerColor = new Color(70, 130, 180);
        Color textColor = Color.WHITE;
        Color buttonColor = new Color(0, 123, 255);

        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(backgroundColor);
        add(mainPanel);

        JLabel titleLabel = new JLabel("Operations Report", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(headerColor);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        datePanel = new JPanel();
        datePanel.setBackground(backgroundColor);

        fromDateLabel = new JLabel("From Date:");
        toDateLabel = new JLabel("To Date:");
        fromDateLabel.setForeground(textColor);
        toDateLabel.setForeground(textColor);

        fromDayComboBox = new JComboBox<>(generateDays());
        fromMonthComboBox = new JComboBox<>(generateMonths());
        fromYearComboBox = new JComboBox<>(generateYears());

        toDayComboBox = new JComboBox<>(generateDays());
        toMonthComboBox = new JComboBox<>(generateMonths());
        toYearComboBox = new JComboBox<>(generateYears());

        datePanel.add(fromDateLabel);
        datePanel.add(fromDayComboBox);
        datePanel.add(fromMonthComboBox);
        datePanel.add(fromYearComboBox);

        datePanel.add(toDateLabel);
        datePanel.add(toDayComboBox);
        datePanel.add(toMonthComboBox);
        datePanel.add(toYearComboBox);

        mainPanel.add(datePanel, BorderLayout.NORTH);

        tablePanel = new JPanel(new GridLayout(1, 2, 10, 10));
        tablePanel.setBackground(backgroundColor);

        salesTable = new JTable();
        purchasesTable = new JTable();

        JScrollPane salesScroll = new JScrollPane(salesTable);
        JScrollPane purchasesScroll = new JScrollPane(purchasesTable);

        tablePanel.add(salesScroll);
        tablePanel.add(purchasesScroll);

        mainPanel.add(tablePanel, BorderLayout.CENTER);

        rowCountPanel = new JPanel();
        rowCountPanel.setBackground(backgroundColor);

        salesRowCountLabel = new JLabel("Sales Count: 0");
        purchasesRowCountLabel = new JLabel("Purchases Count: 0");
        salesRowCountLabel.setForeground(textColor);
        purchasesRowCountLabel.setForeground(textColor);
        rowCountPanel.add(salesRowCountLabel);
        rowCountPanel.add(purchasesRowCountLabel);

        mainPanel.add(rowCountPanel, BorderLayout.SOUTH);

        buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);

        generateButton = new JButton("Generate");
        generateButton.setBackground(buttonColor);
        generateButton.setForeground(textColor);
        generateButton.setFocusPainted(false);
        generateButton.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPanel.add(generateButton);

        printButton = new JButton("Print");
        printButton.setBackground(buttonColor);
        printButton.setForeground(textColor);
        printButton.setFocusPainted(false);
        printButton.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPanel.add(printButton);

        clearButton = new JButton("Clear");
        clearButton.setBackground(buttonColor);
        clearButton.setForeground(textColor);
        clearButton.setFocusPainted(false);
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPanel.add(clearButton);

        exitButton = new JButton("Exit");
        exitButton.setBackground(buttonColor);
        exitButton.setForeground(textColor);
        exitButton.setFocusPainted(false);
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        generateButton.addActionListener(e -> loadReportData());
        printButton.addActionListener(e -> printReport(salesTable ,purchasesTable));
        clearButton.addActionListener(e -> clearTables());
        exitButton.addActionListener(e -> System.exit(0));

        loadReportData();
    }

    private String[] generateDays() {
        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.format("%02d", i + 1);
        }
        return days;
    }

    private String[] generateMonths() {
        return new String[]{
                "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
        };
    }

    private String[] generateYears() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String[] years = new String[12];
        for (int i = 0; i < 12; i++) {
            years[i] = String.valueOf(currentYear - 10 + i);
        }
        return years;
    }


    private void loadReportData() {
        String fromDate = fromYearComboBox.getSelectedItem() + "-" +
                (fromMonthComboBox.getSelectedIndex() + 1) + "-" +
                fromDayComboBox.getSelectedItem();

        String toDate = toYearComboBox.getSelectedItem() + "-" +
                (toMonthComboBox.getSelectedIndex() + 1) + "-" +
                toDayComboBox.getSelectedItem();


        if (toDate.equals("-1-")) {
            toDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:new_file")) {

            String salesQuery = "SELECT SaleID, TotalAmount, SaleDate FROM Sales WHERE SaleDate BETWEEN ? AND ?";
            PreparedStatement stmt = conn.prepareStatement(salesQuery);
            stmt.setString(1, fromDate);
            stmt.setString(2, toDate);
            ResultSet salesResult = stmt.executeQuery();
            salesTable.setModel(buildTableModel(salesResult));


            String purchasesQuery = "SELECT PurchaseID, TotalAmount, PurchaseDate FROM Purchases WHERE PurchaseDate BETWEEN ? AND ?";
            stmt = conn.prepareStatement(purchasesQuery);
            stmt.setString(1, fromDate);
            stmt.setString(2, toDate);
            ResultSet purchasesResult = stmt.executeQuery();
            purchasesTable.setModel(buildTableModel(purchasesResult));


            int salesCount = salesTable.getRowCount();
            int purchasesCount = purchasesTable.getRowCount();
            salesRowCountLabel.setText("Sales Count: " + salesCount);
            purchasesRowCountLabel.setText("Purchases Count: " + purchasesCount);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage());
        }
    }


    private static TableModel buildTableModel(ResultSet rs) throws SQLException {
        java.sql.ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnNames[i] = metaData.getColumnLabel(i + 1);
        }
        java.util.List<String[]> data = new java.util.ArrayList<>();
        while (rs.next()) {
            String[] row = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                row[i] = rs.getString(i + 1);
            }
            data.add(row);
        }
        return new DefaultTableModel(data.toArray(new Object[0][]), columnNames);
    }


    private void printReport(JTable salesTable, JTable purchasesTable) {
        try {

            boolean salesPrinted = salesTable.print(
                    JTable.PrintMode.FIT_WIDTH,
                    new MessageFormat("Sales Report - page {0}"),
                    new MessageFormat(""),
                    true,
                    null,
                    true,
                    null
            );

            if (salesPrinted) {
                purchasesTable.print(
                        JTable.PrintMode.FIT_WIDTH,
                        new MessageFormat("purchases Report - page {0}"),
                        new MessageFormat(""),
                        true,
                        null,
                        true,
                        null
                );
            }
        } catch (java.awt.print.PrinterException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "print error : " + ex.getMessage(),
                    "error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }




    private void clearTables() {
        salesTable.setModel(new DefaultTableModel());
        purchasesTable.setModel(new DefaultTableModel());
        salesRowCountLabel.setText("Sales Count: 0");
        purchasesRowCountLabel.setText("Purchases Count: 0");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OperationsReport().setVisible(true));
    }
}
