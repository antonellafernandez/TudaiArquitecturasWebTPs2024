package factories;

import dao.DireccionDAO;
import dao.JpaDireccionDAO;
import dao.JpaPersonaDAO;
import dao.PersonaDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaMySqlDAOFactory extends DAOFactory {
    private static final String PERSISTENCE_UNIT_NAME = "Example";

    @Override
    public DireccionDAO getDireccionDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
        return new JpaDireccionDAO(em);
    }

    @Override
    public PersonaDAO getPersonaDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
        return new JpaPersonaDAO(em);
    }
}
