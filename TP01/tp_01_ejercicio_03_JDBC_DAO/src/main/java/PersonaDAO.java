import java.sql.SQLException;
import java.util.List;

public interface PersonaDAO {
    void addPerson(int id, String name, int age) throws SQLException;
    List<Persona> getAllPersons() throws SQLException;
}
