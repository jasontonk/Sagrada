package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PlayerDBA {

	private DataBaseConnection conn;
	private int idplayer;
	private String username;
	private int idGame;
	private String playStatus;
	private int seqnr;
	private String cardColor;
	private int idPatternCard;
	private int score;
	private String query; 
	private GameDBA game;
	
	public PlayerDBA(DataBaseConnection c) {
		this.conn = c;
		game = new GameDBA(c);
	}
	
	
	public boolean addPlayer(String username, int idgame, String playstatus, int seqnr, String playerColor, int idpattercard, int score ) {
		int playerid = autoIdPlayer();
		
		query = "INSERT INTO player VALUES("+playerid+",'"+username+"',"+idgame+",'"+playstatus+"',"+seqnr+",'"+playerColor+"',"+idpattercard+","+score+");";
		
		try {
			Statement stmt = conn.createStatemant();
			stmt.executeUpdate(query);
			stmt.close();
			game.addPlayerDB(playerid, idgame);
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	private int autoIdPlayer() {

		int playerid = 0;
		
		ArrayList<Integer> results = new ArrayList<Integer>();
		
		query = "SELECT idplayer FROM player;";
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				int idgame = rs.getInt("idplayer");
				results.add(idgame);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < results.size();i++) {
			if(playerid < results.get(i)) {
				playerid = results.get(i);
			}
		}
		playerid = playerid +1;
		
		return playerid;
	}
	
	public String playerInfo(int playerid) {
		String result = "";
		
		query = "SELECT * FROM player WHERE idplayer = "+playerid+";";
	
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				idplayer = rs.getInt("idplayer");
				this.username = rs.getString("username");
				idGame = rs.getInt("idgame");
				playStatus = rs.getString("playstatus");
				seqnr = rs.getInt("seqnr");
				cardColor = rs.getString("private_objectivecard_color");
				idPatternCard = rs.getInt("idpatterncard");
				score = rs.getInt("score");
				
				result= idplayer+", "+username+", "+idGame+", "+playStatus+", "+seqnr+", "+cardColor+", "+idPatternCard+", "+score;
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	private void updatePlayer() {
		try {
			Statement stmt = conn.createStatemant();
			stmt.executeUpdate(query);
			stmt.close();
		
	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public void setPlayStatus(int playerid, String playstatus){
		
		query = "UPDATE player SET playstatus = '"+playstatus+"' WHERE idplayer = "+playerid+";";
		updatePlayer();
	}
	
	public void setScore(int playerid, int score) {
		query = "UPDATE player SET score = "+score+" WHERE idplayer = "+playerid+";";
		updatePlayer();
	}
	
	public String getUsername(int playerid) {
		playerInfo(playerid);
		return username;
	}

	public int getIdGame(int playerid) {
		playerInfo(playerid);
		return idGame;
	}

	public String getPlayStatus(int playerid) {
		playerInfo(playerid);
		return playStatus;
	}

	public int getSeqnr(int playerid) {
		playerInfo(playerid);
		return seqnr;
	}

	public String getCardColor(int playerid) {
		playerInfo(playerid);
		return cardColor;
	}

	public int getIdPatternCard(int playerid) {
		playerInfo(playerid);
		return idPatternCard;
	}

	public int getScore(int playerid) {
		playerInfo(playerid);
		return score;
	}
}
