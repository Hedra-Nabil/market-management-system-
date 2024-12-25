package wearhouse.gui;

import admin.code.ImageLoader;
import wearhouse.code.allOperations;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    public class Search extends allOperations {
        public Search() {
            JFrame frame = new JFrame("Search Product");
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.getContentPane().setBackground(new Color(17, 24, 47));
            frame.setLayout(null);
            frame.setLocationRelativeTo(null);

            JLabel titleLabel = new JLabel("Search Product");
            ImageIcon imageTop = ImageLoader.loadImage(("/images/search32.png"));
            titleLabel.setIcon(imageTop);
            titleLabel.setHorizontalTextPosition(JLabel.LEFT);
            titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setBounds(300, 20, 400, 40);


            JLabel searchLabel = new JLabel("Enter Product ID:");
            searchLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            searchLabel.setForeground(Color.WHITE);
            searchLabel.setBounds(50, 80, 200, 30);

            JTextField searchField = new JTextField();
            searchField.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            searchField.setBounds(250, 80, 300, 30);


            JButton searchButton = new JButton("Search");
            searchButton.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            searchButton.setBounds(570, 80, 120, 30);
            searchButton.setBackground(new Color(86, 66, 177));
            searchButton.setForeground(Color.WHITE);
            searchButton.setFocusable(false);


            String[] columnNames = {
                    "ProductID", "Name", "CategoryID", "SupplierID",
                    "Price", "Stock", "ExpirationDate", "Description"
            };

            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            JTable productTable = new JTable(tableModel);
            productTable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            productTable.setRowHeight(25);

            JScrollPane scrollPane = new JScrollPane(productTable);
            scrollPane.setBounds(50, 150, 700, 350);

            ImageIcon logoutIcon = ImageLoader.loadImage(("/images/logout.png"));
            JButton backButton = new JButton();
            backButton.setIcon(logoutIcon);
            backButton.setBounds(730, 5, 50, 30);
            backButton.setFocusable(false);
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });


            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String productID = searchField.getText();
                    if (!productID.isEmpty()) {

                        searchProductByID(productID, tableModel);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter a Product ID", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });


            frame.add(titleLabel);
            frame.add(searchLabel);
            frame.add(searchField);
            frame.add(searchButton);
            frame.add(scrollPane);
            frame.add(backButton);

            frame.setVisible(true);
        }
    }


