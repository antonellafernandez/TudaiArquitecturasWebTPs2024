package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlPersonaDAO implements PersonaDAO {
    private Connection conn;

    public MySqlPersonaDAO(Connection conn) {
        this.conn = conn;

        try {
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createTable() throws SQLException {
        String drop_table = "DROP TABLE IF EXISTS Persona";

        PreparedStatement ps1 = conn.prepareStatement(drop_table);
        ps1.executeUpdate();
        ps1.close();

        String table = "CREATE TABLE IF NOT EXISTS Persona(" +
                "id INT," +
                "nombre VARCHAR(500)," +
                "edad INT," +
                "PRIMARY KEY(id))";

        PreparedStatement ps2 = conn.prepareStatement(table);
        ps2.executeUpdate();
        ps2.close();
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