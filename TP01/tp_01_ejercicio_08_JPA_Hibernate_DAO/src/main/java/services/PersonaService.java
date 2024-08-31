package services;

import dao.PersonaDAO;
import factories.DAOFactory;

import java.sql.SQLException;
import java.util.List;

public class PersonaService {
    private PersonaDAO personaDAO;

    public PersonaService(DAOFactory factory) {
        this.personaDAO = factory.getPersonaDAO();
    }

    public void addPerson(int id, String name, int age) throws SQLException {
        personaDAO.addPerson(id, name, age);
    }

    public List<Persona> getAllPersons() throws SQLException {
        return personaDAO.getAllPersons();
    }
}
