package factories;

import daos.mysql.MySqlClienteDAO;
import daos.mysql.MySqlFacturaDAO;
import daos.mysql.MySqlFacturaProductoDAO;
import daos.mysql.MySqlProductoDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Patrón Singleton
public class MySqlConnectionFactory extends DatabaseFactory {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URI = "jdbc:mysql://localhost:3306/mysql_db_integrador";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "password";

    private static MySqlConnectionFactory unicaInstancia = null;
    private static Connection conn;

    private MySqlConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static MySqlConnectionFactory getInstance() throws SQLException {
        if (unicaInstancia == null) {
            unicaInstancia = new MySqlConnectionFactory();
        }

        return unicaInstancia;
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(DB_URI, DB_USER, DB_PASSWORD);
                conn.setAutoCommit(false);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return conn;
    }

    @Override
    public void closeConnection() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public MySqlClienteDAO getClienteDAO() throws SQLException {
        return MySqlClienteDAO.getInstance();
    }

    @Override
    public MySqlFacturaDAO getFacturaDAO() throws SQLException {
        return MySqlFacturaDAO.getInstance();
    }

    @Override
    public MySqlFacturaProductoDAO getFacturaProductoDAO() throws SQLException {
        return MySqlFacturaProductoDAO.getInstance();
    }

    @Override
    public MySqlProductoDAO getProductoDAO() throws SQLException {
        return MySqlProductoDAO.getInstance();
    }
}
