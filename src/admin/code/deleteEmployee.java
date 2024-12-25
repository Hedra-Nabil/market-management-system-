package admin.code;

import java.sql.*;

public class deleteEmployee {


    protected static final String DATABASE_URL = "jdbc:sqlite:new_file";


    public ResultSet searchEmployeeByName(String name) throws SQLException {
        String query = "SELECT * FROM Employees WHERE UPPER(TRIM(Name)) LIKE UPPER(TRIM(?))";
        Connection conn = DriverManager.getConnection(DATABASE_URL);
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, "%" + name + "%");
        return statement.executeQuery();

    }


    public boolean deleteEmployeeById(int employeeId) throws SQLException {
        String query = "DELETE FROM Employees WHERE EmployeeID = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, employeeId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
