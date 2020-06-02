package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
	
	private Connection conn;
	
	public DataBaseConnection(String c) {
		loadDBdriver(c);
		makeConnection();
		
	}
	
	private boolean loadDBdriver(String s) {
		try {
			Class.forName(s);
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean makeConnection() {
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://databases.aii.avans.nl:3306/jjtonk_db2?user=jjtonk&password=Ab12345");
//			conn = DriverManager.getConnection("jdbc:mysql://databases.aii.avans.nl:3306/2020_soprj4_sagrada_ab?user=2020_soprj4_b&password=Ab12345");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public Connection getConn() {
		return conn;
	}
}
