package wearhouse.gui;

import admin.code.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Sales_Records {

    private JFrame frame;
    private JPanel panel;
    private JButton buttonAddSale;
    private JButton buttonViewSales;
    private JTextField customerNameField;
    private JTextField saleDateField;
    private JTable salesTable;
    private DefaultTableModel tableModel;

    private void initializeUI() {
        frame = new JFrame("Sales Records");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        frame.getContentPane().setBackground(new Color(0, 0, 41));

        panel = new JPanel();
        panel.setLayout(new FlowLayout());


        panel.setBackground(new Color(0, 0, 41));

        // إضافة صورة في الواجهة
        ImageIcon icon = ImageLoader.loadImage("/images/myImage.jpg");
        JLabel label = new JLabel(icon);
        panel.add(label);

        // إضافة حقل اسم الكاستومير
        JLabel customerNameLabel = new JLabel("Customer Name:");
        customerNameLabel.setForeground(new Color(255, 255, 255)); // لون النص (رمادي داكن)
        customerNameField = new JTextField(20);
        customerNameField.setBackground(new Color(255, 255, 255)); // لون خلفية الحقل
        customerNameField.setForeground(new Color(50, 50, 50)); // لون النص داخل الحقل
        panel.add(customerNameLabel);
        panel.add(customerNameField);

        // إضافة حقل تاريخ البيع
        JLabel saleDateLabel = new JLabel("Sale Date (yyyy-mm-dd):");
        saleDateLabel.setForeground(new Color(218, 244, 255)); // لون النص
        saleDateField = new JTextField(20);
        saleDateField.setBackground(new Color(255, 255, 255)); // لون خلفية الحقل
        saleDateField.setForeground(new Color(50, 50, 50)); // لون النص داخل الحقل
        panel.add(saleDateLabel);
        panel.add(saleDateField);

        // إضافة الأزرار
        buttonAddSale = new JButton("Add Sale");
        buttonAddSale.setPreferredSize(new Dimension(150, 40));
        buttonAddSale.setBackground(new Color(0, 123, 255)); // لون الخلفية للأزرار
        buttonAddSale.setForeground(new Color(255, 255, 255)); // لون النص داخل الزر
        buttonAddSale.setFocusPainted(false); // إزالة التأثير عند الضغط
       // buttonAddSale.addActionListener(e -> addSaleToDatabase());
        panel.add(buttonAddSale);

        buttonViewSales = new JButton("View Sales");
        buttonViewSales.setPreferredSize(new Dimension(150, 40));
        buttonViewSales.setBackground(new Color(0, 123, 255)); // لون الخلفية للأزرار
        buttonViewSales.setForeground(new Color(255, 255, 255)); // لون النص داخل الزر
        buttonViewSales.setFocusPainted(false); // إزالة التأثير عند الضغط
        buttonViewSales.addActionListener(e -> viewSalesHistory());
        panel.add(buttonViewSales);

        // إعداد الجدول لعرض سجلات المبيعات
        String[] columnNames = {"ID", "Customer Name", "Sale Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        salesTable = new JTable(tableModel);
        salesTable.setBackground(new Color(255, 255, 255, 255)); // لون خلفية الجدول
        salesTable.setForeground(new Color(0, 0, 41)); // لون النص داخل الجدول
        salesTable.setGridColor(new Color(53, 78, 173)); // لون الخطوط داخل الجدول
        JScrollPane scrollPane = new JScrollPane(salesTable);
        panel.add(scrollPane);

        frame.add(panel, BorderLayout.CENTER);

        // عرض الإطار
        frame.setVisible(true);
    }



    // دالة لعرض تاريخ المبيعات
    private void viewSalesHistory() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:new_file")) {  // تعديل المسار
            String sql = "SELECT * FROM SaleDetails";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                // مسح البيانات القديمة من الجدول
                tableModel.setRowCount(0);
                // إضافة السجلات الجديدة إلى الجدول
                while (rs.next()) {
                    Object[] row = {rs.getInt("SaleDetailID"), rs.getString("CustomerName"), rs.getString("SaleDate")};
                    tableModel.addRow(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error retrieving sales history.");
        }
    }

    // دالة للتحقق من صحة التاريخ
    private boolean isValidDate(String date) {
        try {
            String[] parts = date.split("-");
            if (parts.length != 3) return false;
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            if (month < 1 || month > 12 || day < 1 || day > 31) return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // دالة لبدء التطبيق
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Sales_Records window = new Sales_Records();
                window.initializeUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
