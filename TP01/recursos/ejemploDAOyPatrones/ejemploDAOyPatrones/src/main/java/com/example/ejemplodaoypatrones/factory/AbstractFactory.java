package com.example.ejemplodaoypatrones.factory;

import com.example.ejemplodaoypatrones.dao.DireccionDAO;
import com.example.ejemplodaoypatrones.dao.PersonaDAO;

public abstract class AbstractFactory {
    public static final int MYSQL_JDBC = 1;
    public static final int DERBY_JDBC = 2;
    public abstract PersonaDAO getPersonaDAO();
    public abstract DireccionDAO getDireccionDAO();
    public static AbstractFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case MYSQL_JDBC : {
                return MySQLDAOFactory.getInstance();
            }
            case DERBY_JDBC: return null;
            default: return null;
        }
    }

}
