package daos.mysql;

import daos.interfaces.ClienteDAO;
import dtos.ClienteConFacturacionDTO;
import entities.Cliente;
import factories.MySqlConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Patrón Singleton
public class MySqlClienteDAO implements ClienteDAO {
    private static MySqlClienteDAO unicaInstancia;
    private final Connection conn;

    private MySqlClienteDAO() throws SQLException {
        this.conn = MySqlConnectionFactory.getInstance().getConnection();
    }

    public static MySqlClienteDAO getInstance() throws SQLException {
        if (unicaInstancia == null) {
            unicaInstancia = new MySqlClienteDAO();
        }

        return unicaInstancia;
    }

    @Override
    public void dropTable() throws SQLException {
        try {
            String drop_table = "DROP TABLE IF EXISTS Cliente";

            PreparedStatement ps = conn.prepareStatement(drop_table);
            ps.executeUpdate();
            ps.close();

            this.conn.commit();
        } catch (SQLException e) {
            throw new SQLException("Error al eliminar la tabla Cliente.", e);
        }
    }

    @Override
    public void createTable() throws SQLException {
        try {
            String table = "CREATE TABLE IF NOT EXISTS Cliente(" +
                    "idCliente INT," +
                    "nombre VARCHAR(500)," +
                    "email VARCHAR(150)," +
                    "PRIMARY KEY(idCliente))";

            PreparedStatement ps = conn.prepareStatement(table);
            ps.executeUpdate();
            ps.close();

            this.conn.commit();
        } catch (SQLException e) {
            throw new SQLException("Error al crear la tabla Cliente.", e);
        }
    }

    @Override
    public void insert (Cliente c) throws SQLException {
        try {
            String query = "INSERT INTO Cliente(idCliente, nombre, email) VALUES (?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, c.getIdCliente());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getEmail());
            ps.executeUpdate();
            ps.close();

            conn.commit();
        } catch (SQLException e) {
            throw new SQLException("Error al insertar Cliente!", e);
        }
    }

    @Override
    public Cliente select (Integer id) throws SQLException {
        Cliente c = null;

        try {
            String query = "SELECT * FROM Cliente WHERE idCliente=?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id.intValue());
            ResultSet rs = ps.executeQuery();

            c = new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3));
        } catch (SQLException e) {
            throw new SQLException("Error al seleccionar Cliente con id=" + id + "!", e);
        }

        return c;
    }

    @Override
    public List<Cliente> selectAll () throws SQLException {
        List<Cliente> clientes = new ArrayList<>();

        try {
            String query = "SELECT * FROM Cliente";

            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                clientes.add(new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener Clientes!", e);
        }

        return clientes;
    }

    @Override
    public boolean update () {
        return false;
    }

    @Override
    public boolean delete (Integer id) throws SQLException {
        return false;
    }

    public List<ClienteConFacturacionDTO> obtenerClientesPorMayorFacturacionDesc () throws SQLException {
        List<ClienteConFacturacionDTO> clientesFacturadosDesc = new ArrayList<>();

        String query = "SELECT c.idCliente, c.nombre, c.email, SUM(fp.cantidad) AS cantidad, SUM(p.valor * fp.cantidad) AS totalFacturado "
                + "FROM Cliente c "
                + "JOIN Factura f USING (idCliente) "
                + "JOIN Factura_Producto fp USING (idFactura) "
                + "JOIN Producto p USING (idProducto) "
                + "GROUP BY c.idCliente "
                + "ORDER BY totalFacturado DESC";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente.
        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                clientesFacturadosDesc.add(new ClienteConFacturacionDTO(rs.getInt("idCliente"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getFloat("totalFacturado")));
            }
        } catch(SQLException e){
            throw new SQLException("Error al obtener Clientes por facturación desc!", e);
        }

        return clientesFacturadosDesc;
    }
}
