package factories;

import dao.DireccionDAO;
import dao.PersonaDAO;

// Combinación de AbstractFactory y FactoryMethod
// Permite gestionar distintos tipos de persistencia
// Hay que definir la inicialización de cada una de las bases de datos

public abstract class DAOFactory {
    public static final int MYSQL_JDBC = 1;
    public static final int DERBY_JDBC = 2;

    public abstract DireccionDAO getDireccionDAO();
    public abstract PersonaDAO getPersonaDAO();

    public static DAOFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case MYSQL_JDBC :
                return new JpaMySqlDAOFactory();
            case DERBY_JDBC:
                return new JpaDerbyDAOFactory();
            default:
                return null;
        }
    }
}
