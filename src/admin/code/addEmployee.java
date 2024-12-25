package admin.code;
import ImplementationDB.SQL;
import wearhouse.code.connection;

import java.sql.*;

import static admin.code.Employee.DATABASE_URL;

public class addEmployee {






        protected static final String DATABASE_URL = "jdbc:sqlite:new_file";

        public boolean addEmp(String name, String position, String salary, String hireDate, String phone) throws SQLException {
            try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {

                if (employeeExists(conn, name)) {
                    return false;
                }

                String insertQuery = "INSERT INTO Employees (Name, Position, salary, HireDate, phone) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, name);
                    insertStmt.setString(2, position);
                    insertStmt.setString(3, salary);
                    insertStmt.setString(4, hireDate);
                    insertStmt.setString(5, phone);
                    insertStmt.executeUpdate();
                }
                return true;


            }finally {
                Connection conn = DriverManager.getConnection(DATABASE_URL);
                if (conn != null) {
                    conn.close();
                    System.out.println("Connection closed successfully.");
                }
            }



        }


        private boolean employeeExists(Connection conn, String name) throws SQLException {
            String checkQuery = "SELECT COUNT(*) FROM employees WHERE name = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, name);
                ResultSet rs = checkStmt.executeQuery();
                return rs.next() && rs.getInt(1) > 0;
            }
            finally {
                if (conn != null) {
                    conn.close();
                    System.out.println("Connection closed successfully.");
                }
        }}}
