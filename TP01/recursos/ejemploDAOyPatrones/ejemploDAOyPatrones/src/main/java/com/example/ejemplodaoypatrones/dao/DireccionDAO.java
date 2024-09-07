package com.example.ejemplodaoypatrones.dao;

import com.example.ejemplodaoypatrones.entities.Direccion;
import com.example.ejemplodaoypatrones.entities.Persona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DireccionDAO {
    private Connection conn;

    public DireccionDAO(Connection conn) {
        this.conn = conn;
    }
    public int insert(Direccion dao) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }


    public boolean delete(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }


    public Direccion find(Integer pk) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'find'");
    }


    public boolean update(Direccion dao) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }


    public List<Direccion> selectList() {
        String query = "SELECT * " +
                "FROM Direccion ";
        Persona personaById = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Direccion> listado = null;
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            // Crear una nueva instancia de Persona con los datos recuperados de la consulta
            listado = new ArrayList<Direccion>();
            while (rs.next()) { // Verificar si hay resultados
                int idDireccion = rs.getInt("idDireccion");
                String ciudad = rs.getString("ciudad");
                String calle = rs.getString("calle");
                int numero = rs.getInt("numero");
                Direccion direccion = new Direccion(idDireccion, ciudad, calle, numero);
                listado.add(direccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return listado;
    }

}
