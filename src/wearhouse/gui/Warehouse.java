package wearhouse.gui;

import admin.code.ImageLoader;
import admin.gui.LoginGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Warehouse {
    public Warehouse() {

            JFrame frame = new JFrame();
            frame.setTitle("Warehouse Manager Home");
           // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setBackground(new Color(17, 24, 47));
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setResizable(false);
            frame.setLayout(null);
            frame.setLocationRelativeTo(null);


            JLabel text1 = new JLabel("Warehouse Manager Home");
            text1.setFont(new Font("Times New Roman", Font.PLAIN, 30));
            ImageIcon imageTop = ImageLoader.loadImage(("/images/addinventory.png"));
            text1.setIcon(imageTop);
            text1.setBounds(485, 20, 420, 50);
            text1.setForeground(Color.WHITE);
            text1.setHorizontalTextPosition(JLabel.LEFT);


            JButton Purchase = new JButton("Purchase Records");
            Purchase.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            Purchase.setBounds(380, 170, 300, 70);
            Purchase.setBackground(new Color(86, 66, 177));
            Purchase.setForeground(Color.WHITE);
            Purchase.setIcon(ImageLoader.loadImage("/images/bill.png"));
            Purchase.setHorizontalTextPosition(JButton.RIGHT);
            Purchase.setFocusable(false);
            Purchase.addActionListener(new ActionListener() {

                @Override
            public void actionPerformed(ActionEvent e) {
                new Purchase_Records();

            }
        });

            JButton Sales = new JButton("Sales Records");
            Sales.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            Sales.setBounds(380, 255, 300, 70);
            Sales.setBackground(new Color(86, 66, 177));
            Sales.setForeground(Color.WHITE);

            Sales.setIcon(ImageLoader.loadImage("/images/shopping-cart64.png"));
            Sales.setHorizontalTextPosition(JButton.RIGHT);
            Sales.setFocusable(false);
            Sales.addActionListener(new ActionListener() {

                @Override
            public void actionPerformed(ActionEvent e) {


                Sales_Records window = new Sales_Records();
                window.main(null);



            }
        });

            JButton expiryDateButton = new JButton("Expiry Dates");
            expiryDateButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            expiryDateButton.setBounds(380, 340, 300, 70);
            expiryDateButton.setBackground(new Color(86, 66, 177));
            expiryDateButton.setForeground(Color.WHITE);
            expiryDateButton.setIcon(ImageLoader.loadImage("/images/searchreceipt.png"));
            expiryDateButton.setHorizontalTextPosition(JButton.RIGHT);
            expiryDateButton.setFocusable(false);
            expiryDateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Expiry_Dates();


            }
        });

            JButton stockShortageButton = new JButton("Stock Shortages");
            stockShortageButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            stockShortageButton.setBounds(380, 425, 300, 70);
            stockShortageButton.setBackground(new Color(86, 66, 177));
            stockShortageButton.setForeground(Color.WHITE);
            stockShortageButton.setIcon(ImageLoader.loadImage("/images/checklist1.png"));
            stockShortageButton.setHorizontalTextPosition(JButton.RIGHT);
            stockShortageButton.setFocusable(false);
            stockShortageButton.addActionListener(new ActionListener() {

                @Override
            public void actionPerformed(ActionEvent e) {
                new StockShort();
            }
        });


            JButton searchButton = new JButton("Search");
            searchButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            searchButton.setBounds(470, 540, 500, 70);
            searchButton.setBackground(new Color(86, 66, 177));
            searchButton.setForeground(Color.WHITE);
            searchButton.setIcon(ImageLoader.loadImage("/images/search32.png"));
            searchButton.setHorizontalTextPosition(JButton.RIGHT);
            searchButton.setFocusable(false);
            searchButton.addActionListener(new ActionListener() {

                @Override
            public void actionPerformed(ActionEvent e) {
                new Search();
            }
        });
            JButton addInventoryButton = new JButton("Add Inventory");
            addInventoryButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            addInventoryButton.setBounds(840, 255, 300, 70);
            addInventoryButton.setBackground(new Color(86, 66, 177));
            addInventoryButton.setForeground(Color.WHITE);
            addInventoryButton.setIcon(ImageLoader.loadImage("/images/plus.png"));
            addInventoryButton.setHorizontalTextPosition(JButton.RIGHT);
            addInventoryButton.setFocusable(false);
            addInventoryButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Add_Inventory();

            }
        });


            JButton updateInventoryButton = new JButton("Update Inventory");
            updateInventoryButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            updateInventoryButton.setBounds(840, 170, 300, 70);
            updateInventoryButton.setBackground(new Color(86, 66, 177));
            updateInventoryButton.setForeground(Color.WHITE);
            updateInventoryButton.setIcon(ImageLoader.loadImage("/images/updated.png"));
            updateInventoryButton.setHorizontalTextPosition(JButton.RIGHT);
            updateInventoryButton.setFocusable(false);
            updateInventoryButton.addActionListener(new ActionListener() {

                @Override
            public void actionPerformed(ActionEvent e) {
                new Update();
            }
        });

            JButton addVendorButton = new JButton("Add Vendor");
            addVendorButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            addVendorButton.setBounds(840, 425, 300, 70);
            addVendorButton.setBackground(new Color(86, 66, 177));
            addVendorButton.setForeground(Color.WHITE);
            addVendorButton.setIcon(ImageLoader.loadImage("/images/user (1).png"));
            addVendorButton.setHorizontalTextPosition(JButton.RIGHT);
            addVendorButton.setFocusable(false);
            addVendorButton.addActionListener(new ActionListener() {

                @Override
            public void actionPerformed(ActionEvent e) {
                new AddVendor();
            }
        });
            JButton viewVendorsButton = new JButton("View Vendors");
            viewVendorsButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            viewVendorsButton.setBounds(840, 340, 300, 70);
            viewVendorsButton.setBackground(new Color(86, 66, 177));
            viewVendorsButton.setForeground(Color.WHITE);
            viewVendorsButton.setIcon(ImageLoader.loadImage("/images/officer.png"));
            viewVendorsButton.setHorizontalTextPosition(JButton.RIGHT);
            viewVendorsButton.setFocusable(false);


            viewVendorsButton.addActionListener(new ActionListener() {

                @Override
            public void actionPerformed(ActionEvent e) {
                new showVendors();
            }
        });

        ImageIcon logoutIcon = ImageLoader.loadImage(("/images/logout.png"));

            JButton logoutButton = new JButton();
            logoutButton.setIcon(logoutIcon);
            logoutButton.setBounds(1360, 5, 50, 30);
            logoutButton.setFocusable(false);
            logoutButton.addActionListener(new ActionListener() {

                @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                    LoginGUI login = new LoginGUI();
                    login.setVisible(true);
            }
        });

        frame.add(text1);
        frame.add(Purchase);
        frame.add(Sales);
        frame.add(expiryDateButton);
        frame.add(stockShortageButton);
        frame.add(searchButton);
        frame.add(addInventoryButton);
        frame.add(updateInventoryButton);
        frame.add(addVendorButton);
        frame.add(logoutButton);
        frame.add(viewVendorsButton);


        frame.setVisible(true);
}
}





