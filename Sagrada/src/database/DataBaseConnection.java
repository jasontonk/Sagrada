package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
			conn = DriverManager.getConnection("jdbc:mysql://localhost/sagrada?user=root&password=Matheus18");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public Statement createStatemant() {
		try {
			return conn.createStatement();
		}catch(SQLException e){
			return null;
		}
	}
	
	

}
