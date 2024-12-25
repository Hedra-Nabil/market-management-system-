package casher.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Customer {
    private String id;
    private String name;
    private String mobile;
    private String address;

    public Customer(String id, String name, String mobile, String address) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.address = address;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Delete Function
    public boolean deleteCustomerById(String customerId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:new_file");

            String sql = "DELETE FROM customers WHERE CustomerID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, customerId);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0; // Return true if a row was deleted

        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
