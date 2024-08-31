package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DerbyPersonaDAO implements PersonaDAO {
    private Connection conn;

    public DerbyPersonaDAO(Connection conn) {
        this.conn = conn;

        try {
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createTable() throws SQLException {
        String table = "CREATE TABLE Persona(" +
                "id INT," +
                "nombre VARCHAR(500)," +
                "edad INT," +
                "PRIMARY KEY(id))";

        PreparedStatement ps = conn.prepareStatement(table);
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public void addPerson(int id, String name, int age) throws SQLException {
        String insert = "INSERT INTO Persona (id, nombre, edad) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(insert);
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setInt(3, age);
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public List<Persona> getAllPersons() throws SQLException {
        List<Persona> persons = new ArrayList<>();
        String query = "SELECT * FROM Persona";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            persons.add(new Persona(rs.getInt("id"), rs.getString("nombre"), rs.getInt("edad")));
        }

        rs.close();
        ps.close();
        return persons;
    }
}
