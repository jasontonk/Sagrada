package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.ModelColor;
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
		String query = "INSERT INTO gamedie(idgame,dienumber,diecolor) VALUES("+game.getGameID()+","+gamedie.getNumber()+",'"+getStringFromColor(gamedie)+"');";
		try {
			Statement stmt = conn.getConn().createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void addRoundID(GameDie gamedie, Game game, int round) {
		
		String query = "UPDATE gamedie SET roundID = "+round+" WHERE idgame = "+game.getGameID()+" AND dienumber ="+gamedie.getNumber()+" AND diecolor= '"+getStringFromColor(gamedie)+"';";
		try {
				Statement stmt = conn.getConn().createStatement();
				stmt.executeUpdate(query);
				stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getRoundID(GameDie gamedie, Game game) {
		
		int round = 0;
        String query = "SELECT roundID FROM gamedie WHERE idgame="+game.getGameID()+" AND dienumber ="+gamedie.getNumber()+" AND diecolor= '"+getStringFromColor(gamedie)+"';";
        try {
        	Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
            	if(rs.getString("roundID")!= null) {
            		round = rs.getInt("roundID");
            	}
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return round;
	}
	public void addEyes(GameDie gamedie, Game game, int eyes) {
		String query = "UPDATE gamedie SET eyes = "+eyes+" WHERE idgame = "+game.getGameID()+" AND dienumber = "+gamedie.getNumber()+" AND diecolor = '"+getStringFromColor(gamedie)+"';";
		try {
				Statement stmt = conn.getConn().createStatement();
				stmt.executeUpdate(query);
				stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getStringFromColor(GameDie gamedie) {
		String color = null;
		if(gamedie.getColor()!= null) {
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
			}
		}
		return color;
	}
	
	public ModelColor getColorFromString(String c) {
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
	
	public void addDieToRoundTrack(GameDie gamedie, Game game, int round) {
		String query = "UPDATE gamedie SET roundtrack = "+round+" WHERE idgame = "+game.getGameID()+" AND dienumber = "+gamedie.getNumber()+" AND diecolor = '"+getStringFromColor(gamedie)+"';";
		try {
				Statement stmt = conn.getConn().createStatement();
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
        	Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                GameDie gameDie = new GameDie(getColorFromString(rs.getString("diecolor")),rs.getInt("dienumber"),rs.getInt("eyes"), game, conn, this);
                if(rs.getInt("roundID") != 0) {
                	gameDie.setRoundIDFromDB(rs.getInt("roundID"));
                }
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
	        	Statement stmt = conn.getConn().createStatement();
				ResultSet rs = stmt.executeQuery(query);
	            while (rs.next()) {
	                gameDie = new GameDie(getColorFromString(rs.getString("diecolor")),rs.getInt("dienumber"),rs.getInt("eyes"), game, conn, this);
	            }
	            stmt.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return gameDie;
	}
	
	public ArrayList<GameDie> getAllRoundDice(Game game) {
        ArrayList<GameDie> list = new ArrayList<GameDie>();
        String query = "SELECT * FROM gamedie WHERE idgame="+game.getGameID()+" AND roundID = "+game.getRound().get()+";";
        try {
        	Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                GameDie gameDie = new GameDie(getColorFromString(rs.getString("diecolor")),rs.getInt("dienumber"),rs.getInt("eyes"), game, conn, this);
                list.add(gameDie);
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
	
	public ArrayList<GameDie> getAllavailableDiceOfRound(Game game) {
        int roundFromGameClass = game.getRoundFromDB();
		int round;
		if(roundFromGameClass%2 == 0) {
			round = roundFromGameClass - 1;
		}
		else {
			round = roundFromGameClass;
		}
		ArrayList<GameDie> list = new ArrayList<GameDie>();
        String query = "SELECT gamedie.* FROM gamedie LEFT JOIN playerframefield ON gamedie.dienumber = playerframefield.dienumber "
        		+ "AND gamedie.idgame = playerframefield.idgame AND gamedie.diecolor = playerframefield.diecolor "
        		+ "WHERE playerframefield.dienumber IS NULL AND playerframefield.diecolor IS NULL AND roundtrack IS NULL "
        		+ "AND gamedie.idgame = "+game.getGameID()+" AND roundID = "+round+" "
        		+ "ORDER BY gamedie.diecolor, gamedie.dienumber;";
        try {
        	Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                GameDie gameDie = new GameDie(getColorFromString(rs.getString("diecolor")),rs.getInt("dienumber"),rs.getInt("eyes"), game, conn, this);
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
        String query = "SELECT * FROM gamedie WHERE idgame = "+game.getGameID()+" AND roundtrack IS NOT NULL ORDER BY roundtrack, diecolor, eyes ASC;";
        try {
        	Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                GameDie gameDie = new GameDie(getColorFromString(rs.getString("diecolor")),rs.getInt("dienumber"),rs.getInt("eyes"), game, conn, this); 
                gameDie.setAvailable(false);
                gameDie.setOnRoundTrack(rs.getInt("roundtrack"));
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
				Statement stmt = conn.getConn().createStatement();
				stmt.executeUpdate(query);
				stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<GameDie> getAllUnusedDiceOfGame(Game game) {
        ArrayList<GameDie> list = new ArrayList<GameDie>();
        String query = "SELECT * FROM gamedie WHERE idgame="+game.getGameID()+ " AND roundID is null;";
        try {
        	Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                GameDie gameDie = new GameDie(getColorFromString(rs.getString("diecolor")),rs.getInt("dienumber"),rs.getInt("eyes"), game, conn, this);
                if(rs.getInt("roundID") != 0) {
                	gameDie.setRoundIDFromDB(rs.getInt("roundID"));
                }
                list.add(gameDie);
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
	}
	
	public GameDie getUnusedDiceOfGame(Game game) {
		GameDie gameDie = null;
        String query = "SELECT * FROM gamedie WHERE idgame="+game.getGameID()+ " AND roundID is null LIMIT 1;";
        try {
        	Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                gameDie = new GameDie(getColorFromString(rs.getString("diecolor")),rs.getInt("dienumber"),rs.getInt("eyes"), game, conn, this);
                gameDie.setRoundID(game); 
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameDie;
	}
	
	public void setGameDieUnused(GameDie gamedie, Game game) {
		String query = "UPDATE gamedie SET roundID = NULL WHERE idgame = "+game.getGameID()+" AND dienumber = "+gamedie.getNumber()+" AND diecolor = '"+getStringFromColor(gamedie)+"';";
		try {
				Statement stmt = conn.getConn().createStatement();
				stmt.executeUpdate(query);
				stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeDieFromRoundTrack(GameDie selectedDieRoundTrack, Game game) {
		 String query = "UPDATE gamedie SET roundtrack = NULL WHERE dienumber = "+selectedDieRoundTrack.getNumber()+" AND eyes =  "+selectedDieRoundTrack.getEyes()+" AND diecolor ='"+selectedDieRoundTrack.getColor()+"';";
	        try {
	            Statement stmt = conn.getConn().createStatement();
	            stmt.executeUpdate(query);
	            stmt.close();
	        }catch(SQLException e) {
	            e.printStackTrace();
	        }
	}

	public void setDieOnRoundTrack(GameDie gamedie, GameDie rountrackDie, Game game) {
		String query = "UPDATE gamedie SET roundtrack = "+rountrackDie.isOnRoundTrack()+" WHERE dienumber = "+gamedie.getNumber()+" AND eyes =  "+gamedie.getEyes()+" AND diecolor ='"+gamedie.getColor()+"';";
        try {
            Statement stmt = conn.getConn().createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
		
	}
}
