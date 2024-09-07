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
		
		String driver = "com.mysql.cj.jdbc.Driver"; 
		try {
			Class.forName(driver).getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		String uri = "jdbc:mysql://localhost:3306/exampleDB2";
		try {
			Connection conn = DriverManager.getConnection(uri, "root", "password");
			conn.setAutoCommit(false);
			
			String select = "SELECT * FROM persona";
			PreparedStatement ps = conn.prepareStatement(select);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getInt(1)+", "+rs.getString(2)+", "+rs.getInt(3));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return;
	}


}

