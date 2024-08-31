package factories;

import dao.MySqlPersonaDAO;
import dao.PersonaDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDAOFactory extends DAOFactory {
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DBURL = "jdbc:mysql://localhost:3306/example_db";

    public static Connection createConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(DBURL, "root", "password");
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PersonaDAO getPersonaDAO() {
        return new MySqlPersonaDAO(createConnection());
    }
}
