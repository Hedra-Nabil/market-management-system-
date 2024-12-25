package admin.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ImplementationDB.SQL;
import casher.gui.CashierHome;
import admin.code.ImageLoader;
import wearhouse.gui.Warehouse;


public class LoginGUI extends JFrame {
    public LoginGUI() {
        setTitle("Welcome to the System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setBackground(new Color(0, 0, 35));


        setLayout(new BorderLayout());


        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(0, 0, 35));


        JPanel welcomePanel = new JPanel();
        welcomePanel.setBackground(new Color(0, 0, 35));
        JLabel welcomeLabel = new JLabel("Welcome to \n market system ");
        welcomeLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 65));
        welcomeLabel.setForeground(new Color(255, 255, 255));

        ImageIcon welcomeIcon = ImageLoader.loadImage("/images/shop.png");

        JLabel welcomeIconLabel = new JLabel(welcomeIcon);
        welcomePanel.add(welcomeLabel);
        welcomePanel.add(welcomeIconLabel);

        leftPanel.add(welcomePanel);


        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(0, 0, 35));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel cnicLabel = new JLabel("CNIC:");
        cnicLabel.setForeground(new Color(255, 255, 255));
        cnicLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        formPanel.add(cnicLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JPanel cnicPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        cnicPanel.setBackground(new Color(0, 0, 35));
        ImageIcon cnicIcon  = ImageLoader.loadImage("/images/officer.png");

        JLabel cnicIconLabel = new JLabel(cnicIcon);
        JTextField cnicField = new JTextField(30);
        cnicField.setBackground(Color.WHITE);
        cnicField.setForeground(new Color(0, 0, 0));
        cnicField.setBorder(BorderFactory.createLineBorder(new Color(128, 128, 192), 2));
        cnicPanel.add(cnicIconLabel);
        cnicPanel.add(cnicField);
        formPanel.add(cnicPanel, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        passwordLabel.setForeground(new Color(255, 255, 255));
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        passwordPanel.setBackground(new Color(0, 0, 35));
        ImageIcon passwordIcon  = ImageLoader.loadImage("/images/key.png");
        JLabel passwordIconLabel = new JLabel(passwordIcon);
        JPasswordField passwordField = new JPasswordField(30);
        passwordField.setBackground(Color.WHITE);
        passwordField.setForeground(new Color(0, 0, 0));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(128, 128, 192), 2));
        passwordPanel.add(passwordIconLabel);
        passwordPanel.add(passwordField);
        formPanel.add(passwordPanel, gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel interfaceLabel = new JLabel("Interface:");
        interfaceLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        interfaceLabel.setForeground(new Color(255, 255, 255));
        formPanel.add(interfaceLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JPanel interfacePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        interfacePanel.setBackground(new Color(0, 0, 35));
        ImageIcon interfaceIcon = ImageLoader.loadImage("/images/man.png");
        JLabel interfaceIconLabel = new JLabel(interfaceIcon);
        JComboBox<String> interfaceCombo = new JComboBox<>(new String[] {"SELECT", "CASHIER", "WAREHOUSE MANAGER", "STORE MANAGER"});
        interfaceCombo.setBackground(Color.WHITE);
        interfaceCombo.setForeground(new Color(0, 0, 0));
        interfaceCombo.setBorder(BorderFactory.createLineBorder(new Color(128, 128, 192), 2));
        interfacePanel.add(interfaceIconLabel);
        interfacePanel.add(interfaceCombo);
        formPanel.add(interfacePanel, gbc);

        leftPanel.add(formPanel);


        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setBackground(new Color(0, 0, 35));


        JButton loginButton = new JButton("Log In");
        loginButton.setBackground(new Color(128, 128, 192));
        loginButton.setForeground(new Color(224, 224, 224));
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setPreferredSize(new Dimension(150, 50));
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(128, 128, 192), 2));
        buttonsPanel.add(loginButton);


        JButton logoutButton = new JButton("Exit");
        logoutButton.setBackground(new Color(255, 75, 75));
        logoutButton.setForeground(new Color(224, 224, 224));
        logoutButton.setFont(new Font("Arial", Font.BOLD, 18));
        logoutButton.setPreferredSize(new Dimension(150, 50));
        logoutButton.setBorder(BorderFactory.createLineBorder(new Color(128, 128, 192), 2));
        buttonsPanel.add(logoutButton);

        leftPanel.add(buttonsPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(0, 0, 35));

// تحميل الصورة
        ImageIcon imageIcon = ImageLoader.loadImage("/images/cyber-security.jpg");

// إنشاء JLabel لعرض الصورة
        JLabel imageLabel = new JLabel(imageIcon);

// تحديد الحجم
        imageLabel.setPreferredSize(new Dimension(900, 900));
        rightPanel.setPreferredSize(imageLabel.getPreferredSize());

// إضافة JLabel إلى panel
        rightPanel.add(imageLabel);


        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String cnic = cnicField.getText();
                String password = new String(passwordField.getPassword());
                String selectedInterface = (String) interfaceCombo.getSelectedItem();

                if (cnic.isEmpty() || password.isEmpty() || selectedInterface.equals("SELECT")) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields correctly", "Error", JOptionPane.ERROR_MESSAGE);
                } else {

                    String role = SQL.login(cnic, password);

                    if (role == null) {
                        JOptionPane.showMessageDialog(null, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {

                        if (selectedInterface.equals("CASHIER") && role.equals("1")) {
                            JOptionPane.showMessageDialog(null, "Welcome Cashier", "Login Successful", JOptionPane.INFORMATION_MESSAGE);

                            CashierHome cashier = new CashierHome();
                            cashier.setVisible(true);
                            dispose();
                        } else if (selectedInterface.equals("WAREHOUSE MANAGER") && role.equals("2")) {
                            JOptionPane.showMessageDialog(null, "Welcome Warehouse Manager", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                            Warehouse warehouse= new Warehouse();

                            dispose();
                        } else if (selectedInterface.equals("STORE MANAGER") && role.equals("3")) {
                            JOptionPane.showMessageDialog(null, "Welcome Store Manager", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                            StoreManagerHome manage = new StoreManagerHome();
                            manage.setVisible(true);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Access Denied. You don't have permission for this interface.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(null, "Exit........Goodbye", "Goodbye", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginGUI();
    }
}
