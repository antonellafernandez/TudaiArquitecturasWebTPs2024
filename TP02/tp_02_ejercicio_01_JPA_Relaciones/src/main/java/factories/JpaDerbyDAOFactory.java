package factories;

import dao.interfaces.DAO;
import dao.JpaDireccionDAO;
import dao.JpaPersonaDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaDerbyDAOFactory extends DAOFactory {
    private static final String PERSISTENCE_UNIT_NAME = "Example";

    @Override
    public DAO getDireccionDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
        return new JpaDireccionDAO(em);
    }

    @Override
    public DAO getPersonaDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
        return new JpaPersonaDAO(em);
    }
}
