package chessgame.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServiceConnector {
    private static final String URL = "jdbc:mysql://localhost:13306/chess";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    public static Connection getConnection() {
        loadDriver();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스에 접근이 불가능합니다.");
        }
        return connection;
    }

    private static void loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (final Exception e) {
            throw new RuntimeException("데이터베이스에 접근이 불가능합니다.");
        }
    }

}
