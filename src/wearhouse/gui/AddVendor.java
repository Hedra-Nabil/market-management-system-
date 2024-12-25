package wearhouse.gui;

import admin.code.ImageLoader;
import wearhouse.code.allOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddVendor extends allOperations {
    public AddVendor(){
        JFrame frame = new JFrame();
        frame.setTitle("Add Vendor");
      //  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(17, 24, 47));
        frame.setSize(600, 500);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        JLabel text1 = new JLabel("Add Vendor");
        text1.setFont(new Font("Times New Roman", Font.PLAIN, 35));
        ImageIcon imageTop = ImageLoader.loadImage(("/images/add-user.png"));
        text1.setIcon(imageTop);
        text1.setBounds(180, 22, 400, 55);
        text1.setForeground(Color.WHITE);
        text1.setHorizontalTextPosition(JLabel.LEFT);


        JLabel supplierID = new JLabel("Supplier ID:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel phoneLabel = new JLabel("Phone:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel addressLabel = new JLabel("Address:");

        supplierID.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        nameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        phoneLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        emailLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        addressLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        supplierID.setBounds(20, 150, 120, 30);
        nameLabel.setBounds(20, 200, 120, 30);
        phoneLabel.setBounds(20, 250, 120, 30);
        emailLabel.setBounds(20, 300, 120, 30);
        addressLabel.setBounds(20, 350, 120, 30);

        supplierID.setForeground(Color.WHITE);
        nameLabel.setForeground(Color.WHITE);
        phoneLabel.setForeground(Color.WHITE);
        emailLabel.setForeground(Color.WHITE);
        addressLabel.setForeground(Color.WHITE);

        JTextField supplierIDField = new JTextField(15);
        JTextField nameField = new JTextField(15);
        JTextField phoneField = new JTextField(15);
        JTextField emailField = new JTextField(15);
        JTextArea addressField = new JTextArea();
        addressField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        addressField.setLineWrap(true);
        addressField.setWrapStyleWord(true);

        JScrollPane addressScrollPane = new JScrollPane(addressField);

        supplierIDField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        nameField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        phoneField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        emailField.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        supplierIDField.setBounds(200, 150, 150, 30);
        nameField.setBounds(200, 200, 150, 30);
        phoneField.setBounds(200, 250, 150, 30);
        emailField.setBounds(200, 300, 150, 30);
        addressScrollPane.setBounds(200, 350, 150, 50);

        JButton addButton = new JButton("Add Vendor");
        addButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        ImageIcon addIcon = new ImageIcon("add.png");
        addButton.setIcon(addIcon);
        addButton.setBounds(380, 420, 200, 40);
        addButton.setHorizontalTextPosition(JLabel.RIGHT);
        addButton.setFocusable(Boolean.FALSE);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String supplierID = supplierIDField.getText();
                String name = nameField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();
                String address = addressField.getText();

                addVendor(supplierID, name, phone, email, address, frame);

            }
        });
        ImageIcon logoutIcon = ImageLoader.loadImage(("/images/logout.png"));
        JButton logButton = new JButton();
        logButton.setIcon(logoutIcon);
        logButton.setBounds(530, 5, 50, 30);
        logButton.setFocusable(Boolean.FALSE);

        logButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.add(text1);

        frame.add(supplierID);
        frame.add(nameLabel);
        frame.add(phoneLabel);
        frame.add(emailLabel);
        frame.add(addressLabel);
        frame.add(supplierIDField);
        frame.add(nameField);
        frame.add(phoneField);
        frame.add(emailField);
        frame.add(addressScrollPane);
        frame.add(addButton);
        frame.add(logButton);

        frame.setVisible(true);
    }
}
