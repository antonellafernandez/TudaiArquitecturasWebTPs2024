package dao;

import entities.Persona;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class JpaPersonaDAO implements PersonaDAO {
    private EntityManager em;

    public JpaPersonaDAO(EntityManager em) {
        this.em = em;
        initialize();
    }

    private void initialize() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            // Creating the table should be handled by JPA/Hibernate schema generation
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void addPersona(Persona persona) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            em.persist(persona);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Persona> getAllPersonas() {
        return em.createQuery("SELECT p FROM Persona p", Persona.class).getResultList();
    }
}
