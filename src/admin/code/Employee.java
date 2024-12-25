package admin.code;

import java.sql.*;



public class Employee extends deleteEmployee {


    public ResultSet getPositions() throws SQLException {
        String query = "SELECT DISTINCT Position FROM Employees";
        Connection conn = DriverManager.getConnection(DATABASE_URL);
        PreparedStatement statement = conn.prepareStatement(query);
        return statement.executeQuery();
    }

    public ResultSet searchUsersWithAccounts(String name) throws SQLException {
        String query;
        if (name.isEmpty()) {

            query = "SELECT e.EmployeeID, e.Name, e.Position, e.Salary, e.HireDate, e.Phone " +
                    "FROM Employees e " +
                    "JOIN UserAccounts u ON e.EmployeeID = u.EmployeeID";
        } else {

            query = "SELECT e.EmployeeID, e.Name, e.Position, e.Salary, e.HireDate, e.Phone " +
                    "FROM Employees e " +
                    "JOIN UserAccounts u ON e.EmployeeID = u.EmployeeID " +
                    "WHERE e.Name LIKE ?";
        }

        Connection conn = DriverManager.getConnection(DATABASE_URL);
        PreparedStatement statement = conn.prepareStatement(query);
        if (!name.isEmpty()) {
            statement.setString(1, "%" + name + "%");
        }
        return statement.executeQuery();
    }


    public ResultSet searchEmployeeByNameAndPosition(String name, String position) throws SQLException {
        String query = "SELECT * FROM Employees WHERE UPPER(TRIM(Name)) LIKE UPPER(TRIM(?))";
        if (position != null && !position.isEmpty()) {
            query += " AND Position = ?";
        }
        Connection conn = DriverManager.getConnection(DATABASE_URL);
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, "%" + name + "%");
        if (position != null && !position.isEmpty()) {
            statement.setString(2, position);
        }
        return statement.executeQuery();
    }
    public ResultSet searchEmployeeByName(String name) throws SQLException {
        String query = "SELECT * FROM Employees WHERE UPPER(TRIM(Name)) LIKE UPPER(TRIM(?))";

        Connection conn = DriverManager.getConnection(DATABASE_URL);
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, "%" + name + "%");

        return statement.executeQuery();
    }


    public boolean createUser(String username, String password, String role, int employeeID) throws SQLException {
        int roleID = getRoleID(role);
        String query = "INSERT INTO UserAccounts (Username, Password, Role, EmployeeID) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, roleID);
            statement.setInt(4, employeeID);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean deleteUserAccount(int employeeID) throws SQLException {
        String query = "DELETE FROM UserAccounts WHERE EmployeeID = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, employeeID);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    private int getRoleID(String role) {
        switch (role) {
            case "Manager":
                return 3;
            case "Warehouse":
                return 2;
            case "Cashier":
                return 1;
            default:
                return 0;
        }
    }
}
