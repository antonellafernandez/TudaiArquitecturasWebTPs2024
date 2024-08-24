import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DerbyJDBCDaoFactory extends DAOFactory {
    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String DBURL = "jdbc:derby:MyDerbyDb;create=true";

    public static Connection createConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(DBURL);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PersonaDAO getPersonaDAO() {
        return new DerbyPersonaDAO(createConnection());
    }
}
