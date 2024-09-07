package factories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnectionFactory {
    public static final String DB_URI = "jdbc:mysql://localhost:3306/mysql_db";
    public static final String DB_USER = "user";
    public static final String DB_PASSWORD = "password";
    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        if (conn == null){
            conn = DriverManager.getConnection(DB_URI, DB_USER, DB_PASSWORD);
            conn.setAutoCommit(false);
        }

        return conn;
    }

    public static void closeConnection() throws SQLException {
        conn.close();
    }
}
