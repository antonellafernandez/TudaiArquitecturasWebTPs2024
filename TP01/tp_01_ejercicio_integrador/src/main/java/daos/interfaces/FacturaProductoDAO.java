package daos.interfaces;

import entities.Cliente;
import entities.FacturaProducto;

import java.sql.SQLException;
import java.util.List;

public interface FacturaProductoDAO {
    void dropTable() throws SQLException;

    void createTable() throws SQLException;

    void insert(FacturaProducto fp) throws SQLException;

    FacturaProducto select(int IdFactura, int IdProducto) throws SQLException;
    List<FacturaProducto> selectAll() throws SQLException;

    boolean update() throws SQLException;

    boolean delete(int IdFactura, int IdProducto) throws SQLException;
}
