package factories;

import daos.mysql.MySqlClienteDAO;
import daos.mysql.MySqlFacturaDAO;
import daos.mysql.MySqlFacturaProductoDAO;
import daos.mysql.MySqlProductoDAO;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DatabaseFactory {
    public static final int MYSQL_JDBC = 1;

    public abstract Connection getConnection() throws SQLException;
    public abstract void closeConnection() throws SQLException;

    public abstract MySqlClienteDAO getClienteDAO() throws SQLException;
    public abstract MySqlFacturaDAO getFacturaDAO() throws SQLException;
    public abstract MySqlFacturaProductoDAO getFacturaProductoDAO() throws SQLException;
    public abstract MySqlProductoDAO getProductoDAO() throws SQLException;

    public static DatabaseFactory getDAOFactory(int whichFactory) throws SQLException {
        switch(whichFactory) {
            case MYSQL_JDBC:
                return MySqlConnectionFactory.getInstance();
            default:
                return null;
        }
    }
}
