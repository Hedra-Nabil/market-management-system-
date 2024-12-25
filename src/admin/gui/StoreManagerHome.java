package admin.gui;

import ImplementationDB.SQL;
import admin.code.ImageLoader;
import casher.gui.AddCustomer;
import casher.gui.Discount;
import casher.gui.MakeNewSale;
import casher.gui.UpdateCustomer;
import wearhouse.gui.*;
import wearhouse.gui.StockShort;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.net.HttpURLConnection;
import java.net.URL;

public class StoreManagerHome extends JFrame {
    private JLabel timeLabel;
    private JLabel statusLabel;

    public StoreManagerHome() {
        setTitle("Store Manager Home");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0, 0, 35));

        JLabel titleLabel = new JLabel("Store Manager Home", JLabel.CENTER);
        titleLabel.setIcon(ImageLoader.loadImage("/Images/StoreManager.png"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(new Color(224, 224, 224));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        timeLabel.setForeground(new Color(224, 224, 224));
        timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        timeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));

        statusLabel = new JLabel();
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        statusLabel.setForeground(new Color(224, 224, 224));
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 0, 35));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(timeLabel, BorderLayout.EAST);
        headerPanel.add(statusLabel, BorderLayout.WEST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        JPanel cashierPanel = createSidePanel(new String[] {
                "Sales Operations",  "Search Products",
                "Add Customer", "Edit Customer Details", "Discount Coupon",
                "Sales Records"
        }, "Cashier Panel");
        mainPanel.add(cashierPanel, BorderLayout.EAST);

        JPanel inventoryPanel = createSidePanel(new String[] {
                "Add Products", "Products in Stock", "Production Dates",
                 "Show vendors", "Purchase Records", "Sales Records"
        }, "Inventory Panel");
        mainPanel.add(inventoryPanel, BorderLayout.WEST);
        add(mainPanel);
        startClock();
        startStatusUpdate();
    }


    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(0, 0, 35));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        String[][] buttons = {
                {"Search in Store", "/Images/search32.png"},
                {"Add Employee", "/Images/add.png"},
                {"Apply Promotion", "/Images/sale.png"},
                {"Analyze Performance", "/Images/analysis.png"},
                {"Remove Supplier", "/Images/man.png"},
                {"Remove Employee", "/Images/officer.png"},
                {"Operations Report", "/Images/growth.png"},
                {"Suppliers/Employees List", "/Images/checklist.png"},
                {"Remove Customer", "/Images/customer.png"},
                {"User Account", "/Images/user2.png"},
                {"Employee Salaries", "/Images/12.png"},
                {"Log Out", "/Images/logout.png"}
        };

        for (int i = 0; i < buttons.length; i++) {
            gbc.gridx = i % 4;
            gbc.gridy = i / 4;

            String buttonText = buttons[i][0];
            String iconPath = buttons[i][1];

            JButton button = createButtons(buttonText, iconPath);
            button.addActionListener(new ButtonActionListener(buttonText));
            buttonPanel.add(button, gbc);
        }

        return buttonPanel;
    }

    private JPanel createSidePanel(String[] buttonNames, String panelName) {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(0, 0, 50));
        sidePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(224, 224, 224), 2),
                panelName,
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 18),
                new Color(224, 224, 224)
        ));

        for (String buttonName : buttonNames) {


            JButton button = createButton(buttonName,"");
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.addActionListener(new SideButtonActionListener(buttonName));
            button.setSize(150, 50);
            button.setMaximumSize(new Dimension(150, 50));
            button.setMinimumSize(new Dimension(150, 50));
            sidePanel.add(button);
            sidePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        return sidePanel;
    }

    private JButton createButtons(String name, String iconPath) {
        JButton button = new JButton(name);
        button.setFont(new Font("Times New Roman", Font.BOLD, 20));
        button.setForeground(new Color(224, 224, 224));
        button.setBackground(new Color(128, 128, 192));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(128, 128, 192), 2));
        button.setPreferredSize(new Dimension(220, 70));


        if (iconPath != null && !iconPath.isEmpty()) {
            Icon icon  = ImageLoader.loadImage(iconPath,30,30);
            button.setIcon(icon);
            button.setHorizontalTextPosition(SwingConstants.RIGHT);
            button.setVerticalTextPosition(SwingConstants.CENTER);
        }

        return button;
    }



    private class SideButtonActionListener implements ActionListener {
        private String buttonName;

        public SideButtonActionListener(String buttonName) {
            this.buttonName = buttonName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (buttonName) {
                case "Sales Operations":
                    MakeNewSale makeNewSale=new MakeNewSale();
                    makeNewSale.setVisible(true);
                    break;
                case "Search Products":
                   new Search();
                    break;
                case "Add Customer":
                    AddCustomer addCustomer=new AddCustomer();
                    addCustomer.setVisible(true);
                    break;
                case "Edit Customer Details":
                    UpdateCustomer updateCustomer=new UpdateCustomer();
                    updateCustomer.setVisible(true);
                    break;
                case "Discount Coupon":
                    Discount discount=new Discount();
                    discount.setVisible(true);
                    break;
                case "Sales Records":
                    Sales_Records window = new Sales_Records();
                    window.main(null);

                    break;
                case "Add Products":
                   Add_Inventory Add_Inventory=new Add_Inventory();
                   Add_Inventory.setVisible(true);
                    break;
                case "Products in Stock":
                    StockShort stock=new StockShort();
                    break;
                case "Production Dates":
                    Expiry_Dates expiry_Dates=new Expiry_Dates();
                    expiry_Dates.setVisible(true);
                    break;
                case "Show vendors":
                    new showVendors();
                    break;
                case "Purchase Records":
                    Purchase_Records purchase_records=new Purchase_Records();
                    purchase_records.setVisible(true);
                    break;
                default:
                    break;
            }
        }
    }



    private JButton createButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Times New Roman", Font.BOLD, 20));
        button.setForeground(new Color(224, 224, 224));
        button.setBackground(new Color(128, 128, 192));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(128, 128, 192), 2));
        button.setPreferredSize(new Dimension(220, 70));


        if (iconPath != null && !iconPath.isEmpty()) {
            Icon icon = new ImageIcon(iconPath);
            button.setIcon(icon);
            button.setHorizontalTextPosition(SwingConstants.RIGHT);
            button.setVerticalTextPosition(SwingConstants.CENTER);
        }

        return button;
    }

    private class ButtonActionListener implements ActionListener {
        private String buttonText;

        public ButtonActionListener(String buttonText) {
            this.buttonText = buttonText;}

        public void actionPerformed(ActionEvent e) {
            switch (buttonText) {
                case "Search in Store":
                    new Search();
                    break;
                case "Add Employee":
                    AddEmployeeForm addEmployeeForm = new AddEmployeeForm();
                    addEmployeeForm.setVisible(true);
                    break;
                case "Apply Promotion":
                   DiscountGUI discountGUI = new DiscountGUI();
                   discountGUI.setVisible(true);
                    break;
                case "Analyze Performance":
                  MarketAnalysis analysis = new MarketAnalysis();
                  analysis.setVisible(true);
                    break;
                case "Remove Supplier":
                    RemoveVendor removeVendor = new RemoveVendor();
                    removeVendor.setVisible(true);
                    break;
                case "Add Supplier":
                    new AddVendor();
                    break;
                case "Remove Employee":
                    DeleteEmployeeForm deleteEmployeeForm = new DeleteEmployeeForm();
                    deleteEmployeeForm.setVisible(true);
                    break;
                case "Operations Report":
                  OperationsReport operationsReport = new OperationsReport();
                  operationsReport.setVisible(true);
                    break;
                case "Suppliers/Employees List":
                    EmployeeSupplierView employeeSupplierView=new EmployeeSupplierView();
                    employeeSupplierView.setVisible(true);
                    break;
                case "Remove Customer":
                    DeleteCustomerForm deleteCustomerForm = new DeleteCustomerForm();
                    deleteCustomerForm.setVisible(true);
                    break;
                case "User Account":
                    UserAccountView userAccountView = new UserAccountView();
                    userAccountView.setVisible(true);
                    break;
                case "Employee Salaries":
                    EmployeeSalaries employeeSalaries = new EmployeeSalaries();
                    employeeSalaries.setVisible(true);
                    break;
                case "Log Out":
                    performLogout();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Action not implemented for: " + buttonText);
                    break;
            }
        }


        public void performLogout() {
            int response = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to log out?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                dispose();

                JOptionPane.showMessageDialog(null, "Logged out successfully");
                LoginGUI loginGUI = new LoginGUI();
                loginGUI.setVisible(true);
            }
        }
    }





    private void startClock () {
    Timer timer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            updateTime();
        }
    });
    timer.start();
}

    private void updateTime () {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date();
    timeLabel.setText(dateFormat.format(date));
}

    private void startStatusUpdate () {
    Timer statusTimer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            updateStatus();
        }
    });
    statusTimer.start();

}

    private void updateStatus () {
    String internetStatus = checkInternetConnection() ? "Connected" : "Disconnected";
    String batteryStatus = checkBatteryStatus();
    String dbStatus = checkDatabaseConnection() ? " Online" : "Offline";

    statusLabel.setText("Internet: " + internetStatus + " | Battery: " + batteryStatus + " | DB: " + dbStatus);
}

    private boolean checkInternetConnection () {
    try {
        URL url = new URL("http://www.google.com");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("HEAD");
        connection.setConnectTimeout(2000);
        connection.connect();
        return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
    } catch (Exception e) {
        return false;
    }
}

    private String checkBatteryStatus () {
    try {
        String command = "wmic path win32_battery get estimatedchargeremaining";
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        String batteryStatus = "Unknown";
        while ((line = reader.readLine()) != null) {
            if (line.trim().matches("\\d+")) {
                batteryStatus = line.trim() + "%";
            }
        }
        return batteryStatus;
    } catch (Exception e) {
        return "Error fetching battery status";
    }
}

    private boolean checkDatabaseConnection () {

    Timer dbCheckTimer = new Timer(30000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            boolean isConnectedNow = SQL.checkDatabaseConnection();
            String dbStatusNow = isConnectedNow ? "Online" : "Offline";
            statusLabel.setText(statusLabel.getText().replaceAll("DB: .*", "DB: " + dbStatusNow));

        }
    });
    dbCheckTimer.start();
    dbCheckTimer.stop();
    return true;


}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StoreManagerHome().setVisible(true);
            }
        });
    }
}