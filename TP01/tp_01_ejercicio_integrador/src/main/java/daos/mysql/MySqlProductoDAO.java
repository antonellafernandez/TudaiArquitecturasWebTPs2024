package daos.mysql;

import daos.interfaces.ProductoDAO;
import entities.Producto;
import factories.MySqlConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Patrón Singleton
public class MySqlProductoDAO implements ProductoDAO {
    private static MySqlProductoDAO unicaInstancia;
    private final Connection conn;

    private MySqlProductoDAO() throws SQLException {
        this.conn = MySqlConnectionFactory.getInstance().getConnection();
    }

    public static MySqlProductoDAO getInstance() throws SQLException {
        if (unicaInstancia == null) {
            unicaInstancia = new MySqlProductoDAO();
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
            throw new SQLException("Error al crear la tabla Producto.", e);
        }
    }

    @Override
    public void insert (Producto p) throws SQLException {
        try {
            String query = "INSERT INTO Producto(nombre, valor) VALUES (?, ?)";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, p.getNombre());
            ps.setFloat(2, p.getValor());
            ps.executeUpdate();
            ps.close();

            conn.commit();
        } catch (SQLException e) {
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
}
