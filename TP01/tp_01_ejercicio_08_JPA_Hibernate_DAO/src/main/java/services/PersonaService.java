package services;

import dao.PersonaDAO;
import entities.Direccion;
import entities.Persona;
import factories.DAOFactory;

import java.sql.SQLException;
import java.util.List;

public class PersonaService {
    private PersonaDAO personaDAO;

    public PersonaService(DAOFactory factory) {
        this.personaDAO = factory.getPersonaDAO();
    }

    public void addPersona(Persona persona) {
        personaDAO.addPersona(persona);
    }

    public List<Persona> getAllPersonas() {
        return personaDAO.getAllPersonas();
    }
}
