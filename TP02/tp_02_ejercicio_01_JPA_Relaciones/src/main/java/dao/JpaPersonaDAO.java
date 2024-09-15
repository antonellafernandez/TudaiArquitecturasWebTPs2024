package dao;

import dao.interfaces.DAO;
import entities.Direccion;
import entities.Persona;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.util.List;

public class JpaPersonaDAO implements DAO<Persona> {
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
    public void insert(Persona persona) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            em.persist(persona);
            transaction.commit();
        } catch (PersistenceException e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public Persona selectById(int id) {
        return em.find(Persona.class, id);
    }

    @Override
    public List<Persona> selectAll() {
        return em.createQuery("SELECT p FROM Persona p", Persona.class).getResultList();
    }

    @Override
    public boolean update(Persona persona) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            Persona existingPersona = em.find(Persona.class, persona.getId());

            if (existingPersona != null) {
                // Actualizar los campos simples
                existingPersona.setNombre(persona.getNombre());
                existingPersona.setEdad(persona.getEdad());

                // Actualizar la relación ManyToOne (domicilio)
                if (persona.getDomicilio() != null) {
                    Direccion newDomicilio = em.find(Direccion.class, persona.getDomicilio().getId());
                    existingPersona.setDomicilio(newDomicilio); // Actualizar si cambia el domicilio
                }

                // Guardar los cambios
                em.merge(existingPersona);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false; // Si no se encuentra la persona
            }
        } catch (PersistenceException e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public boolean delete(int id) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            Persona persona = em.find(Persona.class, id);

            if (persona != null) {
                em.remove(persona);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        } catch (PersistenceException e) {
            transaction.rollback();
            throw e;
        }
    }
}
