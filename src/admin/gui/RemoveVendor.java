package admin.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RemoveVendor extends JFrame {
    private JTable vendorTable;
    private DefaultTableModel tableModel;
    private JButton deleteButton;
    private int selectedVendorID = 0;

    public RemoveVendor() {
        setTitle("Remove Vendor");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(0, 0, 50));

        String[] columns = {"VendorID", "Name", "Address", "Phone","Email"};
        tableModel = new DefaultTableModel(columns, 0);
        vendorTable = new JTable(tableModel);
        vendorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        styleTable(vendorTable);

        JScrollPane scrollPane = new JScrollPane(vendorTable);
        scrollPane.setPreferredSize(new Dimension(750, 400));

        deleteButton = new JButton("Delete Selected Vendor");
        deleteButton.setBackground(new Color(220, 20, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setEnabled(false);

        // الحدث عند اختيار صف من الجدول
        vendorTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = vendorTable.getSelectedRow();
            if (selectedRow != -1) {
                selectedVendorID = (int) tableModel.getValueAt(selectedRow, 0); // تخزين ID المورد
                deleteButton.setEnabled(true);
            }
        });


        deleteButton.addActionListener(e -> deleteVendor());


        JPanel panel = new JPanel();
        panel.add(deleteButton);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        loadVendors();
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(65, 105, 225));
        table.getTableHeader().setForeground(Color.WHITE);
    }

    private void loadVendors() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:new_file")) {
            String query = "SELECT SupplierID, Name, Address, Phone,Email FROM Suppliers";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            tableModel.setRowCount(0);
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("SupplierID"),
                        rs.getString("Name"),
                        rs.getString("Address"),
                        rs.getString("Phone"),
                        rs.getString("Email")
                });
            }
        } catch (SQLException ex) {
            showError("Error loading vendors: " + ex.getMessage());
        }
    }

    private void deleteVendor() {
        if (selectedVendorID == 0) {
            showError("No vendor selected!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this vendor?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:new_file")) {
                String query = "DELETE FROM Suppliers WHERE SupplierID = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, selectedVendorID);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    showInfo("Vendor deleted successfully!");
                    loadVendors();
                    deleteButton.setEnabled(false);
                } else {
                    showError("Failed to delete vendor.");
                }
            } catch (SQLException ex) {
                showError("Error deleting vendor: " + ex.getMessage());
            }
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RemoveVendor().setVisible(true));
    }
}
