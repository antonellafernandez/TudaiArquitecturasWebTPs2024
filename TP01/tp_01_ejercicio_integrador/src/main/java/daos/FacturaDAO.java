package daos;

import daos.interfaces.DAO;
import dtos.ClienteConFacturacionDTO;
import entities.Factura;
import factories.MySqlConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Patrón Singleton
public class FacturaDAO implements DAO<Factura> {
    private static FacturaDAO unicaInstancia;

    private FacturaDAO() throws SQLException {

    }

    public static FacturaDAO getInstance() throws SQLException {
        if (unicaInstancia == null) {
            unicaInstancia = new FacturaDAO();
        }

        return unicaInstancia;
    }

    @Override
    public void dropTable() throws SQLException {
        Connection conn = MySqlConnectionFactory.getInstance().getConnection();

        String drop_table = "DROP TABLE IF EXISTS Factura";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(drop_table)) {
            ps.executeUpdate();

            conn.commit();
            conn.close();
        } catch (SQLException e) {
            conn.rollback(); // Rollback en caso de error
            throw new SQLException("Error al eliminar la tabla Factura.", e);
        }
    }

    @Override
    public void createTable() throws SQLException {
        Connection conn = MySqlConnectionFactory.getInstance().getConnection();

        String table = "CREATE TABLE IF NOT EXISTS Factura(" +
                "idFactura INT," +
                "idCliente INT," +
                "PRIMARY KEY(idFactura))";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(table)) {
            ps.executeUpdate();

            conn.commit();
            conn.close();
        } catch (SQLException e) {
            conn.rollback(); // Rollback en caso de error
            throw new SQLException("Error al crear la tabla Factura.", e);
        }
    }

    @Override
    public void insert (Factura f) throws SQLException {
        Connection conn = MySqlConnectionFactory.getInstance().getConnection();

        String query = "INSERT INTO Factura(idFactura, idCliente) VALUES (?, ?)";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, f.getIdFactura());
            ps.setInt(2, f.getIdCliente());
            ps.executeUpdate();

            conn.commit();
            conn.close();
        } catch (SQLException e) {
            conn.rollback(); // Rollback en caso de error
            throw new SQLException("Error al insertar Factura!", e);
        }
    }

    @Override
    public Factura select (int id) throws SQLException {
        Connection conn = MySqlConnectionFactory.getInstance().getConnection();

        Factura f = null;
        String query = "SELECT * FROM Factura WHERE idFactura=?";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            ps.setInt(1, id);

            f = new Factura(rs.getInt(1), rs.getInt(2));

            conn.close();
        } catch (SQLException e) {
            throw new SQLException("Error al seleccionar Cliente con id=" + id + "!", e);
        }

        return f;
    }

    @Override
    public List<Factura> selectAll () throws SQLException {
        Connection conn = MySqlConnectionFactory.getInstance().getConnection();

        List<Factura> facturas = new ArrayList<>();
        String query = "SELECT * FROM Factura";

        // try-with-resources asegura que PreparedStatement y ResultSet se cierren automáticamente
        try (PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                facturas.add(new Factura(rs.getInt(1), rs.getInt(2)));
            }

            conn.close();
        } catch (SQLException e) {
            throw new SQLException("Error al obtener Facturas!", e);
        }

        return facturas;
    }

    @Override
    public boolean update (Factura f) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete (int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
