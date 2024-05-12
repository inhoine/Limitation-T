package automation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection connectToDatabase() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            String dbURL = "jdbc:postgresql://db-ca.mediastep.com:5432/postgres";
            String username = "postgres";
            String password = "postgres";
            conn = DriverManager.getConnection(dbURL, username, password);
            if (conn != null) {
                System.out.println("");
                System.out.println("a. Kết nối tới cơ sở dữ liệu DBeaver thành công!");
            } else {
                System.out.println("a. Không thể kết nối tới cơ sở dữ liệu DBeaver!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("a. Kết nối tới cơ sở dữ liệu DBeaver thất bại! Lỗi: " + e.getMessage());
        }
        return conn;
    }
}
