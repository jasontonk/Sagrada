package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

import model.Game;

public class GameDBA {
	
	private DataBaseConnection conn;
	private int gameid;
	
	public GameDBA(DataBaseConnection c) {
		this.conn = c;
	}
	
	public Game getGameByID(int id) {
		Game game = null;
        
        String query = "SELECT * FROM game WHERE idgame= "+id+";";
        try {
			Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				game = new Game(conn, false);
				game.setGameID(rs.getInt("idgame"));
				game.setRound(getCurrentRound(id));
			}
			stmt.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
        return game;
    }
	
	public boolean addNewGameDB(LocalDateTime datetime, Game game) {
		
		int gameID = autoIdGame();
		game.setGameID(gameID);
		String query = "INSERT INTO game VALUES("+gameID+",null, 1,'"+datetime+"');";
		
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
	
	public String gameInfoDB(int gameid){
		String result = "";
		
		String query = "SELECT * FROM game WHERE idgame = '"+gameid+"';";
	
		try {
			Statement stmt = conn.getConn().createStatement();
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
			Statement stmt = conn.getConn().createStatement();
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
	

	public void changeCurrentPlayer(int playerid, Game game) {
		
		String query = "UPDATE game SET turn_idplayer = "+playerid+" WHERE idgame = "+game.getGameID()+";";

		try {
				Statement stmt = conn.getConn().createStatement();
				stmt.executeUpdate(query);
				stmt.close();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Integer> getAllGamesId(){
		ArrayList<Integer> gameid = new ArrayList<Integer>();
		String query = "SELECT idgame FROM game";
		
		try {
			Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				gameid.add(rs.getInt("idgame"));
            }
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return gameid;
	}
	
	public int getCurrentRound(int gameid) {
		int currentRound = 1;
		String query = "SELECT DISTINCT('round') FROM gamedie WHERE idgame = "+gameid+" AND roundtrack IS NOT NULL ORDER BY round DESC LIMIT 1;"; 
		try {
			Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
                currentRound = rs.getInt("round") + 1;
            }
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return currentRound;
	}

	public boolean isRoundClockwise(Game game) {
		boolean isClockwise = false;
		String query = "SELECT clockwise FROM round INNER JOIN game ON game.current_roundID = round.roundID WHERE idgame = "+game.getGameID()+";"; 
		try {
			Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
                isClockwise = rs.getBoolean("clockwise");
            }
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return isClockwise;
	}

	public void changeRoundDirection(Game game) {
		
		String query = "UPDATE game SET current_roundID = "+(game.getRound()+1)+" WHERE idgame = "+game.getGameID()+";";
		try {
				Statement stmt = conn.getConn().createStatement();
				stmt.executeUpdate(query);
				stmt.close();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setNextRound(Game game) {
		String query = "UPDATE game SET current_roundID = "+(game.getRound()+2)+" WHERE idgame = "+game.getGameID()+";";
		try {
				Statement stmt = conn.getConn().createStatement();
				stmt.executeUpdate(query);
				stmt.close();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
