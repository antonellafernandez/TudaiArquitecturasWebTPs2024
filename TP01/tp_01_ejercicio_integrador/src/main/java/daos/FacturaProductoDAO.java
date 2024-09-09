package daos;

import daos.interfaces.DAO;
import entities.Factura;
import entities.FacturaProducto;
import factories.MySqlConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Patrón Singleton
public class FacturaProductoDAO implements DAO<FacturaProducto> {
    private static FacturaProductoDAO unicaInstancia;

    private FacturaProductoDAO() throws SQLException {

    }

    public static FacturaProductoDAO getInstance() throws SQLException {
        if (unicaInstancia == null) {
            unicaInstancia = new FacturaProductoDAO();
        }

        return unicaInstancia;
    }

    @Override
    public void dropTable() throws SQLException {
        Connection conn = MySqlConnectionFactory.getInstance().getConnection();

        String drop_table = "DROP TABLE IF EXISTS Factura_Producto";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(drop_table)) {
            ps.executeUpdate();

            conn.commit();
            conn.close();
        } catch (SQLException e) {
            conn.rollback(); // Rollback en caso de error
            throw new SQLException("Error al eliminar la tabla Factura_Producto.", e);
        }
    }

    @Override
    public void createTable() throws SQLException {
        Connection conn = MySqlConnectionFactory.getInstance().getConnection();

        String table = "CREATE TABLE IF NOT EXISTS Factura_Producto(" +
                "idFactura INT," +
                "idProducto INT," +
                "cantidad INT," +
                "PRIMARY KEY(idFactura, idProducto)," +
                "FOREIGN KEY(idFactura) REFERENCES Factura(idFactura)," +
                "FOREIGN KEY(idProducto) REFERENCES Producto(idProducto))";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(table);) {
            ps.executeUpdate();

            conn.commit();
            conn.close();
        } catch (SQLException e) {
            conn.rollback(); // Rollback en caso de error
            throw new SQLException("Error al crear la tabla Factura_Producto.", e);
        }
    }

    @Override
    public void insert(FacturaProducto fp) throws SQLException {
        Connection conn = MySqlConnectionFactory.getInstance().getConnection();

        String query = "INSERT INTO Factura_Producto(idFactura, idProducto, cantidad) VALUES (?, ?, ?)";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, fp.getIdFactura());
            ps.setInt(2, fp.getIdProducto());
            ps.setInt(3, fp.getCantidad());
            ps.executeUpdate();

            conn.commit();
            conn.close();
        } catch (SQLException e) {
            conn.rollback();
            throw new SQLException("Error al insertar FacturaProducto!", e);
        }
    }

    @Override
    public FacturaProducto select (int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public FacturaProducto select(int idFactura, int idProducto) throws SQLException {
        Connection conn = MySqlConnectionFactory.getInstance().getConnection();

        FacturaProducto fp = null;
        String query = "SELECT * FROM Factura_Producto WHERE idFactura=? AND idProducto=?";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            ps.setInt(1, idFactura);
            ps.setInt(2, idProducto);

            fp = new FacturaProducto(rs.getInt(1), rs.getInt(2), rs.getInt(3));

            conn.close();
        } catch (SQLException e) {
            throw new SQLException("Error al seleccionar FacturaProducto!");
        }

        return fp;
    }

    @Override
    public List<FacturaProducto> selectAll() throws SQLException {
        Connection conn = MySqlConnectionFactory.getInstance().getConnection();

        List<FacturaProducto> facturas_productos = new ArrayList<>();
        String query = "SELECT * FROM Factura_Producto";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente.
        try (PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                facturas_productos.add(new FacturaProducto(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
            }

            conn.close();
        } catch (SQLException e) {
            throw new SQLException("Error al obtener FacturasProductos!", e);
        }

        return facturas_productos;
    }

    @Override
    public boolean update(FacturaProducto fp) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
