package dao;

import dao.interfaces.DAO;
import entities.Direccion;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.util.List;

public class JpaDireccionDAO implements DAO<Direccion> {
    private EntityManager em;

    public JpaDireccionDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public void insert(Direccion direccion) {
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
    public Direccion selectById(int id) {
        return em.find(Direccion.class, id);
    }

    @Override
    public List<Direccion> selectAll() {
        return em.createQuery("SELECT d FROM Direccion d", Direccion.class).getResultList();
    }

    @Override
    public boolean update(Direccion direccion) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
