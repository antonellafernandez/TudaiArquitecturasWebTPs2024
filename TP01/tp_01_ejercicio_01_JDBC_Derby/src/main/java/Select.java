import org.apache.derby.jdbc.EmbeddedDriver;

import java.sql.*;

public class Select {
    public static void main(String[] args) {
        new EmbeddedDriver();
        String uri = "jdbc:derby:MyDerbyDb;create=true"; // Si no existe la bdd, la crea

        try {
            Connection conn = DriverManager.getConnection(uri);

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
