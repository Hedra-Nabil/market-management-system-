package wearhouse.gui;

import admin.code.ImageLoader;
import wearhouse.code.allOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Update extends allOperations {
    public Update(){
        JFrame frame=new JFrame();
        frame.setTitle("Update");
     //   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(17, 24, 47));
        frame.setSize(1200, 630);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        JLabel text1=new JLabel("Update Inventory ");
        text1.setFont(new Font("Times New Roman", Font.PLAIN, 35));
        ImageIcon imageTop = ImageLoader.loadImage(("/images/updateinventory.png"));
        text1.setIcon(imageTop);
        text1.setBounds(430,20,400,50);
        text1.setForeground(Color.WHITE);
        text1.setHorizontalTextPosition(JLabel.LEFT);

        JLabel batch =new JLabel("Update Batch Details");

        batch.setFont(new Font("Times New Roman", Font.BOLD, 20));

        batch.setBounds(150,140,200,30);
        batch.setForeground(Color.WHITE);



        JLabel batchID=new JLabel("Batch ID:");

        batchID.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        batchID.setBounds(10,190,100,30);
        batchID.setForeground(Color.WHITE);


        JTextField batchID1=new JTextField(15);

        batchID1.setBounds(140,190,200,30);

        batchID1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        JButton checkLeft=new JButton("Check");
        checkLeft.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        ImageIcon check = ImageLoader.loadImage(("/images/check-mark.png"));
        checkLeft.setIcon(check);
        checkLeft.setHorizontalTextPosition(JLabel.RIGHT);
        checkLeft.setBounds(735,190,150,40);
        checkLeft.setFocusable(Boolean.FALSE);

        JPanel panelLeft= new JPanel(null);
        JPanel panelRight= new JPanel(null);
        panelLeft.setBounds(10,250,500,250);
        panelRight.setBounds(650,250,500,250);
        panelRight.setBackground(new Color(86, 66, 177));
        panelLeft.setBackground(new Color(86, 66, 177));


        JLabel VendorID=new JLabel(" Vendor ID:");
        JTextField vendorID1=new JTextField(15);

        vendorID1.setFont(new Font(" Times New Roman", Font.PLAIN, 20));
        VendorID.setFont(new Font(" Times New Roman", Font.PLAIN, 20));
        VendorID.setForeground(Color.WHITE);


        JLabel ExpiryDate=new JLabel(" Expiry Date:");
        JTextField expiryDate1=new JTextField(15);
        expiryDate1.setFont(new Font(" Times New Roman", Font.PLAIN, 20));
        ExpiryDate.setFont(new Font(" Times New Roman", Font.PLAIN, 20));
        ExpiryDate.setForeground(Color.WHITE);



        JLabel Stock=new JLabel(" Quantity:");
        JSpinner stock1=new JSpinner(new SpinnerNumberModel(0,0,10000,1));
        stock1.setFont(new Font(" Times New Roman", Font.PLAIN, 20));
        Stock.setFont(new Font(" Times New Roman", Font.PLAIN, 20));
        Stock.setForeground(Color.WHITE);

        VendorID.setBounds(20, 30, 120, 30);
        vendorID1.setBounds(315, 30, 150, 30);
        ExpiryDate.setBounds(20, 110, 120, 30);
        expiryDate1.setBounds(315, 110, 150, 30);
        Stock.setBounds(20, 180, 120, 30);
        stock1.setBounds(315, 180, 150, 30);


        JLabel Price=new JLabel( " Price:");
        JTextField price1=new JTextField(15);
        price1.setFont(new Font(" Times New Roman", Font.PLAIN, 20));
        Price.setFont(new Font(" Times New Roman", Font.PLAIN, 20));
        Price.setForeground(Color.WHITE);
        JLabel CategoryID=new JLabel(" Category:");
        JComboBox<String> categoryComboBox = new JComboBox<>();
        categoryComboBox.setFont(new Font(" Times New Roman", Font.PLAIN, 20));
        CategoryID.setFont(new Font(" Times New Roman", Font.PLAIN, 20));
        CategoryID.setForeground(Color.WHITE);

        allOperations operations = new allOperations();
        operations.loadCategories(categoryComboBox);
        JLabel Name=new JLabel(" Name:");
        JTextField name1=new JTextField(15);
        name1.setFont(new Font(" Times New Roman", Font.PLAIN, 20));
        Name.setFont(new Font(" Times New Roman", Font.PLAIN, 20));
        Name.setForeground(Color.WHITE);
        JLabel Description=new JLabel(" Description:");
        JTextArea description1=new JTextArea();
        description1.setFont(new Font(" Times New Roman", Font.PLAIN, 20));
        Description.setFont(new Font(" Times New Roman", Font.PLAIN, 20));
        Description.setForeground(Color.WHITE);
        description1.setLineWrap(true);
        description1.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(description1);


        Name.setBounds(20, 15, 120, 30);
        name1.setBounds(320, 15, 150, 30);
        Price.setBounds(20, 70, 120, 30);
        price1.setBounds(320, 70, 150, 30);
        CategoryID.setBounds(20, 130, 120, 30);
        categoryComboBox.setBounds(320, 130, 150, 30);
        Description.setBounds(20, 190, 120, 30);
        scrollPane.setBounds(270, 190, 200, 50);
        ImageIcon imageUpdate = ImageLoader.loadImage(("/images/updated.png"));
        JButton UpdateRight=new JButton("Update");
        UpdateRight.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        UpdateRight.setIcon(imageUpdate);
        UpdateRight.setHorizontalTextPosition(JLabel.RIGHT);
        UpdateRight.setBounds(950,190,150,40);
        UpdateRight.setFocusable(Boolean.FALSE);


        ImageIcon logout = ImageLoader.loadImage(("/images/logout.png"));
        JButton log=new JButton();
        log.setIcon(logout);
        log.setBounds(1130,5,50,30);
        log.setFocusable(Boolean.FALSE);


        log.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        checkLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String batchID = batchID1.getText();
                performCheck(batchID,
                        vendorID1,
                        expiryDate1,
                        stock1,
                        name1,
                        price1,
                        categoryComboBox,
                        description1);
            }
        });
        UpdateRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String batchID = batchID1.getText();
                String vendorID = vendorID1.getText();
                int quantity = (int) stock1.getValue();
                double price = Double.parseDouble(price1.getText());
                String expiryDate = expiryDate1.getText();
                String description = description1.getText();
                performUpdate(batchID, vendorID, quantity, price, expiryDate, description);
            }


        });







        panelLeft.add(VendorID);
        panelLeft.add(vendorID1);
        panelLeft.add(ExpiryDate);
        panelLeft.add(expiryDate1);
        panelLeft.add(Stock);
        panelLeft.add(stock1);

        panelRight.add(Name);
        panelRight.add(name1);
        panelRight.add(Price);
        panelRight.add(price1);
        panelRight.add(CategoryID);
        panelRight.add(categoryComboBox);
        panelRight.add(Description);
        panelRight.add(scrollPane);;



        frame.add(text1);
        frame.add(batch);

        frame.add(batchID);

        frame.add(batchID1);
        frame.add(checkLeft);



        frame.add(panelLeft);
        frame.add(panelRight);


        frame.add(UpdateRight);
        frame.add(log);




        frame.setVisible(true);
    }}
