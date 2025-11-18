package app;

import java.sql.*;

public class Database {
private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/ngo_app?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
private static final String MYSQL_USER = "ngoapp";
private static final String MYSQL_PASS = "ngo123";



    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASS);
        }
        return connection;
    }
}

