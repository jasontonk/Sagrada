package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import model.Chat;

public class ChatDBA {

private DataBaseConnection conn;
	
	public ChatDBA(DataBaseConnection c) {
		this.conn = c;
	}
	
	public boolean addChatDB(int idplayer, String message
			, Chat chat
			) {
		Timestamp timestamp = chat.getTime();
		String query = "INSERT INTO chatline VALUES('"+idplayer+"','"+timestamp+"','"+message+"');";
		
		try {
			Statement stmt = conn.getConn().createStatement();
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
			Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				text = rs.getString("message");
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return text;
	}
	
	public void getTime(Chat chatline) {
		String query = "SELECT NOW()";
		try {
			Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				chatline.setTime(rs.getTimestamp("NOW()"));
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Chat> getChatlinesOfGame(int gameid) {
		ArrayList<Chat> chatlines = new ArrayList<>();
		String query = "SELECT chatline.* FROM chatline JOIN player ON chatline.idplayer = player.idplayer WHERE idgame = "+gameid+" ORDER BY time ASC;";
		try {
			Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				Chat chat = new Chat(rs.getInt("idplayer"), rs.getString("message"), conn); //edited to fix error
				chat.setTime(rs.getTimestamp("time"));
				chatlines.add(chat);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chatlines;
	}
}
