package casher.gui;

import admin.code.ImageLoader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MakeNewSale extends JFrame {
    private JLabel lblCurrentTime;
    private JTable itemTable;  // Table to display items in the receipt
    private JTextField txtCustomerId, txtItemSku;
    private JSpinner spinnerQuantity;
    private JLabel lblTotalAmount;  // Label to display total amount

    public MakeNewSale() {
        setTitle("Make New Sale");
        setSize(1000, 700);
       // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(10, 25, 47));

        // Maximize the window
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Title Label
        JLabel lblTitle = new JLabel("Make New Sale", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(400, 20, 800, 60);
        add(lblTitle);

        // Current time label
        lblCurrentTime = new JLabel();
        lblCurrentTime.setFont(new Font("SansSerif", Font.PLAIN, 24));
        lblCurrentTime.setForeground(Color.WHITE);
        lblCurrentTime.setBounds(1430, 10, 200, 40);
        add(lblCurrentTime);

        // Initialize the current time display
        updateCurrentTime();

        // Customer Details Panel
        JPanel customerPanel = new JPanel();
        customerPanel.setBackground(new Color(0, 30, 50));
        customerPanel.setLayout(null);
        customerPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE), "Customer Detail",
                0, 0, new Font("Segoe UI", Font.BOLD, 16), Color.WHITE));
        customerPanel.setBounds(50, 150, 600, 200);
        add(customerPanel);

        JLabel lblCustomerId = new JLabel("Customer ID:");
        lblCustomerId.setForeground(Color.WHITE);
        lblCustomerId.setBounds(20, 50, 100, 30);
        customerPanel.add(lblCustomerId);

        txtCustomerId = new JTextField();
        txtCustomerId.setBounds(130, 50, 150, 30);
        customerPanel.add(txtCustomerId);

        JButton btnCheck = new JButton("Check", ImageLoader.loadImage("/images/check-mark.png"));
        btnCheck.setBounds(300, 50, 250, 30);
        btnCheck.setBackground(new Color(0, 153, 76));
        btnCheck.setForeground(Color.WHITE);
        customerPanel.add(btnCheck);

        JButton btnAddCustomer = new JButton("Add New Customer");
        btnAddCustomer.setBounds(150, 130, 250, 30);
        btnAddCustomer.setBackground(new Color(65, 105, 225));
        btnAddCustomer.setForeground(Color.WHITE);
        customerPanel.add(btnAddCustomer);

        // Item Details Panel
        JPanel itemPanel = new JPanel();
        itemPanel.setBackground(new Color(0, 30, 50));
        itemPanel.setLayout(null);
        itemPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE), "Item Details",
                0, 0, new Font("Segoe UI", Font.BOLD, 16), Color.WHITE));
        itemPanel.setBounds(50, 400, 600, 300);
        add(itemPanel);

        JLabel lblItemSku = new JLabel("Item SKU:");
        lblItemSku.setForeground(Color.WHITE);
        lblItemSku.setBounds(20, 50, 100, 30);
        itemPanel.add(lblItemSku);

        txtItemSku = new JTextField();
        txtItemSku.setBounds(100, 50, 150, 30);
        itemPanel.add(txtItemSku);

        JLabel lblQuantity = new JLabel("Quantity:");
        lblQuantity.setForeground(Color.WHITE);
        lblQuantity.setBounds(320, 50, 100, 30);
        itemPanel.add(lblQuantity);

        spinnerQuantity = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        spinnerQuantity.setBounds(400, 50, 80, 30);
        itemPanel.add(spinnerQuantity);

        JButton btnAddItem = new JButton("Add Item", ImageLoader.loadImage("/images/plus.png"));
        btnAddItem.setBounds(100, 150, 150, 40);
        btnAddItem.setBackground(new Color(0, 153, 76));
        btnAddItem.setForeground(Color.WHITE);
        itemPanel.add(btnAddItem);

        JButton btnRemoveItem = new JButton("Remove Item", ImageLoader.loadImage("/images/shopping-cart.png"));
        btnRemoveItem.setBounds(350, 150, 150, 40);
        btnRemoveItem.setBackground(new Color(255, 69, 0));
        btnRemoveItem.setForeground(Color.WHITE);
        itemPanel.add(btnRemoveItem);

        JButton btnGenerateReceipt = new JButton("Generate Receipt", ImageLoader.loadImage("/images/receipt.png"));
        btnGenerateReceipt.setBounds(200, 220, 200, 40);
        btnGenerateReceipt.setBackground(new Color(0, 102, 204));
        btnGenerateReceipt.setForeground(Color.WHITE);
        itemPanel.add(btnGenerateReceipt);

        // Table for Item Details
        itemTable = new JTable(new DefaultTableModel(new Object[]{"Item SKU", "Quantity", "Price", "Total"}, 0));
        JScrollPane tableScrollPane = new JScrollPane(itemTable);
        tableScrollPane.setBounds(700, 150, 800, 600);
        add(tableScrollPane);

        // Total Amount Label
        lblTotalAmount = new JLabel("Total: $0.00");
        lblTotalAmount.setForeground(Color.WHITE);
        lblTotalAmount.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotalAmount.setBounds(700, 750, 200, 30);
        add(lblTotalAmount);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(20, 20, 80, 30);
        backButton.setBackground(new Color(192, 57, 43));
        backButton.setForeground(Color.WHITE);
        add(backButton);

        // Action listeners
        btnAddItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sku = txtItemSku.getText();
                int quantity = (int) spinnerQuantity.getValue();

                // Get product price from database or some other logic
                double price = getPrice(sku);
                double total = price * quantity;

                // Add item to the table
                DefaultTableModel model = (DefaultTableModel) itemTable.getModel();
                model.addRow(new Object[]{sku, quantity, price, total});

                // Update the total amount
                updateTotalAmount();
            }
        });

        btnRemoveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = itemTable.getSelectedRow();
                if (selectedRow >= 0) {
                    DefaultTableModel model = (DefaultTableModel) itemTable.getModel();
                    model.removeRow(selectedRow);

                    // Update the total amount
                    updateTotalAmount();
                } else {
                    JOptionPane.showMessageDialog(MakeNewSale.this, "Select an item to remove.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnGenerateReceipt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(MakeNewSale.this, "Do you want to generate the receipt?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    // Insert sale record
                    insertSale();

                    // Generate text receipt
                    generateTextReceipt();
                }
            }
        });
        btnAddCustomer.addActionListener(e -> {
            new AddCustomer().setVisible(true);

        });
        backButton.addActionListener(e -> {
            new CashierHome().setVisible(true);
            dispose();
        });

        btnCheck.addActionListener(e -> {
            String customerId = txtCustomerId.getText();
            JOptionPane.showMessageDialog(MakeNewSale.this, "Customer ID " + customerId + " checked.", "Customer Check", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    // Get price of the item from the database
    private double getPrice(String sku) {
        // Placeholder logic to retrieve the price for the SKU
        return 20.0;
    }

    // Insert sale record into the Sales and SaleDetails tables
    private void insertSale() {
        // Retrieve customer ID and other sale details
        int customerId = Integer.parseInt(txtCustomerId.getText());
        double totalAmount = 0.0;

        DefaultTableModel model = (DefaultTableModel) itemTable.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            double total = (double) model.getValueAt(i, 3);
            totalAmount += total;
        }


    }

    // Generate text receipt
    private void generateTextReceipt() {
        try {
            // إنشاء الفاتورة النصية
            StringBuilder receiptContent = new StringBuilder();
            receiptContent.append("Receipt\n");
            receiptContent.append("================================\n");
            receiptContent.append("Customer ID: " + txtCustomerId.getText() + "\n\n");

            DefaultTableModel model = (DefaultTableModel) itemTable.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                String sku = (String) model.getValueAt(i, 0);
                int quantity = (int) model.getValueAt(i, 1);
                double price = (double) model.getValueAt(i, 2);
                double total = (double) model.getValueAt(i, 3);

                receiptContent.append("Item SKU: " + sku + ", Quantity: " + quantity + ", Price: " + price + ", Total: " + total + "\n");
            }

            receiptContent.append("\n================================\n");
            receiptContent.append("Total Amount: " + lblTotalAmount.getText() + "\n");

            // تحويل المحتوى إلى BLOB (بيانات ثنائية)
            byte[] receiptBlob = receiptContent.toString().getBytes();

            // إدخال الفاتورة في جدول الفواتير
            try (Connection conn = connectToDatabase()) {
                if (conn != null) {
                    String insertSQL = "INSERT INTO Invoices (InvoiceName, InvoiceDate, InvoiceFile, CustomerID) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                        pstmt.setString(1, "Invoice_" + System.currentTimeMillis());  // اسم الفاتورة
                        pstmt.setString(2, java.time.LocalDate.now().toString());  // تاريخ الفاتورة
                        pstmt.setBytes(3, receiptBlob);  // محتوى الفاتورة
                        pstmt.setInt(4, Integer.parseInt(txtCustomerId.getText()));  // معرف العميل

                        pstmt.executeUpdate();
                    }
                }
            }

            JOptionPane.showMessageDialog(this, "Receipt stored successfully in the database.", "Success", JOptionPane.INFORMATION_MESSAGE);

            // إنشاء نافذة لعرض محتوى الفاتورة
            JTextArea receiptTextArea = new JTextArea(receiptContent.toString());
            receiptTextArea.setEditable(false);  // جعل النص غير قابل للتعديل
            JScrollPane scrollPane = new JScrollPane(receiptTextArea);

            JFrame receiptFrame = new JFrame("Receipt");
            receiptFrame.setSize(600, 400);
            receiptFrame.setLocationRelativeTo(this);
            receiptFrame.add(scrollPane);
            receiptFrame.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error generating and storing receipt: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }




        private Connection connectToDatabase() {
            try {
                String url = "jdbc:sqlite:new_file";  // استبدل بالمسار الصحيح لقاعدة البيانات
                return DriverManager.getConnection(url);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        // الاتصال بقاعدة البيانات




    // Method to update current time every second
    private void updateCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> lblCurrentTime.setText(LocalTime.now().format(formatter)));
            }
        }, 0, 1000); // Update every 1 second
    }

    // Update total amount displayed in the label
    private void updateTotalAmount() {
        double totalAmount = 0.0;

        DefaultTableModel model = (DefaultTableModel) itemTable.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            totalAmount += (double) model.getValueAt(i, 3);
        }

        lblTotalAmount.setText("Total: $" + String.format("%.2f", totalAmount));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MakeNewSale frame = new MakeNewSale();
            frame.setVisible(true);
        });
    }
}
