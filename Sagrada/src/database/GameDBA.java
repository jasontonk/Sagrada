package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

import model.Game;
import model.Player;

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
		String query = "SELECT current_roundID FROM game WHERE idgame =" +gameid+";"; 
		try {
			Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
                currentRound = rs.getInt("current_roundID");
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
		
		String query = "UPDATE game SET current_roundID = "+(game.getRound().get()+1)+" WHERE idgame = "+game.getGameID()+";";
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
		String query = "UPDATE game SET current_roundID = "+(game.getRound().get()+1)+" WHERE idgame = "+game.getGameID()+";";
		try {
				Statement stmt = conn.getConn().createStatement();
				stmt.executeUpdate(query);
				stmt.close();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public String getNameOfChallengerOfGameWithID(int gameID) {
		String name = "";
		String query = "SELECT username FROM player WHERE idgame = " + gameID + " AND playstatus = 'CHALLENGER';";
		try {
				Statement stmt = conn.getConn().createStatement();
				ResultSet rs = stmt.executeQuery(query);
				if (rs.next()) {
	                name = rs.getString("username");
	            }
				stmt.close();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}
	public Player getChallengerOfGameWithID(int gameID,Game game) {
		Player player = new Player(conn,game);
		String query = "SELECT idplayer FROM player WHERE idgame = " + gameID + " AND playstatus = 'CHALLENGER';";
		try {
				Statement stmt = conn.getConn().createStatement();
				ResultSet rs = stmt.executeQuery(query);
				if (rs.next()) {
	                PlayerDBA playerDBA = new PlayerDBA(conn);
					player = playerDBA.getPlayerUsingID(rs.getInt("idplayer"), game);
	            }
				stmt.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return player;
	}
	
	
	 public ArrayList<Player> getPlayersOfGame(Game game) {
	        PlayerDBA playerDBA = new PlayerDBA(conn);
	        ArrayList<Player> players = new ArrayList<>();
	        String query = "SELECT idplayer FROM player WHERE idgame = " + game.getGameID();
	        
	        try {
	        	Statement stmt = conn.getConn().createStatement();
				ResultSet rs = stmt.executeQuery(query);
	            while (rs.next()) {
	                int playerId = rs.getInt("idplayer");
	                Player player = playerDBA.getPlayerById(playerId,game);
	                player.setGame(game);
	                players.add(player);
	            }
	            rs.close();
	        }catch (Exception e) {
	            e.printStackTrace();
	        }
	        return players;
	    }
	 public  ArrayList<String> getWinnerOfGameUsingGameID(Game game) {
         ArrayList<String> winnerInfo = new ArrayList<>();
         String query = "SELECT idplayer, score, username FROM player WHERE idgame = " + game.getGameID() + " ORDER BY score DESC limit 1;";

            try {
                Statement stmt = conn.getConn().createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    winnerInfo.add(rs.getString("username"));
                    winnerInfo.add(Integer.toString(rs.getInt("score")));
                }
                rs.close();
            }catch (Exception e) {
                e.printStackTrace();
            }


         return winnerInfo;
     }

	public Player getCurrentPlayer(Game game) {
		Player currentplayer = null;
		ArrayList<Player> players = game.getPlayers();
	    String query = "SELECT player.* FROM game RIGHT JOIN player " + 
	    				"ON game.idgame = player.idgame AND game.turn_idplayer = player.idplayer " + 
	    				"WHERE game.idgame= "+game.getGameID()+";";
	        
	        try {
				Statement stmt = conn.getConn().createStatement();
				ResultSet rs = stmt.executeQuery(query);
				if(rs.next()) {
					for (Player player : players) {
						if(player.getId() == rs.getInt("idplayer")) {
							currentplayer = player;
							break;
						}
					}
				}
				stmt.close();
				
			}catch (SQLException e) {
				e.printStackTrace();
			}
	        return currentplayer;
	}
	 
}
