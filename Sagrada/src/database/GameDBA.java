package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GameDBA {
	
	private DataBaseConnection conn;
	private int gameid;
	
	public GameDBA(DataBaseConnection c) {
		this.conn = c;
	}
	
	public boolean addNewGameDB(LocalDateTime datetime) {
		
		String query = "INSERT INTO game VALUES("+autoIdGame()+",null,'"+datetime+"');";
		
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
	
	public String gameInfoDB(int gameid){
		String result = "";
		
		String query = "SELECT * FROM game WHERE idgame = '"+gameid+"';";
	
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				int idgame = rs.getInt("idgame");
				int idplayer = rs.getInt("turn_idplayer");
				Timestamp date = rs.getTimestamp("creationdate");
				String s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
				result= " " +idgame+ "," +idplayer+","+s;
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	private int autoIdGame() {
		gameid = 0;
		
		ArrayList<Integer> results = new ArrayList<Integer>();
		
		String query = "SELECT idgame FROM game;";
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				int idgame = rs.getInt("idgame");
				results.add(idgame);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < results.size();i++) {
			if(gameid < results.get(i)) {
				gameid = results.get(i);
			}
		}
		gameid = gameid +1;
		
		return gameid;
	}
	
	public void addPlayerDB(int playerid, int gameid) {
		
		String query = "UPDATE game SET turn_idplayer = "+playerid+" WHERE idgame = "+gameid+";";
		try {
				Statement stmt = conn.createStatemant();
				stmt.executeUpdate(query);
				stmt.close();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
