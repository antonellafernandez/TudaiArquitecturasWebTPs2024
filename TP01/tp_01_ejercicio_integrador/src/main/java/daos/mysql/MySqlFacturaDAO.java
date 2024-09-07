package daos.mysql;

import daos.interfaces.FacturaDAO;
import entities.Factura;
import factories.MySqlConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Patrón Singleton
public class MySqlFacturaDAO implements FacturaDAO {
    private static MySqlFacturaDAO unicaInstancia;
    private final Connection conn;

    private MySqlFacturaDAO() throws SQLException {
        this.conn = MySqlConnectionFactory.getConnection();
    }

    public static MySqlFacturaDAO getInstance() throws SQLException {
        if (unicaInstancia == null) {
            unicaInstancia = new MySqlFacturaDAO();
        }

        return unicaInstancia;
    }

    @Override
    public void insert (Factura f) throws SQLException {
        try {
            String query = "INSERT INTO Factura(idCliente) VALUES (?)";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, f.getIdCliente());
            ps.executeUpdate();
            ps.close();

            conn.commit();
        } catch (SQLException e) {
            throw new SQLException("Error al insertar Factura!", e);
        }
    }

    @Override
    public Factura select (Integer id) throws SQLException {
        Factura f = null;

        try {
            String query = "SELECT * FROM Factura WHERE idFactura=?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id.intValue());
            ResultSet rs = ps.executeQuery();

            f = new Factura(rs.getInt(1), rs.getInt(2));

            conn.commit();
        } catch (SQLException e) {
            throw new SQLException("Error al seleccionar Cliente con id=" + id + "!", e);
        }

        return f;
    }

    @Override
    public List<Factura> selectAll () throws SQLException {
        List<Factura> facturas = new ArrayList<>();

        try {
            String query = "SELECT * FROM Factura";

            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                facturas.add(new Factura(rs.getInt(1), rs.getInt(2)));
            }

            conn.commit();
        } catch (SQLException e) {
            throw new SQLException("Error al obtener Facturas!", e);
        }

        return facturas;
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
