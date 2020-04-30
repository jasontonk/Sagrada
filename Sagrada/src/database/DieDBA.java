package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Color;
import model.Die;

public class DieDBA {

	private DataBaseConnection conn;
	
	
	public DieDBA(DataBaseConnection c) {
		this.conn = c;
	}
	
	public ArrayList<Die> getDice(){
		ArrayList<Die> list = new ArrayList<>();
		String query = "SELECT * FROM die;";
		Color color = Color.BLUE;
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				switch(rs.getString("color")) {
				case "blue":
					color = Color.BLUE;
					break;
				case "green":
					color = Color.GREEN;
					break;
				case "purple":
					color = Color.PURPLE;
					break;
				case "red":
					color = Color.RED;
					break;
				case "yellow":
					color = Color.YELLOW;
					break;
				}
				
				Die die = new Die(color ,rs.getInt("number"));
				list.add(die);
			}
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
