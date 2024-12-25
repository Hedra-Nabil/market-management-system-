package wearhouse.gui;

import admin.code.ImageLoader;
import wearhouse.code.allOperations;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    public class StockShort extends allOperations {
        public StockShort(){
            JFrame frame = new JFrame("Low Stock Products");
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.getContentPane().setBackground(new Color(17, 24, 47));
            frame.setLayout(null);
            frame.setLocationRelativeTo(null);


            JLabel titleLabel = new JLabel("Low Stock Products");
            titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setBounds(250, 20, 400, 40);
            ImageIcon imageTop  = ImageLoader.loadImage(("/images/checklist1.png"));
            titleLabel.setIcon(imageTop);
            titleLabel.setHorizontalTextPosition(JLabel.LEFT);


            JLabel quantityLabel = new JLabel("Enter Minimum Quantity:");
            quantityLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            quantityLabel.setForeground(Color.WHITE);
            quantityLabel.setBounds(50, 80, 200, 30);

            JTextField quantityField = new JTextField();
            quantityField.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            quantityField.setBounds(250, 80, 200, 30);


            JButton checkButton = new JButton("Check");
            checkButton.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            checkButton.setBounds(470, 80, 120, 30);
            checkButton.setBackground(new Color(86, 66, 177));
            checkButton.setForeground(Color.WHITE);
            checkButton.setFocusable(false);
            ImageIcon check  = ImageLoader.loadImage(("/images/check-mark.png"));
            checkButton.setIcon(check);
            checkButton.setHorizontalTextPosition(JLabel.RIGHT);


            String[] columnNames = {
                    "ProductID", "Name", "CategoryID", "SupplierID",
                    "Stock", "Description"
            };

            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            JTable lowStockTable = new JTable(tableModel);
            lowStockTable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            lowStockTable.setRowHeight(25);

            JScrollPane scrollPane = new JScrollPane(lowStockTable);
            scrollPane.setBounds(50, 150, 700, 350);

            ImageIcon logoutIcon = ImageLoader.loadImage(("/images/logout.png"));
            JButton backButton = new JButton();
            backButton.setIcon(logoutIcon);
            backButton.setBounds(730, 5, 50, 30);
            backButton.setFocusable(false);
            backButton.setFocusable(false);
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });


            checkButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String quantityText = quantityField.getText();
                    if (!quantityText.isEmpty()) {
                        try {
                            int minQuantity = Integer.parseInt(quantityText);

                            showLowStockProducts(minQuantity, tableModel);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame,ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter a quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });


            frame.add(titleLabel);
            frame.add(quantityLabel);
            frame.add(quantityField);
            frame.add(checkButton);
            frame.add(scrollPane);
            frame.add(backButton);

            frame.setVisible(true);
        }
    }

