package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.ModelColor;
import model.Die;

public class DieDBA {

	private DataBaseConnection conn;
	
	
	public DieDBA(DataBaseConnection c) {
		this.conn = c;
	}
	
	public ArrayList<Die> getDice(){
		ArrayList<Die> list = new ArrayList<>();
		String query = "SELECT * FROM die;";
		ModelColor modelColor = ModelColor.BLUE;
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				switch(rs.getString("color")) {
				case "blue":
					modelColor = ModelColor.BLUE;
					break;
				case "green":
					modelColor = ModelColor.GREEN;
					break;
				case "purple":
					modelColor = ModelColor.PURPLE;
					break;
				case "red":
					modelColor = ModelColor.RED;
					break;
				case "yellow":
					modelColor = ModelColor.YELLOW;
					break;
				}
				
				Die die = new Die(modelColor ,rs.getInt("number"));
				list.add(die);
			}
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
