// Combinación de AbstractFactory y FactoryMethod
// Permite gestionar distintos tipos de persistencia
// Hay que definir la inicialización de cada una de las bases de datos

public abstract class DAOFactory {
    public static final int MYSQL_JDBC = 1;
    public static final int DERBY_JDBC = 2;

    public abstract PersonaDAO getPersonaDAO();

    public static DAOFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case MYSQL_JDBC :
                return new MySqlJDBCDaoFactory();
            case DERBY_JDBC:
                return new DerbyJDBCDaoFactory();
            default:
                return null;
        }
    }
}
