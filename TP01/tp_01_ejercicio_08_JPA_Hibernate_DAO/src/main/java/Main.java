import entities.Direccion;
import entities.Persona;
import factories.DAOFactory;
import services.DireccionService;
import services.PersonaService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Elegir la base de datos que deseas usar (MySQL o Derby)
        // RECORDAR MODIFICAR PERSISTENCE.XML
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MYSQL_JDBC);
        //DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.DERBY_JDBC);

        // Crear una instancia del servicio utilizando la fábrica seleccionada
        DireccionService direccionService = new DireccionService(factory);
        PersonaService personaService = new PersonaService(factory);

        try {
            // Agregar direccion a la base de datos
            direccionService.addDireccion(new Direccion("Tandil", "Sarmiento 772"));

            Direccion direccion = direccionService.getDireccionById(1);

            // Agregar personas a la base de datos
            personaService.addPersona(new Persona(1, "Juan", 20, direccion));
            personaService.addPersona(new Persona(2, "Paula", 30, direccion));

            // Obtener todas las personas de la base de datos
            List<Persona> personas = personaService.getAllPersonas();

            // Mostrar la lista de personas
            for (Persona persona : personas) {
                System.out.println(persona);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
