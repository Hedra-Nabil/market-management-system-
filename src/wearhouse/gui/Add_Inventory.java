package wearhouse.gui;

import admin.code.ImageLoader;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Menu;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.SpinnerDateModel;

public class Add_Inventory extends JFrame {
    private static final String DATABASE_URL = "jdbc:sqlite:new_file";

    private JTextField productNameField, priceField, stockField;
    private JSpinner expirationDateSpinner;
    private JTextArea descriptionArea;
    private JComboBox<String> categoryCombo;
    private JComboBox<String> supplierCombo;
    private DefaultTableModel tableModel;
    private JTable productTable;
    private JLabel totalProductsLabel;

    public Add_Inventory() {
        setTitle("Add Inventory");
       // setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(0, 0, 50));

        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(0, 0, 50));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        ImageIcon icon = ImageLoader.loadImage("/Images/addinventory.png");
        JLabel titleLabel = new JLabel("Add Inventory", icon, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 27));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titleLabel.setHorizontalTextPosition(JLabel.RIGHT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(0, 0, 50));
        titlePanel.add(titleLabel);

        mainPanel.add(titlePanel, BorderLayout.NORTH);



        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(0, 0, 50));
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(null, "Add Inventory", 
                TitledBorder.DEFAULT_JUSTIFICATION, 
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14), Color.WHITE),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        
        productNameField = new JTextField(20);
        categoryCombo = new JComboBox<>();
        supplierCombo = new JComboBox<>();
        priceField = new JTextField(20);
        stockField = new JTextField(20);
        
        
        SpinnerDateModel dateModel = new SpinnerDateModel();
        expirationDateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(expirationDateSpinner, "yyyy-MM-dd");
        expirationDateSpinner.setEditor(dateEditor);
        ((JSpinner.DefaultEditor) expirationDateSpinner.getEditor()).getTextField().setBackground(new Color(255, 255, 255));
        ((JSpinner.DefaultEditor) expirationDateSpinner.getEditor()).getTextField().setForeground(new Color(0, 0, 50));
        ((JSpinner.DefaultEditor) expirationDateSpinner.getEditor()).getTextField().setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(128, 128, 192)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            )
        );
        expirationDateSpinner.setPreferredSize(new Dimension(200, 30));
        
        descriptionArea = new JTextArea(4, 20);

        
        Component[] components = {
            productNameField,
            supplierCombo,
            priceField,
            stockField,
            descriptionArea
        };

        for (Component comp : components) {
            comp.setBackground(new Color(255, 255, 255));
            comp.setForeground(new Color(0, 0, 50));
            comp.setFont(new Font("Arial", Font.PLAIN, 14));
            if (comp instanceof JTextField) {
                ((JTextField) comp).setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(128, 128,192)),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
            }
        }

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        
        
        JLabel nameLabel = new JLabel("Product Name:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(productNameField, gbc);

        
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1; ;
        inputPanel.add(categoryLabel, gbc);

        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        categoryPanel.setBackground(new Color(0, 0, 50));
        categoryCombo.setPreferredSize(new Dimension(200, 25));
        categoryPanel.add(categoryCombo);
        ImageIcon addCategoryIcon = ImageLoader.loadImage("/images/7.png");
        addCategoryIcon.setImage(addCategoryIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        JButton addCategoryBtn = new JButton("Add", addCategoryIcon);
        addCategoryBtn.setMargin(new Insets(0, 0, 0, 0));
        addCategoryBtn.setHorizontalAlignment(SwingConstants.LEFT);
        addCategoryBtn.setPreferredSize(new Dimension(80, 25));
        addCategoryBtn.setBackground(new Color(128, 128, 192));
        addCategoryBtn.setForeground(Color.WHITE);
        addCategoryBtn.setFocusPainted(false);
        addCategoryBtn.setFont(new Font("Arial", Font.BOLD, 15));
        categoryPanel.add(addCategoryBtn);

        gbc.gridx = 1;
        inputPanel.add(categoryPanel, gbc);

        
        addCategoryBtn.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Add Category", true);
            dialog.setLayout(new BorderLayout());
            dialog.setBackground(new Color(0, 0, 50));

            JPanel dialogPanel = new JPanel(new GridBagLayout());
            dialogPanel.setBackground(new Color(0, 0, 50));
            dialogPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel catLabel = new JLabel("Category Name:");
            catLabel.setForeground(Color.WHITE);
            catLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            JTextField catField = new JTextField(20);
            catField.setBackground(new Color(240, 240, 245));
            catField.setForeground(new Color(0, 0, 50));
            catField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(128, 128,192)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));

            JButton saveBtn = new JButton("Save");
            saveBtn.setBackground(new Color(128, 128,192));
            saveBtn.setForeground(Color.WHITE);
            saveBtn.setFocusPainted(false);

            GridBagConstraints dgbc = new GridBagConstraints();
            dgbc.insets = new Insets(5, 5, 5, 5);
            dgbc.gridx = 0;
            dgbc.gridy = 0;
            dialogPanel.add(catLabel, dgbc);
            dgbc.gridx = 1;
            dialogPanel.add(catField, dgbc);
            dgbc.gridy = 1;
            dgbc.gridx = 0;
            dgbc.gridwidth = 2;
            dgbc.anchor = GridBagConstraints.CENTER;
            dialogPanel.add(saveBtn, dgbc);

            saveBtn.addActionListener(se -> {
                String newCategory = catField.getText().trim();
                if (!newCategory.isEmpty()) {
                    try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                         PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Categories (CategoryName) VALUES (?)")) {
                        pstmt.setString(1, newCategory);
                        pstmt.executeUpdate();
                        loadCategoriesFromDatabase();
                        categoryCombo.setSelectedItem(newCategory);
                        dialog.dispose();
                        JOptionPane.showMessageDialog(this, "Category added successfully!");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this,
                            "Error adding category: " + ex.getMessage(),
                            "Database Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            dialog.add(dialogPanel);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });

        
        Component[] remainingFields = {
            supplierCombo, priceField, stockField, 
            expirationDateSpinner, new JScrollPane(descriptionArea)
        };
        String[] remainingLabels = {
            "Supplier:", "Price:", "Stock:", 
            "Expiration Date:", "Description:"
        };

        for (int i = 0; i < remainingLabels.length; i++) {
            JLabel label = new JLabel(remainingLabels[i]);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            gbc.gridx = 0;
            gbc.gridy = i + 2;
            inputPanel.add(label, gbc);

            gbc.gridx = 1;
            inputPanel.add(remainingFields[i], gbc);
        }

        
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 78, 10));
        buttonsPanel.setBackground(new Color(0, 0, 50));
        ImageIcon addInventoryIcon = ImageLoader.loadImage("/images/9.png");
        addInventoryIcon.setImage(addInventoryIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        JButton addInventoryBtn = new JButton("Add Inventory");
        addInventoryBtn.setIcon(addInventoryIcon);
        addInventoryBtn.setMargin(new Insets(0, 0, 0, 0));
        addInventoryBtn.setPreferredSize(new Dimension(150, 50));
        addInventoryBtn.setHorizontalAlignment(SwingConstants.LEFT);
        addInventoryBtn.setBackground(new Color(128, 128,192));
        addInventoryBtn.setForeground(Color.WHITE);
        addInventoryBtn.setFocusPainted(false);
        addInventoryBtn.setFont(new Font("Arial", Font.BOLD, 15));
        buttonsPanel.add(addInventoryBtn);

        
        addInventoryBtn.addActionListener(e -> addProductToDatabase());

        
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(buttonsPanel, gbc);

        
        JPanel tablePanel = createProductListPanel();
        tablePanel.setBackground(new Color(0, 0, 50));
        tablePanel.setBorder(BorderFactory.createTitledBorder(null, "Inventory List", 
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.BOLD, 14), Color.WHITE));

        
        mainPanel.add(inputPanel, BorderLayout.WEST);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        bottomPanel.setBackground(new Color(0, 0, 50));

        ImageIcon totalProductsIcon  = ImageLoader.loadImage("/images/11.png");
        totalProductsIcon.setImage(totalProductsIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        totalProductsLabel = new JLabel("Total Products: " + tableModel.getRowCount());
        totalProductsLabel.setIcon(totalProductsIcon);
        totalProductsLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        totalProductsLabel.setHorizontalTextPosition(JLabel.RIGHT);
        totalProductsLabel.setForeground(Color.WHITE);
        totalProductsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bottomPanel.add(totalProductsLabel);

        ImageIcon deleteInventoryIcon   = ImageLoader.loadImage("/images/10.png");
        deleteInventoryIcon.setImage(deleteInventoryIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        JButton deleteInventoryBtn = new JButton("Delete Product");
        deleteInventoryBtn.setIcon(deleteInventoryIcon);
        deleteInventoryBtn.setMargin(new Insets(0, 0, 0, 0));
        deleteInventoryBtn.setPreferredSize(new Dimension(150, 50));
        deleteInventoryBtn.setHorizontalAlignment(SwingConstants.LEFT);
        deleteInventoryBtn.setBackground(new Color(128, 128,192));
        deleteInventoryBtn.setForeground(Color.WHITE);
        deleteInventoryBtn.setFocusPainted(false);
        deleteInventoryBtn.setFont(new Font("Arial", Font.BOLD, 14));
        deleteInventoryBtn.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                int productId = (int) tableModel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this inventory item?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    deleteProduct(productId);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Please select an inventory item to delete",
                    "Selection Required",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        bottomPanel.add(deleteInventoryBtn);

        ImageIcon goBackIcon = ImageLoader.loadImage("/images/5.png");
        goBackIcon.setImage(goBackIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        JButton goBackBtn = new JButton("Back");
        goBackBtn.setIcon(goBackIcon);
        goBackBtn.setMargin(new Insets(0, 0, 0, 0));
        goBackBtn.setPreferredSize(new Dimension(80, 50));
        goBackBtn.setHorizontalAlignment(SwingConstants.LEFT);
        goBackBtn.setBackground(new Color(128, 128,192));
        goBackBtn.setForeground(Color.WHITE);
        goBackBtn.setFocusPainted(false);
        goBackBtn.setFont(new Font("Arial", Font.BOLD, 14));
        goBackBtn.addActionListener(e -> {
            dispose();
            new Menu();
        });
        
        bottomPanel.add(goBackBtn);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        
        tableModel.addTableModelListener(e -> {
            totalProductsLabel.setText("Total Products: " + tableModel.getRowCount());
        });

        
        add(mainPanel);

        
        loadCategoriesFromDatabase();
        loadSuppliersFromDatabase();
        loadProductsFromDatabase();

        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createProductListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(0, 0, 50));

        
        String[] columnNames = {"Product ID", "Product Name", "Category", "Supplier", "Price", "Stock", "Expiration Date", "Description"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        
        productTable = new JTable(tableModel);
        productTable.setBackground(new Color(255, 255, 255));
        productTable.setForeground(new Color(0, 0, 50));
        productTable.setGridColor(new Color(234, 234, 234));
        productTable.getTableHeader().setBackground(new Color(128, 128,192));
        productTable.getTableHeader().setForeground(Color.WHITE);
        productTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        productTable.setFont(new Font("Arial", Font.PLAIN, 12));
        productTable.setRowHeight(25);
        productTable.setSelectionBackground(new Color(128, 128,192));
        productTable.setSelectionForeground(Color.WHITE);

        
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(0, 0, 50));
        panel.add(scrollPane, BorderLayout.CENTER);

        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(0, 0, 50));
        
        JTextField searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setBackground(new Color(255, 255, 255));
        searchField.setForeground(new Color(0, 0, 50));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(128, 128,192)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        ImageIcon searchIcon = ImageLoader.loadImage("/images/8.png");
        searchIcon.setImage(searchIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        JButton searchButton = new JButton("Search");
        searchButton.setIcon(searchIcon);
        searchButton.setMargin(new Insets(0, 0, 0, 0));
        searchButton.setPreferredSize(new Dimension(100, 40));
        searchButton.setHorizontalAlignment(SwingConstants.LEFT);
        searchButton.setBackground(new Color(128, 128,192));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setFont(new Font("Arial", Font.BOLD, 16));
        
        JComboBox<String> searchCriteria = new JComboBox<>(new String[]{
            "Product Name", "Category", "Supplier", "Price Range"
        });
        searchCriteria.setBackground(Color.WHITE);
        
        JLabel searchLabel = new JLabel("Search by:");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(new Font("Arial", Font.BOLD, 15));
        searchPanel.add(searchLabel);
        searchPanel.add(searchCriteria);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        
        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText().trim();
            String criteria = (String) searchCriteria.getSelectedItem();
            
            if (searchTerm.isEmpty()) {
                loadProductsFromDatabase();
                return;
            }
            
            try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
                String sql = buildSearchQuery(criteria);
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    if (criteria.equals("Price Range")) {
                        String[] range = searchTerm.split("-");
                        if (range.length == 2) {
                            pstmt.setDouble(1, Double.parseDouble(range[0].trim()));
                            pstmt.setDouble(2, Double.parseDouble(range[1].trim()));
                        } else {
                            JOptionPane.showMessageDialog(this,
                                "Please enter price range in format: min-max",
                                "Invalid Input",
                                JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        pstmt.setString(1, "%" + searchTerm + "%");
                    }
                    
                    ResultSet rs = pstmt.executeQuery();
                    tableModel.setRowCount(0);
                    while (rs.next()) {
                        Object[] row = {
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getString("category"),
                            rs.getString("supplier"),
                            rs.getDouble("price"),
                            rs.getInt("stock"),
                            rs.getString("expiration_date"),
                            rs.getString("description")
                        };
                        tableModel.addRow(row);
                    }
                    
                    if (tableModel.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(this,
                            "No products found matching your search criteria.",
                            "No Results",
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Please enter valid numbers for price range.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "Error searching products: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        
        panel.add(searchPanel, BorderLayout.NORTH);

        
        panel.add(scrollPane, BorderLayout.CENTER);

        
        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e);
                }
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e);
                }
            }
        });

        return panel;
    }

    private String buildSearchQuery(String criteria) {
        String baseQuery = "SELECT * FROM " +
                "products WHERE ";
        switch (criteria) {
            case "Product Name":
                return baseQuery + "Name LIKE ?";
            case "CategoryName":
                return baseQuery + "category LIKE ?";
            case "Supplier":
                return baseQuery + "supplier LIKE ?";
            case "Price Range":
                return baseQuery + "price BETWEEN ? AND ?";
            default:
                return baseQuery + "product_name LIKE ?";
        }
    }

    private void showPopupMenu(MouseEvent e) {
        JTable source = (JTable) e.getSource();
        int row = source.rowAtPoint(e.getPoint());
        if (row >= 0) {
            source.setRowSelectionInterval(row, row);
            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem deleteItem = new JMenuItem("Delete Product");
            deleteItem.addActionListener(event -> {
                int productId = (int) tableModel.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(
                    Add_Inventory.this,
                    "Are you sure you want to delete this product?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    deleteProduct(productId);
                }
            });
            popupMenu.add(deleteItem);
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    private void loadProductsFromDatabase() {
        tableModel.setRowCount(0);
        String query = "SELECT * FROM Products ORDER BY ProductID";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("productID"),
                    rs.getString("name"),
                    rs.getString("CategoryName"),
                    rs.getString("supplierID"),
                    rs.getDouble("price"),
                    rs.getInt("stock"),
                    rs.getString("expirationdate"),
                    rs.getString("description")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error loading products: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadCategoriesFromDatabase() {
        categoryCombo.removeAllItems();
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT CategoryName FROM categories ORDER BY CategoryName")) {
            
            while (rs.next()) {
                categoryCombo.addItem(rs.getString("CategoryName"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error loading categories: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadSuppliersFromDatabase() {
        supplierCombo.removeAllItems();
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT Name FROM suppliers ORDER BY Name")) {
            
            while (rs.next()) {
                supplierCombo.addItem(rs.getString("Name"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error loading suppliers: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void addProductToDatabase() {
        try {
            String productName = productNameField.getText().trim();
            String category = (String) categoryCombo.getSelectedItem();
            String supplier = (String) supplierCombo.getSelectedItem();
            String priceText = priceField.getText().trim();
            String stockText = stockField.getText().trim();
            Date expirationDate = (Date) expirationDateSpinner.getValue();
            String description = descriptionArea.getText().trim();

            
            if (productName.isEmpty() || category == null || supplier == null || 
                priceText.isEmpty() || stockText.isEmpty() || expirationDate == null) {
                JOptionPane.showMessageDialog(this,
                    "Please fill in all required fields",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            double price;
            int stock;
            try {
                price = Double.parseDouble(priceText);
                stock = Integer.parseInt(stockText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                    "Please enter valid numbers for price and stock",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(expirationDate);

            try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
                String sql = "INSERT INTO Products (Name,CategoryName,SupplierID, Price, Stock, ExpirationDate, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, productName);
                    pstmt.setString(2, category);
                    pstmt.setString(3, supplier);
                    pstmt.setDouble(4, price);
                    pstmt.setInt(5, stock);
                    pstmt.setString(6, formattedDate);
                    pstmt.setString(7, description);
                    
                    pstmt.executeUpdate();
                    
                    JOptionPane.showMessageDialog(this,
                        "Product added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    clearFields();
                    loadProductsFromDatabase();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "Error adding product: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        productNameField.setText("");
        categoryCombo.setSelectedIndex(0);
        supplierCombo.setSelectedIndex(0);
        priceField.setText("");
        stockField.setText("");
        expirationDateSpinner.setValue(new Date());
        descriptionArea.setText("");
    }

    private void deleteProduct(int productId) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Products WHERE ProductID = ?")) {
            pstmt.setInt(1, productId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                loadProductsFromDatabase();
                JOptionPane.showMessageDialog(this, "Product deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to delete product",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error deleting product: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        Add_Inventory frame = new Add_Inventory();
    }
}
