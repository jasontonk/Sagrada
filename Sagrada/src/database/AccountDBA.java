package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountDBA {

	private DataBaseConnection conn;
	
	public AccountDBA(DataBaseConnection c) {
		this.conn = c;
	}
	
	public boolean addAccountDB(String username, String password) {
		String query = "INSERT INTO account VALUES('"+username+"','"+password+"');";
		
		try {
			Statement stmt = conn.createStatemant();
			stmt.executeUpdate(query);
			stmt.close();
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	public String GetAccountDB(String s) {
		String account = "";
		String query = "SELECT username FROM account WHERE username = '"+s+"';";
		
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				account = rs.getString("username");
			}
			stmt.close();
			if(account == "" ) {
				return "Account bestaat niet";
			}else {
				return account;
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	public String updateUsernameDB(String oldaccount,String newaccount) {
		
		String query = "UPDATE account SET username = '"+newaccount+"' WHERE username = '"+oldaccount+"';";
		try {
			if(GetAccountDB(oldaccount).equals(oldaccount)) {
				Statement stmt = conn.createStatemant();
				stmt.executeUpdate(query);
				stmt.close();
				return "Account is succesvol geupdated";
			}else {
				return "Account bestaat niet";
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ging wat fout";
		}
	}
	
	public String updatePasswordDB(String username, String passwoord) {
		String query = "UPDATE account SET password = '"+passwoord+"' WHERE username = '"+username+"';";
		try {
			if(GetAccountDB(username).equals(username)) {
				Statement stmt = conn.createStatemant();
				stmt.executeUpdate(query);
				stmt.close();
				return "Wachtwoord is succesvol geupdated";
			}else {
				return "Account bestaat niet";
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ging wat fout";
		}
	}
}
