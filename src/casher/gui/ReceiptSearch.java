package casher.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class ReceiptSearch extends JFrame {
    private static final String DB_URL = "jdbc:sqlite:new_file";
    public ReceiptSearch() {
        // Frame setup

        setTitle("Receipt Search");
       // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Initial size before maximizing
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(10, 25, 47)); // Dark blue background

        // Maximize the window
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Title
        JLabel titleLabel = new JLabel("Receipt Search", JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        titleLabel.setBounds(400, 20, 800, 60);
        add(titleLabel);

        // Receipt ID Input
        JLabel receiptIdLabel = new JLabel("Enter Receipt ID:");
        receiptIdLabel.setForeground(Color.WHITE);
        receiptIdLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        receiptIdLabel.setBounds(500, 150, 200, 30);
        add(receiptIdLabel);

        JTextField receiptIdField = new JTextField();
        receiptIdField.setBounds(700, 150, 300, 30);
        receiptIdField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        add(receiptIdField);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(1050, 150, 150, 40);
        searchButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        searchButton.setBackground(new Color(67, 84, 138));
        searchButton.setForeground(Color.WHITE);
        add(searchButton);

        // No receipt found message
        JLabel noReceiptLabel = new JLabel("*No receipt found in the Record", JLabel.CENTER);
        noReceiptLabel.setForeground(Color.RED);
        noReceiptLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        noReceiptLabel.setBounds(550, 200, 400, 30);
        noReceiptLabel.setVisible(false);
        add(noReceiptLabel);

        // Receipt Details Section
        JLabel receiptDetailsLabel = new JLabel("Receipt Details");
        receiptDetailsLabel.setForeground(Color.WHITE);
        receiptDetailsLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        receiptDetailsLabel.setBounds(400, 250, 200, 30);
        add(receiptDetailsLabel);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setBounds(400, 300, 500, 200);
        detailsPanel.setLayout(null);
        detailsPanel.setBackground(new Color(67, 84, 138));
        add(detailsPanel);

        JLabel customerIdLabel = new JLabel("Customer ID:");
        customerIdLabel.setBounds(20, 20, 150, 30);
        customerIdLabel.setForeground(Color.WHITE);
        customerIdLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        detailsPanel.add(customerIdLabel);

        JTextField customerIdField = new JTextField();
        customerIdField.setBounds(180, 20, 250, 30);
        customerIdField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        detailsPanel.add(customerIdField);

        JLabel customerNameLabel = new JLabel("Customer Name:");
        customerNameLabel.setBounds(20, 70, 150, 30);
        customerNameLabel.setForeground(Color.WHITE);
        customerNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        detailsPanel.add(customerNameLabel);

        JTextField customerNameField = new JTextField();
        customerNameField.setBounds(180, 70, 250, 30);
        customerNameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        detailsPanel.add(customerNameField);

        JLabel discountLabel = new JLabel("Discount:");
        discountLabel.setBounds(20, 120, 150, 30);
        discountLabel.setForeground(Color.WHITE);
        discountLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        detailsPanel.add(discountLabel);

        JTextField discountField = new JTextField();
        discountField.setBounds(180, 120, 250, 30);
        discountField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        detailsPanel.add(discountField);

        JLabel totalBillLabel = new JLabel("Total Bill:");
        totalBillLabel.setBounds(20, 170, 150, 30);
        totalBillLabel.setForeground(Color.WHITE);
        totalBillLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        detailsPanel.add(totalBillLabel);

        JTextField totalBillField = new JTextField();
        totalBillField.setBounds(180, 170, 250, 30);
        totalBillField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        detailsPanel.add(totalBillField);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(20, 20, 80, 30);
        backButton.setBackground(new Color(192, 57, 43));
        backButton.setForeground(Color.WHITE);
        add(backButton);

        // View Receipt Button
        JButton viewReceiptButton = new JButton("View Receipt");
        viewReceiptButton.setBounds(1050, 200, 150, 40);
        viewReceiptButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        viewReceiptButton.setBackground(new Color(67, 84, 138));
        viewReceiptButton.setForeground(Color.WHITE);
        add(viewReceiptButton);

        // Item List Table Section
        JLabel itemListLabel = new JLabel("Item List");
        itemListLabel.setForeground(Color.WHITE);
        itemListLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        itemListLabel.setBounds(950, 250, 200, 30);
        add(itemListLabel);

        JTable itemTable = new JTable(new DefaultTableModel(new Object[]{"Item SKU", "Quantity", "Price", "Total"}, 0));
        itemTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        itemTable.setRowHeight(25);
        JScrollPane tableScrollPane = new JScrollPane(itemTable);
        tableScrollPane.setBounds(950, 300, 500, 400);
        add(tableScrollPane);

        // Make frame visible
        setVisible(true);
        searchButton.addActionListener(e -> {
            String receiptId = receiptIdField.getText();

            if (!receiptId.isEmpty()) {
                try (Connection connection = DriverManager.getConnection(DB_URL)) {
                    // استعلام للبحث عن بيانات الفاتورة من جدول Invoices
                    String invoiceQuery = "SELECT InvoiceID, InvoiceDate, discount, CustomerID FROM Invoices WHERE InvoiceID = ?";
                    PreparedStatement invoiceStatement = connection.prepareStatement(invoiceQuery);
                    invoiceStatement.setString(1, receiptId);

                    ResultSet invoiceResultSet = invoiceStatement.executeQuery();

                    if (invoiceResultSet.next()) {
                        // استخراج بيانات الفاتورة
                        receiptIdField.setText(invoiceResultSet.getString("InvoiceID"));
                        discountField.setText(invoiceResultSet.getString("discount"));
                        String customerId = invoiceResultSet.getString("CustomerID");

                        // عرض الـ CustomerID في الحقل المخصص
                        customerIdField.setText(customerId);  // عرض CustomerID في الحقل المخصص

                        // استعلام للحصول على اسم العميل من جدول Customers بناءً على CustomerID
                        String customerQuery = "SELECT Name FROM Customers WHERE CustomerID = ?";
                        PreparedStatement customerStatement = connection.prepareStatement(customerQuery);
                        customerStatement.setString(1, customerId);

                        ResultSet customerResultSet = customerStatement.executeQuery();
                        if (customerResultSet.next()) {
                            String customerName = customerResultSet.getString("Name");
                            customerNameField.setText(customerName);  // عرض اسم العميل في الحقل المخصص
                        }

                        // استعلام للحصول على المبيعات بناءً على SaleID
                        String salesQuery = "SELECT SaleDetails.productid, SaleDetails.Quantity, SaleDetails.Price, Sales.TotalAmount " +
                                "FROM SaleDetails " +
                                "INNER JOIN Sales ON SaleDetails.SaleID = Sales.SaleID " +
                                "WHERE Sales.SaleID = ?";

                        PreparedStatement salesStatement = connection.prepareStatement(salesQuery);
                        salesStatement.setString(1, receiptId);

                        ResultSet salesResultSet = salesStatement.executeQuery();

                        // حساب المبلغ الإجمالي بعد الخصم وعرضه
                        double totalAmount = 0;
                        DefaultTableModel tableModel = (DefaultTableModel) itemTable.getModel();  // الحصول على الموديل الخاص بالـ JTable
                        tableModel.setRowCount(0);  // إعادة تعيين الجدول قبل إضافة البيانات الجديدة

                        while (salesResultSet.next()) {
                            double itemTotal = salesResultSet.getDouble("TotalAmount");
                            totalAmount += itemTotal;

                            // إضافة صفوف إلى الـ JTable
                            tableModel.addRow(new Object[] {
                                    salesResultSet.getString("productid"),  // SKU
                                    salesResultSet.getInt("Quantity"),      // Quantity
                                    salesResultSet.getDouble("Price"),      // Price
                                    itemTotal                               // Total
                            });
                        }

                        double discount = invoiceResultSet.getDouble("discount");

                        // حساب المبلغ الإجمالي بعد الخصم
                        double totalAfterDiscount = totalAmount - (totalAmount * (discount / 100));
                        totalBillField.setText(String.format("%.2f", totalAfterDiscount));

                    } else {
                        // في حال لم يتم العثور على الفاتورة
                        noReceiptLabel.setVisible(true);
                        receiptIdField.setText("");
                        discountField.setText("");
                        totalBillField.setText("");  // عرض فارغ إذا لم يتم العثور على البيانات
                        customerIdField.setText(""); // عرض فارغ لـ CustomerID
                        customerNameField.setText(""); // عرض فارغ لـ CustomerName
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error searching for receipt: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid Invoice ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });



        // Back button action
        backButton.addActionListener(e -> {
            new CashierHome().setVisible(true);
            dispose();
        });


        viewReceiptButton.addActionListener(e -> {
            String receiptId = receiptIdField.getText();
            if (!receiptId.isEmpty()) {
                try {
                    // الاتصال بقاعدة البيانات
                    Connection connection = DriverManager.getConnection(DB_URL);

                    // استعلام لجلب جميع الفواتير
                    String query = "SELECT * FROM Invoices";
                    PreparedStatement statement = connection.prepareStatement(query);
                    ResultSet resultSet = statement.executeQuery();

                    // إنشاء نافذة جديدة لعرض قائمة الفواتير
                    JFrame receiptListFrame = new JFrame("Receipt List");
                    receiptListFrame.setSize(600, 400);
                    receiptListFrame.setLayout(new BorderLayout());
                    DefaultTableModel model = new DefaultTableModel(new Object[]{"Receipt ID", "Customer Name", "Total Bill"}, 0);
                    JTable receiptTable = new JTable(model);

                    // إضافة البيانات إلى الجدول
                    while (resultSet.next()) {
                        model.addRow(new Object[]{
                                resultSet.getString("Invoiceid"),
                                resultSet.getString("customerid"),
                                // resultSet.getDouble("total_bill")
                        });
                    }

                    // إنشاء ScrollPane للجدول
                    JScrollPane scrollPane = new JScrollPane(receiptTable);
                    receiptListFrame.add(scrollPane, BorderLayout.CENTER);

                    // إضافة زر لفتح الفاتورة المحددة
                    JButton openButton = new JButton("Open Receipt");
                    receiptListFrame.add(openButton, BorderLayout.SOUTH);

                    // عند الضغط على الزر، يتم فتح الفاتورة
                    openButton.addActionListener(event -> {
                        int selectedRow = receiptTable.getSelectedRow();
                        if (selectedRow != -1) {
                            String selectedReceiptId = (String) receiptTable.getValueAt(selectedRow, 0);
                            openReceiptFile(selectedReceiptId);
                        } else {
                            JOptionPane.showMessageDialog(receiptListFrame, "Please select a receipt.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    });

                    // إظهار النافذة
                    receiptListFrame.setLocationRelativeTo(null);
                    receiptListFrame.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error retrieving receipts: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid Receipt ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

    }




    // Move the method outside the event listener
    private void openReceiptFile(String receiptId) {
        String query = "SELECT InvoiceFile FROM Invoices WHERE Invoiceid = ?";
        FileWriter fileWriter = null;

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set the receiptId parameter in the query
            stmt.setString(1, receiptId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Retrieve the BLOB data from the database
                    byte[] blobData = rs.getBytes("InvoiceFile");

                    if (blobData != null && blobData.length > 0) {
                        // Convert the BLOB to a String using UTF-8 encoding
                        String fileContent = new String(blobData, StandardCharsets.UTF_8);

                        // Define the path where you want to save the file
                        File file = new File(System.getProperty("user.home") + "/Documents/Receipt_" + receiptId + ".txt");

                        // Write the content to the file
                        fileWriter = new FileWriter(file);
                        fileWriter.write(fileContent);

                        // Once the file is written, open it using the default system editor
                        Desktop.getDesktop().open(file);

                    } else {
                        JOptionPane.showMessageDialog(this, "No data available for this invoice.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invoice not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error opening the file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error closing the file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }





    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReceiptSearch::new);
    }
}
