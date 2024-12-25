package wearhouse.gui;

import admin.code.ImageLoader;
import wearhouse.code.allOperations;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


    public class showVendors extends allOperations {
        public showVendors() {
            JFrame frame = new JFrame();
            frame.setTitle("View Vendor Details");
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setBackground(new Color(17, 24, 47));
            frame.setSize(630, 500);
            frame.setResizable(false);
            frame.setLayout(null);
            frame.setLocationRelativeTo(null);

            JLabel text1 = new JLabel("View Vendor Details");
            text1.setFont(new Font("Times New Roman", Font.PLAIN, 35));
            ImageIcon imageTop = ImageLoader.loadImage(("/images/add-user.png"));
            text1.setIcon(imageTop);
            text1.setBounds(140, 22, 400, 55);
            text1.setForeground(Color.WHITE);
            text1.setHorizontalTextPosition(JLabel.LEFT);

            JLabel supplierIDLabel = new JLabel("Supplier ID:");
            supplierIDLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            supplierIDLabel.setForeground(Color.WHITE);
            supplierIDLabel.setBounds(20, 150, 120, 30);

            JTextField supplierIDField = new JTextField(15);
            supplierIDField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            supplierIDField.setBounds(150, 150, 150, 30);

            JButton searchButton = new JButton("Search");
            searchButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
            searchButton.setBounds(320, 150, 120, 30);
            searchButton.setFocusable(false);


            String[] columnNames = {"Supplier ID", "Name", "Phone", "Email", "Address"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            JTable vendorTable = new JTable(model);
            JScrollPane tableScrollPane = new JScrollPane(vendorTable);
            tableScrollPane.setBounds(20, 200, 575, 240);

            ImageIcon logoutIcon = ImageLoader.loadImage(("/images/logout.png"));
            JButton logButton = new JButton();
            logButton.setIcon(logoutIcon);
            logButton.setBounds(560, 5, 50, 30);
            logButton.setFocusable(false);

            JButton showAllButton = new JButton("Show All Vendors");
            showAllButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
            showAllButton.setBounds(450, 150, 150, 30);
            showAllButton.setFocusable(false);


            showAllButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showAllVendors(vendorTable);
                }
            });



            logButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });


            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String supplierID = supplierIDField.getText();
                    searchVendor(supplierID, vendorTable);
                }
            });


            frame.add(text1);
            frame.add(supplierIDLabel);
            frame.add(supplierIDField);
            frame.add(searchButton);
            frame.add(tableScrollPane);
            frame.add(logButton);
            frame.add(showAllButton);

            frame.setVisible(true);
        }
    }


