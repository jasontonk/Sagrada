package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Account;
import model.Die;

public class DieDBA {

	private DataBaseConnection conn;
	
	
	public DieDBA(DataBaseConnection c) {
		this.conn = c;
	}
	
	public ArrayList<Die> getDice(){
		ArrayList<Die> list = new ArrayList<>();
		String query = "SELECT * FROM die;";
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				Die die = new Die(rs.getString("color") ,rs.getInt("number"));
				list.add(die);
			}
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
