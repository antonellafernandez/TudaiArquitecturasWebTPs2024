import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Elegir la base de datos que deseas usar (MySQL o Derby)
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MYSQL_JDBC);
        //DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.DERBY_JDBC);

        // Crear una instancia del servicio utilizando la fábrica seleccionada
        PersonaService personaService = new PersonaService(factory);

        try {
            // Agregar personas a la base de datos
            personaService.addPerson(1, "Juan", 20);
            personaService.addPerson(2, "Paula", 30);

            // Obtener todas las personas de la base de datos
            List<Persona> personas = personaService.getAllPersons();

            // Mostrar la lista de personas
            for (Persona persona : personas) {
                System.out.println("ID: " + persona.getId() + ", Nombre: " + persona.getNombre() + ", Edad: " + persona.getEdad());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
