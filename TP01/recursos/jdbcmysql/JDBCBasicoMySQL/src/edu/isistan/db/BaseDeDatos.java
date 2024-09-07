package edu.isistan.db;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BaseDeDatos {

	public static void main(String[] args) {

		String driver = "com.mysql.cj.jdbc.Driver"; 
		try {
			Class.forName(driver).getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		String uri = "jdbc:mysql://localhost:3306/exampleDB3";
		try {
			Connection conn = DriverManager.getConnection(uri, "root", "password");
			conn.setAutoCommit(false);
			
			String table = "CREATE TABLE persona(" 
					+ "id INT, " 
					+ "nombre VARCHAR(500),"
					+ "edad INT,"
					+ "PRIMARY KEY(id))";
			
			conn.prepareStatement(table).execute();
			conn.commit();
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return;
	}

}
