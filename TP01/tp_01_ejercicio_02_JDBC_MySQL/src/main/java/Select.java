import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class Select {
    public static void main(String[] args) {
        String driver = "com.mysql.cj.jdbc.Driver";

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

            String select = "SELECT * FROM Persona";
            PreparedStatement ps = conn.prepareStatement(select);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getInt(1) + ", " + rs.getString(2) + ", " + rs.getInt(3));
            }

            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
