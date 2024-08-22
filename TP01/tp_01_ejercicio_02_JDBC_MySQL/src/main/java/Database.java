import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    public static void main(String[] args) {
        String driver = "com.mysql.jdbc.Driver";

        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                 | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        String uri = "jdbc:mysql://localhost:3306/example_db";

        try {
            Connection conn = DriverManager.getConnection(uri, "root", "password");

            conn.setAutoCommit(false);

            createTables(conn);

            addPerson(conn, 1, "Juan", 20);
            addPerson(conn, 2, "Paula", 30);

            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void addPerson(Connection conn, int id, String name, int years) throws SQLException {
        // Asegurar que se inserten valores válidos
        String insert = "INSERT INTO Persona (id, nombre, edad) VALUES (?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(insert);

        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setInt(3, years);

        ps.executeUpdate();
        ps.close();
        conn.commit();
    }

    private static void createTables(Connection conn) throws SQLException {
        String table = "CREATE TABLE Persona(" +
                "id INT," +
                "nombre VARCHAR(500)," +
                "edad INT," +
                "PRIMARY KEY(id))";

        conn.prepareStatement(table).execute();
        conn.commit();
    }
}
