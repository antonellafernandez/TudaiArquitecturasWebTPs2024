package dao;

import entities.Direccion;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.util.List;

public class JpaDireccionDAO implements DireccionDAO {
    private EntityManager em;

    public JpaDireccionDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public void addDireccion(Direccion direccion) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            em.persist(direccion);
            transaction.commit();
        } catch (PersistenceException e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public Direccion getDireccionById(int id) {
        return em.find(Direccion.class, id);
    }

    @Override
    public List<Direccion> getAllDirecciones() {
        return em.createQuery("SELECT d FROM Direccion d", Direccion.class).getResultList();
    }
}
