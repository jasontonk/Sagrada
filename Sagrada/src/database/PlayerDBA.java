package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.ModelColor;
import model.Game;
import model.PatternCard;
import model.Player;

public class PlayerDBA {

	private DataBaseConnection conn;
	private GameDBA game;
	
	public PlayerDBA(DataBaseConnection c) {
		this.conn = c;
		game = new GameDBA(c);
	}
	
	
	public boolean addPlayer(Player player) {
		int playerid = autoIdPlayer();
		
		String query = "INSERT INTO player VALUES("+playerid+",'"+player.getName()+"',"+player.getGame().getGameID()+
				",'"+player.getPlayerStatus()+"',"+player.getSequenceNumber()+",'"+getStringFromColor(player)+
				"',"+player.getPatternCard().getPatterncardID()+","+player.getScore()+");";
		
		try {
			Statement stmt = conn.createStatemant();
			stmt.executeUpdate(query);
			stmt.close();
			game.addPlayerDB(playerid, player.getGame().getGameID());
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	private String getStringFromColor(Player player) {
		String color;
		switch(player.getColor()) {
		case BLUE:
			color = "blue" ;
			break;
		case GREEN:
			color = "green";
			break;
		case PURPLE:
			color = "purple";
			break;
		case RED:
			color = "red";
			break;
		case YELLOW:
			color = "yellow";
			break;
		default:
			color = null;
		}
		return color;
	}
	
	private ModelColor getColorFromString(String c) {
		ModelColor modelColor = ModelColor.BLUE;
		switch(c) {
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
		default:
			modelColor = null;
		}
		return modelColor;
	}
	
	private int autoIdPlayer() {

		int playerid = 0;
		
		ArrayList<Integer> results = new ArrayList<Integer>();
		
		String query = "SELECT idplayer FROM player;";
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				int idgame = rs.getInt("idplayer");
				results.add(idgame);
			}
			stmt.close();
		} catch (SQLException e) {
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
	
	public void setPlayerStatus(Player player){
		
		String query = "UPDATE player SET playstatus = '"+player.getPlayerStatus()+"' WHERE idplayer = "+player.getId()+";";
		try {
			Statement stmt = conn.createStatemant();
			stmt.executeUpdate(query);
			stmt.close();
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setScore(Player player) {
		String query = "UPDATE player SET score = "+player.getScore()+" WHERE idplayer = "+player.getId()+";";
		try {
			Statement stmt = conn.createStatemant();
			stmt.executeUpdate(query);
			stmt.close();
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Player getPlayerUsingID(int idplayer) {
		
		Player player = new Player(conn, null);
        String query = "SELECT * FROM player WHERE idplayer="+idplayer+";";
        try {
        	Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
            	PatternCardDBA patternCard = new PatternCardDBA(conn);
            	GameDBA game = new GameDBA(conn);
            	AccountDBA account = new AccountDBA(conn);
            	player.setAccount(account.GetAccountDB(rs.getString("username")));
            	player.setName(rs.getString("username"));
            	player.setId(idplayer);
            	player.setPlayerStatus(rs.getString("playstatus"));
            	player.setSequenceNumber(rs.getInt("seqnr"));
            	player.setScore(rs.getInt("score"));
            	player.setColor(getColorFromString(rs.getString("private_objectivecard_color")));
            	player.setPatternCard(patternCard.getPatterncardByID(rs.getInt("idpatterncard")));
            	player.setGame(game.getGameByID(rs.getInt("idgame")));
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return player;
    }
	
	public Player getPlayerUsingSeqnrAndGame(int seqnr, Game game) {
			
			Player player = new Player(conn, null);
	        String query = "SELECT * FROM player WHERE seqnr= "+seqnr+" AND idgame="+game.getGameID()+";";
	        try {
	        	Statement stmt = conn.createStatemant();
				ResultSet rs = stmt.executeQuery(query);
	            if (rs.next()) {
	            	PatternCardDBA patternCard = new PatternCardDBA(conn);
	            	AccountDBA account = new AccountDBA(conn);
	            	GameDBA g = new GameDBA(conn);
	            	player.setAccount(account.GetAccountDB(rs.getString("username")));
	            	player.setName(rs.getString("username"));
	            	player.setId(rs.getInt("idplayer"));
	            	player.setPlayerStatus(rs.getString("playstatus"));
	            	player.setSequenceNumber(rs.getInt("seqnr"));
	            	player.setScore(rs.getInt("score"));
	            	player.setColor(getColorFromString(rs.getString("private_objectivecard_color")));
	            	player.setPatternCard(patternCard.getPatterncardByID(rs.getInt("idpatterncard")));
	            	player.setGame(g.getGameByID(rs.getInt("idgame")));
	            }
	            stmt.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return player;
	    }

public void setPlayerPatternCard(PatternCard patternCard,Player player){
		
		String query = "UPDATE player SET idpatterncard = "+patternCard.getPatterncardID()+" WHERE idplayer = "+player.getId()+";";
		try {
			Statement stmt = conn.createStatemant();
			stmt.executeUpdate(query);
			stmt.close();
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
