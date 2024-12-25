package wearhouse.gui;

import admin.code.ImageLoader;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.Menu;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Expiry_Dates extends JFrame {
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JSpinner daysSpinner;
    private static final String DATABASE_URL = "jdbc:sqlite:new_file";

    private JLabel totalProductsLabel;

    public Expiry_Dates() {
        setTitle("Expiry_Dates");
        setLayout(new BorderLayout(10, 10));
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        getContentPane().setBackground(new Color(0, 0, 50));


        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(0, 0, 50));


        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 0, 50));
        ImageIcon icon = ImageLoader.loadImage("/images/add.png");
        icon.setImage(icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        JLabel titleLabel = new JLabel("Expiry Dates", icon, SwingConstants.RIGHT);
        titleLabel.setHorizontalTextPosition(JLabel.RIGHT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 27));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel);


        JPanel filterControls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterControls.setBackground(new Color(0, 0, 50));

        JLabel filterLabel = new JLabel("Show products expiring within:");
        filterLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        filterLabel.setForeground(Color.WHITE);
        filterControls.add(filterLabel);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(30, 1, 365, 1);
        daysSpinner = new JSpinner(spinnerModel);
        daysSpinner.setPreferredSize(new Dimension(60, 25));
        filterControls.add(daysSpinner);

        JLabel daysLabel = new JLabel("days");
        daysLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        daysLabel.setForeground(Color.WHITE);
        filterControls.add(daysLabel);
        ImageIcon refreshIcon = ImageLoader.loadImage("/images/13.png",30,30);



        JButton refreshButton = new JButton("Refresh");
        refreshButton.setIcon(refreshIcon);
        refreshButton.setHorizontalTextPosition(JButton.RIGHT);
        refreshButton.setHorizontalAlignment(SwingConstants.LEFT);
        refreshButton.setMargin(new Insets(0, 0, 0, 0));
        refreshButton.setFont(new Font("Arial", Font.PLAIN, 17));
        refreshButton.setBackground(new Color(128, 128, 192));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> refreshData());

// إضافة الزر إلى الـ panel
        filterControls.add(refreshButton);


        filterControls.add(Box.createHorizontalStrut(20));

        ImageIcon filterIcon = ImageLoader.loadImage("/images/11.png",30,30);


        totalProductsLabel = new JLabel("Total Products: 0");
        totalProductsLabel.setIcon(filterIcon);
        totalProductsLabel.setHorizontalTextPosition(JLabel.RIGHT);
        totalProductsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        totalProductsLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        totalProductsLabel.setForeground(Color.WHITE);

// إضافة الـ JLabel إلى الـ panel
        filterControls.add(totalProductsLabel);


        topPanel.add(titlePanel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(filterControls);
        topPanel.add(Box.createVerticalStrut(10));


        String[] columns = {"ID", "Product Name", "Category", "Supplier", "Price",
                "Stock", "Expiration Date", "Days Until Expiry", "Description"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable = new JTable(tableModel);
        productTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


        productTable.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);

                if (value != null) {
                    int daysUntilExpiry = Integer.parseInt(value.toString());
                    if (daysUntilExpiry < 0) {
                        c.setBackground(new Color(255, 175, 175));
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    } else if (daysUntilExpiry <= 30) {
                        c.setBackground(new Color(251, 251, 164));
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                } else {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        });


        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder(null, "Expiry Dates",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14), Color.WHITE));
        tablePanel.setBackground(new Color(0, 0, 50));

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(0, 0, 50));
        tablePanel.add(scrollPane, BorderLayout.CENTER);


        add(topPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(0, 0, 50));
        ImageIcon backButtonIcon = ImageLoader.loadImage("/images/logout.png",30,30);

        JButton backButton = null;

            backButton = new JButton("Back");
            backButton.setIcon(backButtonIcon);
            backButton.setHorizontalTextPosition(JButton.RIGHT);
            backButton.setHorizontalAlignment(SwingConstants.LEFT);
            backButton.setMargin(new Insets(0, 0, 0, 0));
            backButton.setFont(new Font("Arial", Font.PLAIN, 18));
            backButton.setBackground(new Color(128, 128, 192));
            backButton.setForeground(Color.WHITE);
            backButton.setFocusPainted(false);
            backButton.addActionListener(e -> {
                dispose();
                new Menu(); // التأكد من أن Menu موجودة وتعمل بشكل صحيح
            });


// فحص إذا كانت backButton غير null قبل إضافتها
            bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);


        refreshData();


        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void refreshData() {
        tableModel.setRowCount(0);
        int days = (Integer) daysSpinner.getValue();
        LocalDate currentDate = LocalDate.now();

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM Products ORDER BY ExpirationDate")) {
            
            ResultSet rs = stmt.executeQuery();
            int totalProducts = 0;

            while (rs.next()) {
                String expirationDateStr = rs.getString("ExpirationDate");
                long daysUntilExpiry = 0;
                
                if (expirationDateStr != null && !expirationDateStr.trim().isEmpty()) {
                    try {
                        LocalDate expiryDate = LocalDate.parse(expirationDateStr);
                        daysUntilExpiry = ChronoUnit.DAYS.between(currentDate, expiryDate);
                    } catch (Exception e) {
                        
                        continue;
                    }
                } else {
                    
                    continue;
                }

                Object[] row = {
                    rs.getInt("ProductID"),
                    rs.getString("Name"),
                    rs.getString("CategoryName"),
                    rs.getString("SupplierID"),
                    rs.getDouble("Price"),
                    rs.getInt("Stock"),
                    expirationDateStr,
                    daysUntilExpiry,
                    rs.getString("Description")
                };
                
                if (daysUntilExpiry <= days) {
                tableModel.addRow(row);
                totalProducts++;
                }
            }
            
            totalProductsLabel.setText("Total Products: " + totalProducts);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error loading products: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Expiry_Dates frame = new Expiry_Dates();
        frame.setVisible(true);
    }
}
