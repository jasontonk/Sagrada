package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.PublicObjectiveCard;
import model.Toolcard;

public class ToolCardDBA {

	private DataBaseConnection conn;
	
	public ToolCardDBA(DataBaseConnection c) {
		this.conn = c;
	}
	
	public ArrayList<Toolcard> get3Toolcards() {
				
		int id1 =  (int)(Math.random() * 12 + 1);
		int id2 = (int)(Math.random() * 12 + 1);
		while(id2 == id1) {
			id2 = (int)(Math.random() * 12 + 1);
		}
		int id3 = (int)(Math.random() * 12 + 1);
		while(id3 != id1 && id3 != id2) {
			id3 = (int)(Math.random() * 12 + 1);
		}
	       
		ArrayList<Toolcard> list = new ArrayList<Toolcard>();
	    String query = "SELECT * from toolcard where idtoolcard = "+id1+" OR idtoolcard = "+id2+" OR idtoolcard = "+id3+";";
	    try {
	        Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				Toolcard toolcard = new Toolcard(rs.getString("name"),rs.getString("description"),rs.getInt("points"),rs.getInt("idpublic_objectivecard"));
				list.add(toolcard);
			}
			stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return list;
	    }
}
