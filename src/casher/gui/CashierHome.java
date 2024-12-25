package casher.gui;

import admin.code.ImageLoader;
import admin.gui.LoginGUI;
import casher.code.Cashier;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class CashierHome extends JFrame {
    private JLabel lblClassName, lblWelcome;
    private JTextArea txtReceipts, txtCoupons;
    private JButton btnMakeSale, btnUpdateCustomer, btnSearchReceipt, btnLogout;
    private JButton btnNextReceipt, btnPreviousReceipt;

    private Cashier cashier;
    private int currentReceiptIndex = 0;

    public CashierHome() {
        cashier = new Cashier();

        setTitle("Cashier Home");
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
     //   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(10, 25, 47));

        lblClassName = new JLabel("Cashier Home");
        lblClassName.setIcon(new ImageIcon("/Images/shopping-cart64.png"));
        lblClassName.setHorizontalTextPosition(SwingConstants.LEFT);
        lblClassName.setVerticalTextPosition(SwingConstants.CENTER);

        lblClassName.setFont(new Font("SansSerif", Font.BOLD, 30));
        lblClassName.setForeground(Color.WHITE);
        lblClassName.setHorizontalAlignment(SwingConstants.CENTER);

        lblWelcome = new JLabel("\n Welcome");
        lblWelcome.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);

        txtReceipts = createTextArea();
        txtCoupons = createTextArea();


        btnMakeSale = createButton(" Make New Sale", ImageLoader.loadImage("/images/shopping-cart64.png"));
        btnUpdateCustomer = createButton(" Update Customer", ImageLoader.loadImage("/images/add-user.png"));
        btnSearchReceipt = createButton("Search", ImageLoader.loadImage("/images/searchreceipt.png"));
        btnLogout = createButton("Logout", ImageLoader.loadImage("/images/logout.png"));


        btnNextReceipt = new JButton("Next");
        btnPreviousReceipt = new JButton("Previous");

        styleNavigationButtons();

        setupLeftPanel();
        setupCenterPanel();
        setupRightPanel();

        loadReceipts();
        loadCoupons();

        btnMakeSale.addActionListener(e ->
                SwingUtilities.invokeLater(() -> {
                    MakeNewSale frame = new MakeNewSale();
                    frame.setVisible(true);
                }));
        btnUpdateCustomer.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            UpdateCustomer frame = new UpdateCustomer();
            frame.setVisible(true);
        }));
        btnSearchReceipt.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            ReceiptSearch frame = new ReceiptSearch();
            frame.setVisible(true);
        }));
        btnLogout.addActionListener(e -> {
            dispose();

            SwingUtilities.invokeLater(() -> {
                LoginGUI loginGUI = new LoginGUI();
                loginGUI.setVisible(true);
            });
        });


        btnNextReceipt.addActionListener(e -> {
            try {
                if (currentReceiptIndex < cashier.fetchSales().size() - 1) {
                    currentReceiptIndex++;
                    displayReceipt();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        btnPreviousReceipt.addActionListener(e -> {
            if (currentReceiptIndex > 0) {
                currentReceiptIndex--;
                displayReceipt();
            }
        });
    }

    private JButton createButton(String text, ImageIcon icon) {
        JButton button = new JButton(text, icon);
        button.setPreferredSize(new Dimension(80, 25));
        button.setBackground(new Color(193, 143, 255));
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setFont(new Font("SansSerif", Font.PLAIN, 20)); 
        return button;
    }

    private JTextArea createTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 20));
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(new Color(53, 78, 173, 255));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setSize(200, 200);
        textArea.setMaximumSize(textArea.getPreferredSize());
        textArea.setWrapStyleWord(true);
        return textArea;
    }
    private void setupLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBackground(new Color(10, 25, 47));

        // تكبير عرض الفاتورة لتأخذ ربع الشاشة
        JScrollPane receiptsScroll = new JScrollPane(txtReceipts);
        receiptsScroll.setPreferredSize(new Dimension(200, 200)); // تكبير النص
        leftPanel.add(receiptsScroll, BorderLayout.CENTER);

        // تكبير النص الخاص بالبانيل الجانبي
        JLabel receiptsLabel = new JLabel("Receipts");
        receiptsLabel.setFont(new Font("SansSerif", Font.BOLD, 24)); // تكبير الخط
        receiptsLabel.setForeground(Color.WHITE); // اللون الأبيض
        leftPanel.add(receiptsLabel, BorderLayout.NORTH);

        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(new Color(10, 25, 47));
        navigationPanel.add(btnPreviousReceipt);
        navigationPanel.add(btnNextReceipt);

        leftPanel.add(navigationPanel, BorderLayout.SOUTH);
        add(leftPanel, BorderLayout.WEST);
    }

    private void setupRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(new Color(10, 25, 47));

        // تكبير عرض الكوبونات لتأخذ ربع الشاشة
        JScrollPane couponsScroll = new JScrollPane(txtCoupons);
        couponsScroll.setPreferredSize(new Dimension(200, 200)); // تكبير النص
        rightPanel.add(couponsScroll, BorderLayout.CENTER);

        // تكبير النص الخاص بالبانيل الجانبي
        JLabel couponsLabel = new JLabel("Coupons");
        couponsLabel.setFont(new Font("SansSerif", Font.BOLD, 24)); // تكبير الخط
        couponsLabel.setForeground(Color.WHITE); // اللون الأبيض
        rightPanel.add(couponsLabel, BorderLayout.NORTH);

        add(rightPanel, BorderLayout.EAST);
    }

    private void setupCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(new Color(10, 25, 47));

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(10, 25, 47));
        topPanel.setLayout(new FlowLayout());

        // تكبير النصوص في الحاوية العلوية
        lblClassName.setFont(new Font("SansSerif", Font.BOLD, 32)); // تكبير النص
        lblClassName.setForeground(Color.WHITE); // اللون الأبيض
        lblWelcome.setFont(new Font("SansSerif", Font.PLAIN, 18)); // تكبير النص
        lblWelcome.setForeground(Color.WHITE); // اللون الأبيض

        topPanel.add(lblClassName);
       // topPanel.add(lblWelcome);
        centerPanel.add(topPanel, BorderLayout.NORTH);

        // إنشاء حاوية للأزرار مع تغيير الألوان والأحجام
        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridBagLayout()); // استخدام GridBagLayout لتوزيع الأزرار بشكل أفضل
        buttonContainer.setBackground(new Color(10, 25, 47));

        // تكبير الأزرار
        btnMakeSale.setPreferredSize(new Dimension(250, 80));
        btnUpdateCustomer.setPreferredSize(new Dimension(250, 80));
        btnSearchReceipt.setPreferredSize(new Dimension(250, 80));
        btnLogout.setPreferredSize(new Dimension(250, 80));
        btnLogout.setBackground(new Color(235, 0, 0));

        // إضافة الأزرار إلى الحاوية
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonContainer.add(btnMakeSale, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonContainer.add(btnUpdateCustomer, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonContainer.add(btnSearchReceipt, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        buttonContainer.add(btnLogout, gbc);

        centerPanel.add(buttonContainer, BorderLayout.CENTER);
        centerPanel.add(new JLabel("Actions"), BorderLayout.SOUTH); // إضافة اسم للباني

        add(centerPanel, BorderLayout.CENTER);
    }

    private void styleNavigationButtons() {
        btnNextReceipt.setPreferredSize(new Dimension(80, 25)); // تصغير أزرار التنقل
        btnPreviousReceipt.setPreferredSize(new Dimension(80, 25)); // تصغير أزرار التنقل

        btnNextReceipt.setBackground(new Color(193, 143, 255));
        btnPreviousReceipt.setBackground(new Color(193, 143, 255));

        btnNextReceipt.setFocusPainted(false);
        btnPreviousReceipt.setFocusPainted(false);

        btnNextReceipt.setHorizontalAlignment(SwingConstants.LEFT);
        btnPreviousReceipt.setHorizontalAlignment(SwingConstants.LEFT);
    }

    private void displayReceipt() {
        try {
            List<String[]> receiptList = cashier.fetchSales();
            String[] currentReceipt = receiptList.get(currentReceiptIndex);
            txtReceipts.setText(String.format("Sale ID: %s%nCustomer ID: %s%nTotal: %s%nDate: %s",
                    currentReceipt[0], currentReceipt[1], currentReceipt[2], currentReceipt[3]));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading receipt: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadReceipts() {
        displayReceipt();
    }

    private void loadCoupons() {
        try {
            txtCoupons.setText("");
            List<String[]> couponList = cashier.fetchDiscounts();
            for (String[] coupon : couponList) {
                txtCoupons.append(String.format("Product ID: %s%nStart: %s%nEnd: %s%nDiscount: %s%%%n",
                        coupon[0], coupon[1], coupon[2], coupon[3]));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading coupons: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CashierHome frame = new CashierHome();
            frame.setVisible(true);
        });
    }
}