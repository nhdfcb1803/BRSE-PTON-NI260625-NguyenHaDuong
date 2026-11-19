package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDatabase {
    private static final String URL = "jdbc:mysql://localhost:3306/db_library_management";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    // Phương thức kết nối
    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database successfully!");
            return conn;
        } catch (SQLException e) {
            System.out.println("Connect database failed!");
            e.printStackTrace();
            return null;
        }
    }
}
