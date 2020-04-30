package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Color;
import model.Game;
import model.GameDie;
import model.PatternCardField;
import model.Player;

public class GameDieDBA {
	
	private DataBaseConnection conn;
	
	public GameDieDBA(DataBaseConnection c) {
		this.conn = c;
	}
	
	public void addGameDie(GameDie gamedie,Game game) {
		
		String query = "INSERT INTO gamedie(idgame,dienumber,diecolor) VALUES("+game.getGameID()+","+gamedie.getNumber()+","+getStringFromColor(gamedie)+","+gamedie.getEyes()+");";
		try {
			Statement stmt = conn.createStatemant();
			stmt.executeUpdate(query);
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void addRoundID(GameDie gamedie, Game game, int round) {
		
		String query = "UPDATE gamedie SET roundID = "+round+" WHERE idgame = "+game.getGameID()+"AND dienumber ="+gamedie.getNumber()+"AND diecolor= "+getStringFromColor(gamedie)+";";
		try {
				Statement stmt = conn.createStatemant();
				stmt.executeUpdate(query);
				stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void addEyes(GameDie gamedie, Game game, int eyes) {
		String query = "UPDATE gamedie SET eyes = "+eyes+" WHERE idgame = "+game.getGameID()+"AND dienumber = "+gamedie.getNumber()+"AND diecolor = "+getStringFromColor(gamedie)+";";
		try {
				Statement stmt = conn.createStatemant();
				stmt.executeUpdate(query);
				stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String getStringFromColor(GameDie gamedie) {
		String color;
		switch(gamedie.getColor()) {
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
	
	private Color getColorFromString(String c) {
		Color color = Color.BLUE;
		switch(c) {
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
		default:
			color = null;
		}
		return color;
	}
	
	public void addDieToRoundTrack(GameDie gamedie, Game game, int round) {
		String query = "UPDATE gamedie SET roundtrack = "+round+" WHERE idgame = "+game.getGameID()+"AND dienumber = "+gamedie.getNumber()+"AND diecolor = "+getStringFromColor(gamedie)+";";
		try {
				Statement stmt = conn.createStatemant();
				stmt.executeUpdate(query);
				stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<GameDie> getAllDiceFromGame(Game game) {
        ArrayList<GameDie> list = new ArrayList<GameDie>();
        String query = "SELECT * FROM gamedie WHERE idgame="+game.getGameID()+";";
        try {
        	Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                GameDie gameDie = new GameDie(getColorFromString(rs.getString("color")),rs.getInt("dienumber"),rs.getInt("eyes"));
                list.add(gameDie);
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
	
	public GameDie getDie(Game game,GameDie die) {
		 GameDie gameDie = null; 
		String query = "SELECT * FROM gamedie WHERE idgame="+game.getGameID()+"AND dienumber = "+die.getNumber()+"AND diecolor = "+getStringFromColor(die)+";";
	        try {
	        	Statement stmt = conn.createStatemant();
				ResultSet rs = stmt.executeQuery(query);
	            while (rs.next()) {
	                gameDie = new GameDie(getColorFromString(rs.getString("color")),rs.getInt("dienumber"),rs.getInt("eyes"));
	            }
	            stmt.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return gameDie;
	}
	
	public ArrayList<GameDie> getAllRoundDice(Game game) {
        ArrayList<GameDie> list = new ArrayList<GameDie>();
        String query = "SELECT * FROM gamedie WHERE idgame="+game.getGameID()+"AND roundID = "+game.getRound()+";";
        try {
        	Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                GameDie gameDie = new GameDie(getColorFromString(rs.getString("color")),rs.getInt("dienumber"),rs.getInt("eyes"));
                list.add(gameDie);
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
	
	public ArrayList<GameDie> getAllavailableDiceOfRound(Game game) {
        ArrayList<GameDie> list = new ArrayList<GameDie>();
        String query = "SELECT gamedie.* FROM gamedie LEFT JOIN playerframefield ON gamedie.dienumber = playerframefield.dienumber "
        		+ "AND gamedie.idgame = playerframefield.idgame AND gamedie.diecolor = playerframefield.diecolor "
        		+ "WHERE playerframefield.dienumber IS NULL AND playerframefield.diecolor IS NULL "
        		+ "AND gamedie.idgame= "+game.getGameID()+" AND round= "+game.getRound()+";";
        try {
        	Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                GameDie gameDie = new GameDie(getColorFromString(rs.getString("color")),rs.getInt("dienumber"),rs.getInt("eyes"));
                gameDie.setAvailable(true);
                list.add(gameDie);
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
	
	public ArrayList<GameDie> getDiceOnRoundTrack(Game game) {
        ArrayList<GameDie> list = new ArrayList<GameDie>();
        String query = "SELECT * FROM gamedie WHERE idgame="+game.getGameID()+"AND roundtrack IS NOT NULL;";
        try {
        	Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                GameDie gameDie = new GameDie(getColorFromString(rs.getString("color")),rs.getInt("dienumber"),rs.getInt("eyes"));
                gameDie.setAvailable(false);
                list.add(gameDie);
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    } 
	
	public void placeDieOnPatternCardField(GameDie die, Player player, PatternCardField patterncardfield) {
		String query = "UPDATE playerframefield SET dienumber = "+die.getNumber()+", diecolor = "+getStringFromColor(die)+
				" WHERE idplayer = "+player.getId()+" AND position_x = "+patterncardfield.getPositionX()+" And  position_y = "+patterncardfield.getPositionY()+
				" idgame = "+player.getGame()+";";
		try {
				Statement stmt = conn.createStatemant();
				stmt.executeUpdate(query);
				stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
