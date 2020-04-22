package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class ChatDBA {

private DataBaseConnection conn;
	
	public ChatDBA(DataBaseConnection c) {
		this.conn = c;
	}
	
	public boolean addChatDB(int idplayer, Timestamp timestamp, String message) {
		String query = "INSERT INTO chatline VALUES('"+idplayer+"','"+timestamp+"','"+message+"');";
		
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
	
	public String getChatTextDB(int idplayer, Timestamp timestamp) {
		String text = "";
		
		String query = "SELECT * FROM chatline WHERE player_idplayer = "+idplayer+" AND time ='"+timestamp+"';";
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				text = rs.getString("message");
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}
}
