package daos.mysql;

import daos.interfaces.DAO;
import dtos.ProductoMayorRecaudacionDTO;
import entities.Producto;
import factories.MySqlConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Patrón Singleton
public class ProductoDAO implements DAO<Producto, Integer> {
    private static ProductoDAO unicaInstancia;
    private final Connection conn;

    private ProductoDAO() throws SQLException {
        this.conn = MySqlConnectionFactory.getInstance().getConnection();
    }

    public static ProductoDAO getInstance() throws SQLException {
        if (unicaInstancia == null) {
            unicaInstancia = new ProductoDAO();
        }

        return unicaInstancia;
    }

    @Override
    public void dropTable() throws SQLException {
        String drop_table = "DROP TABLE IF EXISTS Producto";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(drop_table)) {
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback(); // Rollback en caso de error
            throw new SQLException("Error al eliminar la tabla Producto.", e);
        }
    }

    @Override
    public void createTable() throws SQLException {
        String table = "CREATE TABLE IF NOT EXISTS Producto(" +
                "idProducto INT," +
                "nombre VARCHAR(45)," +
                "valor FLOAT," +
                "PRIMARY KEY(idProducto))";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(table)) {
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback(); // Rollback en caso de error
            throw new SQLException("Error al crear la tabla Producto.", e);
        }
    }

    @Override
    public void insert (Producto p) throws SQLException {
        String query = "INSERT INTO Producto(idProducto, nombre, valor) VALUES (?, ?, ?)";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, p.getIdProducto());
            ps.setString(2, p.getNombre());
            ps.setFloat(3, p.getValor());
            ps.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            conn.rollback(); // Rollback en caso de error
            throw new SQLException("Error al insertar Producto!", e);
        }
    }

    @Override
    public Producto select (Integer id) throws SQLException {
        Producto p = null;
        String query = "SELECT * FROM Producto WHERE idProducto=?";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id.intValue());
            ResultSet rs = ps.executeQuery();

            p = new Producto(rs.getInt(1), rs.getString(2), rs.getFloat(3));
        } catch (SQLException e) {
            throw new SQLException("Error al seleccionar Producto con id=" + id + "!", e);
        }

        return p;
    }

    @Override
    public List<Producto> selectAll () throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT * FROM Producto";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                productos.add(new Producto(rs.getInt(1), rs.getString(2), rs.getFloat(3)));
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener Productos!", e);
        }

        return productos;
    }

    @Override
    public boolean update(Producto p) throws SQLException {
        String query = "UPDATE Producto SET nombre = ?, valor = ? WHERE idProducto = ?";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getValor());
            ps.setInt(3, p.getIdProducto());

            int affectedRows = ps.executeUpdate(); // Devuelve el número de filas afectadas

            conn.commit();
            return affectedRows > 0; // Retorna true si se actualizó al menos una fila
        } catch (SQLException e) {
            conn.rollback(); // Rollback en caso de error
            throw new SQLException("Error al actualizar Producto!", e);
        }
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        String query = "DELETE FROM Producto WHERE idProducto = ?";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);

            int affectedRows = ps.executeUpdate(); // Devuelve el número de filas afectadas

            conn.commit();
            return affectedRows > 0; // Retorna true si se eliminó al menos una fila
        } catch (SQLException e) {
            conn.rollback(); // Rollback en caso de error
            throw new SQLException("Error al eliminar Producto con id=" + id, e);
        }
    }

    public ProductoMayorRecaudacionDTO obtenerProductoMayorRecaudacion () throws SQLException {
        ProductoMayorRecaudacionDTO productoMayorRecaudacion = null;

        String query = "SELECT p.idProducto, p.nombre, p.valor, "
                + "SUM(fp.cantidad * p.valor) AS recaudacion "
                + "FROM Producto p "
                + "JOIN Factura_Producto fp ON p.idProducto = fp.idProducto "
                + "GROUP BY p.idProducto "
                + "ORDER BY recaudacion DESC "
                + "LIMIT 1";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                productoMayorRecaudacion = new ProductoMayorRecaudacionDTO(rs.getInt("idProducto"),
                        rs.getString("nombre"),
                        rs.getFloat("valor"),
                        rs.getFloat("recaudacion"));
            }
        } catch(SQLException e){
            throw new SQLException("Error al obtener Producto con mayor recaudación!", e);
        }

        return productoMayorRecaudacion;
    }
}
