package com.example.ejemplodaoypatrones.dao;

import com.example.ejemplodaoypatrones.dto.PersonaDTO;
import com.example.ejemplodaoypatrones.entities.Persona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersonaDAO {
    private Connection conn;

    public PersonaDAO(Connection conn) {
        this.conn = conn;
    }
    public void insertPersona(Persona persona) {
        String query = "INSERT INTO Persona (idPersona, nombre, edad, idDireccion) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, persona.getIdPersona()); // idPersona
            ps.setString(2, persona.getNombre()); // nombre
            ps.setInt(3, persona.getEdad()); // edad
            ps.setInt(4, persona.getIdDireccion()); // idDireccion
            ps.executeUpdate();
            System.out.println("Persona insertada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean delete(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }


    public Persona find(Integer pk) {
        String query = "SELECT p.nombre, p.edad, p.idDireccion " +
                "FROM Persona p " +
                "WHERE p.idPersona = ?";
        Persona personaById = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, pk); // Establecer el parámetro en la consulta SQL
            rs = ps.executeQuery();
            if (rs.next()) { // Verificar si hay resultados
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");
                int direccion = rs.getInt("idDireccion");

                // Crear una nueva instancia de Persona con los datos recuperados de la consulta
                personaById = new Persona(pk, nombre, edad,direccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return personaById;
    }

    public PersonaDTO findPersonaDTO(Integer pk) {
        String query = "SELECT p.nombre, p.edad, d.ciudad, d.calle, d.numero " +
                "FROM Persona p " +
                "JOIN Direccion d ON p.idDireccion = d.idDireccion " +
                "WHERE p.idPersona = ?";
        Persona personaById = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        PersonaDTO personaDTO = null;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, pk); // Establecer el parámetro en la consulta SQL
            rs = ps.executeQuery();
            if (rs.next()) { // Verificar si hay resultados
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");
                String ciudad = rs.getString("ciudad");
                String calle = rs.getString("calle");
                int numero = rs.getInt("numero");

                // Crear una nueva instancia de PersonaDTO con los datos recuperados de la consulta
                personaDTO = new PersonaDTO(nombre, edad, ciudad, calle, numero);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return personaDTO;
    }



    public boolean update(Persona dao) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }


    public List<Persona> selectList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectList'");
    }

}
