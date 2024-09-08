package daos.mysql;

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
public class ProductoDAO implements daos.interfaces.ProductoDAO {
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
        try {
            String drop_table = "DROP TABLE IF EXISTS Producto";

            PreparedStatement ps = conn.prepareStatement(drop_table);
            ps.executeUpdate();
            ps.close();

            this.conn.commit();
        } catch (SQLException e) {
            conn.rollback(); // Rollback en caso de error
            throw new SQLException("Error al eliminar la tabla Producto.", e);
        }
    }

    @Override
    public void createTable() throws SQLException {
        try {
            String table = "CREATE TABLE IF NOT EXISTS Producto(" +
                    "idProducto INT," +
                    "nombre VARCHAR(45)," +
                    "valor FLOAT," +
                    "PRIMARY KEY(idProducto))";

            PreparedStatement ps = conn.prepareStatement(table);
            ps.executeUpdate();
            ps.close();

            this.conn.commit();
        } catch (SQLException e) {
            conn.rollback(); // Rollback en caso de error
            throw new SQLException("Error al crear la tabla Producto.", e);
        }
    }

    @Override
    public void insert (Producto p) throws SQLException {
        try {
            String query = "INSERT INTO Producto(idProducto, nombre, valor) VALUES (?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, p.getIdProducto());
            ps.setString(2, p.getNombre());
            ps.setFloat(3, p.getValor());
            ps.executeUpdate();
            ps.close();

            conn.commit();
        } catch (SQLException e) {
            conn.rollback(); // Rollback en caso de error
            throw new SQLException("Error al insertar Producto!", e);
        }
    }

    @Override
    public Producto select (Integer id) throws SQLException {
        Producto p = null;

        try {
            String query = "SELECT * FROM Producto WHERE idProducto=?";

            PreparedStatement ps = conn.prepareStatement(query);
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

        try {
            String query = "SELECT * FROM Producto";

            PreparedStatement ps = conn.prepareStatement(query);
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
    public boolean update () {
        return false;
    }

    @Override
    public boolean delete (Integer id) throws SQLException {
        return false;
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

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente.
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
