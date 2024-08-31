package dao;

import entities.Persona;

import java.sql.SQLException;
import java.util.List;

public interface PersonaDAO {
    void addPersona(Persona persona);
    List<Persona> getAllPersonas();
}
