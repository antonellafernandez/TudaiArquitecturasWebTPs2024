package dao.interfaces;

import entities.Direccion;

import java.util.List;

// Creating the table should be handled by JPA/Hibernate schema generation
public interface DAO<T> {
    void insert(T t);

    T selectById(int id);

    List<T> selectAll();

    boolean update(T t);

    boolean delete(int id);
}
