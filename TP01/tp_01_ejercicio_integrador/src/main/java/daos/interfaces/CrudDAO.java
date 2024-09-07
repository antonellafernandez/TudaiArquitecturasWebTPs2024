package daos.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO <T, K> {
    void dropTable() throws SQLException;

    void createTable() throws SQLException;

    void insert(T t) throws SQLException;

    T select(K k) throws SQLException;
    List<T> selectAll() throws SQLException;

    boolean update() throws SQLException;

    boolean delete(K k) throws SQLException;
}
