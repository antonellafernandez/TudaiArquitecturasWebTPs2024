package services;

import dao.interfaces.DAO;
import entities.Persona;
import factories.DAOFactory;

import java.util.List;

public class PersonaService {
    private DAO<Persona> personaDAO;

    public PersonaService(DAOFactory factory) {
        this.personaDAO = factory.getPersonaDAO();
    }

    public void addPersona(Persona persona) {
        personaDAO.insert(persona);
    }

    public List<Persona> getAllPersonas() {
        return personaDAO.selectAll();
    }

    public boolean deletePersona(int id) { return personaDAO.delete(id); }
}
