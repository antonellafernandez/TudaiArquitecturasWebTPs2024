package factories;

import daos.ClienteDAO;
import daos.FacturaDAO;
import daos.FacturaProductoDAO;
import daos.ProductoDAO;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DatabaseFactory {
    public static final int MYSQL_JDBC = 1;

    public abstract Connection getConnection() throws SQLException;
    public abstract void closeConnection() throws SQLException;

    public abstract ClienteDAO getClienteDAO() throws SQLException;
    public abstract FacturaDAO getFacturaDAO() throws SQLException;
    public abstract FacturaProductoDAO getFacturaProductoDAO() throws SQLException;
    public abstract ProductoDAO getProductoDAO() throws SQLException;

    public static DatabaseFactory getDAOFactory(int whichFactory) throws SQLException {
        switch(whichFactory) {
            case MYSQL_JDBC:
                return MySqlConnectionFactory.getInstance();
            default:
                return null;
        }
    }
}
