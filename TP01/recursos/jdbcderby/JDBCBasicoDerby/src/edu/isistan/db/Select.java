package edu.isistan.db;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Select {
	
	public static void main(String[] args) {
		//new EmbeddedDriver();
		
		String driver = "org.apache.derby.jdbc.EmbeddedDriver"; 
		try {
			Class.forName(driver).getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		String uri = "jdbc:derby:exampleDB;create=true";
		try {
			Connection conn = DriverManager.getConnection(uri);
			
			String select = "SELECT * FROM persona";
			PreparedStatement ps = conn.prepareStatement(select);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				System.out.println(rs.getInt(1)+", "+rs.getString(2)+", "+rs.getInt(3));
				
//				System.out.println("Deleting ... "+rs.getInt(1));
//			    String delete = "DELETE FROM persona WHERE id = ?";
//				PreparedStatement ps1 = conn.prepareStatement(delete);
//				ps1.setInt(1, rs.getInt(1));
//				ps1.executeUpdate();
			}
			System.out.println("Done");
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return;
	}

}
