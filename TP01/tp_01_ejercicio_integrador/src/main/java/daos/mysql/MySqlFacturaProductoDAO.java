package daos.mysql;

import daos.interfaces.FacturaProductoDAO;
import entities.FacturaProducto;
import factories.MySqlConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Patrón Singleton
public class MySqlFacturaProductoDAO implements FacturaProductoDAO {
    private static MySqlFacturaProductoDAO unicaInstancia;
    private final Connection conn;

    private MySqlFacturaProductoDAO() throws SQLException {
        this.conn = MySqlConnectionFactory.getInstance().getConnection();
    }

    public static MySqlFacturaProductoDAO getInstance() throws SQLException {
        if (unicaInstancia == null) {
            unicaInstancia = new MySqlFacturaProductoDAO();
        }

        return unicaInstancia;
    }

    @Override
    public void dropTable() throws SQLException {
        try {
            String drop_table = "DROP TABLE IF EXISTS Factura_Producto";

            PreparedStatement ps = conn.prepareStatement(drop_table);
            ps.executeUpdate();
            ps.close();

            this.conn.commit();
        } catch (SQLException e) {
            throw new SQLException("Error al eliminar la tabla Factura_Producto.", e);
        }
    }

    @Override
    public void createTable() throws SQLException {
        try {
            String table = "CREATE TABLE IF NOT EXISTS Factura_Producto(" +
                    "idFactura INT," +
                    "idFactura INT," +
                    "cantidad INT," +
                    "PRIMARY KEY(idFactura, idProducto)," +
                    "FOREIGN KEY(idFactura) REFERENCES Factura(idFactura)," +
                    "FOREIGN KEY(idProducto) REFERENCES Producto(idProducto))";

            PreparedStatement ps = conn.prepareStatement(table);
            ps.executeUpdate();
            ps.close();

            this.conn.commit();
        } catch (SQLException e) {
            throw new SQLException("Error al crear la tabla Factura_Producto.", e);
        }
    }

    @Override
    public void insert(FacturaProducto fp) throws SQLException {
        if (existeFactura(fp.getIdFactura()) && existeProducto(fp.getIdProducto())) {
            try {
                String query = "INSERT INTO Factura_Producto(idFactura, idProducto, cantidad) VALUES (?, ?, ?)";

                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, fp.getIdFactura());
                ps.setInt(2, fp.getIdProducto());
                ps.setInt(3, fp.getCantidad());
                ps.executeUpdate();
                ps.close();

                conn.commit();
            } catch (SQLException e) {
                throw new SQLException("Error al insertar FacturaProducto!", e);
            }
        }
    }

    private boolean existeFactura(int idFactura) throws SQLException {
        boolean existe = false;

        try{
            String query = "SELECT * FROM Factura WHERE idFactura=?" ;

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, idFactura);
            ResultSet rs = ps.executeQuery();

            // Si hay al menos una fila en el ResultSet, la factura existe
            existe = rs.next();
        } catch (SQLException e) {
            throw new SQLException("Error, la Factura con id=" + idFactura + " no existe!", e);
        }

        return existe;
    }

    private boolean existeProducto(int idProducto) throws SQLException {
        boolean existe = false;

        try{
            String query = "SELECT * FROM Producto WHERE idProducto=?" ;

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();

            // Si hay al menos una fila en el ResultSet, el producto existe
            existe = rs.next();
        } catch (SQLException e) {
            throw new SQLException("Error, el Producto con id=" + idProducto + " no existe!", e);
        }

        return existe;
    }

    @Override
    public FacturaProducto select(int idFactura, int idProducto) throws SQLException {
        FacturaProducto fp = null;

        try {
            String query = "SELECT * FROM Factura_Producto WHERE idFactura=? AND idProducto=?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, idFactura);
            ps.setInt(2, idProducto);
            ResultSet rs = ps.executeQuery();

            fp = new FacturaProducto(rs.getInt(1), rs.getInt(2), rs.getInt(3));
        } catch (SQLException e) {
            throw new SQLException("Error al seleccionar FacturaProducto!");
        }

        return fp;
    }

    @Override
    public List<FacturaProducto> selectAll() throws SQLException {
        List<FacturaProducto> facturas_productos = new ArrayList<>();

        try {
            String query = "SELECT * FROM Factura_Producto";

            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                facturas_productos.add(new FacturaProducto(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener FacturasProductos!", e);
        }

        return facturas_productos;
    }

    @Override
    public boolean update() throws SQLException {
        return false;
    }

    @Override
    public boolean delete(int idFactura, int idProducto) throws SQLException {
        return false;
    }
}
