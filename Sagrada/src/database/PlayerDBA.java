package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.ModelColor;
import model.Account;
import model.Game;
import model.PatternCard;
import model.Player;
import model.PlayerStatus;

public class PlayerDBA {

	private DataBaseConnection conn;
	private GameDBA game;

	public PlayerDBA(DataBaseConnection c) {
		this.conn = c;
		game = new GameDBA(c);
	}



	public void addPlayer(Player player, PlayerStatus playerStatus) {
		int playerid = autoIdPlayer();
		player.setId(playerid);
	
		String query = "INSERT INTO player (idplayer,username,idgame,playstatus,private_objectivecard_color) VALUES("
				+ playerid + ",'" + player.getName() + "'," + player.getGame().getGameID() + ",'"
				+ playerStatus + "','" + getStringFromColor(player) + "');";


		try {
			Statement stmt = conn.getConn().createStatement();
			stmt.executeUpdate(query);
			stmt.close();

		} catch (SQLException e) {

			e.printStackTrace();

		}

	}

	private String getStringFromColor(Player player) {
		String color = null;
		if (player.getPersonalObjectiveCardColor() != null) {
			switch (player.getPersonalObjectiveCardColor()) {
			case BLUE:
				color = "blue";
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

	private ModelColor getColorFromString(String c) {
		ModelColor modelColor = null;
		if (c != null) {
			switch (c) {
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
		}
		return modelColor;
	}

	private String getStringFromPlayerStatus(Player player) {
		String playerStatus = null;
		if (player.getPlayerStatus() != null) {
			switch (player.getPlayerStatus()) {
			case ACCEPTED:
				playerStatus = "accepted";
				break;
			case CHALLENGEE:
				playerStatus = "challengee";
				break;
			case CHALLENGER:
				playerStatus = "challenger";
				break;
			case FINISHED:
				playerStatus = "finished";
				break;
			case REFUSED:
				playerStatus = "refused";
				break;
			case START:
				playerStatus = "start";
				break;
			case INGAME:
				playerStatus = "ingame";
				break;
			}
			
		}

		return playerStatus;
	}

	private PlayerStatus getPlayerStatusFromString(String status) {
		
		PlayerStatus playerStatus = null;
		if (status != null) {
			switch (status.toLowerCase()) {
			case "accepted":
				playerStatus = PlayerStatus.ACCEPTED;
				break;
			case "challengee":
				playerStatus = PlayerStatus.CHALLENGEE;
				break;
			case "challenger":
				playerStatus = PlayerStatus.CHALLENGER;
				break;
			case "finished":
				playerStatus = PlayerStatus.FINISHED;
				break;
			case "refused":
				playerStatus = PlayerStatus.REFUSED;
				break;
			case "start":
				playerStatus = PlayerStatus.START;
				break;
			case "ingame":
				playerStatus = PlayerStatus.INGAME;
				break;
				
			default:
				playerStatus = null;
			}
		}
		return playerStatus;
	}

	private int autoIdPlayer() {

		int playerid = 0;
		ArrayList<Integer> results = new ArrayList<Integer>();

		String query = "SELECT idplayer FROM player;";
		try {
			Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int idgame = rs.getInt("idplayer");
				results.add(idgame);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < results.size(); i++) {
			if (playerid < results.get(i)) {
				playerid = results.get(i);
			}
		}
		playerid = playerid + 1;
		return playerid;
	}

	public void setPlayerStatus(Player player, PlayerStatus status) {

//		String query = "UPDATE player SET playstatus = '" + player.getPlayerStatus() + "' WHERE idplayer = "
//				+ player.getId() + ";";
		String query = "UPDATE player SET playstatus = '" + status + "' WHERE idplayer = "
				+ player.getId() + ";";
		try {
			Statement stmt = conn.getConn().createStatement();
			stmt.executeUpdate(query);
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setScore(Player player, int score) {
		String query = "UPDATE player SET score = " + score + " WHERE idplayer = " + player.getId() + ";";
		try {
			Statement stmt = conn.getConn().createStatement();
			stmt.executeUpdate(query);
			System.out.println("========= SCORE GEUPDATE IN DATABASE");
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Player getPlayerUsingID(int idplayer) {
		

		Player player = new Player(conn);
		String query = "SELECT * FROM player WHERE idplayer = " + idplayer + ";";
		try {
			Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				PatternCardDBA patternCard = new PatternCardDBA(conn);
				
				GameDBA game = new GameDBA(conn);
				AccountDBA account = new AccountDBA(conn);
				player.setAccount(account.GetAccountDB(rs.getString("username")));
				player.setName(rs.getString("username"));
				player.setId(idplayer);
				
				player.setPlayerStatus(getPlayerStatusFromString(rs.getString("playstatus")));
				
				player.setSequenceNumber(rs.getInt("seqnr"));
				player.setScore(rs.getInt("score"));
				player.setPersonalObjectiveCardColorFromDB(getColorFromString(rs.getString("private_objectivecard_color")));
				player.setPatternCard(patternCard.getPatterncardByID(rs.getInt("idpatterncard")));
				player.setGame(game.getGameByID(rs.getInt("idgame")));
			}
			
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return player;
	}
	

	public ArrayList<Player> getPlayersOfAccount(Account account) {
		ArrayList<Player> list = new ArrayList<>();
		
    	
		String username = "'" + account.getUsername() + "'";
		System.out.println("'" + account.getUsername() + "'");
//		String username = "'test10'";
		String query = "SELECT * FROM player WHERE username = "+username;
		try {
			Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Player player = new Player(conn);
				player.setAccount(account);
				PatternCardDBA patternCard = new PatternCardDBA(conn);
				player.setName(rs.getString("username"));
				player.setId(rs.getInt("idplayer"));
				player.setPlayerStatus(getPlayerStatusFromString(rs.getString("playstatus")));
				player.setSequenceNumber(rs.getInt("seqnr"));
				player.setScore(rs.getInt("score"));
				player.setPersonalObjectiveCardColorFromDB(getColorFromString(rs.getString("private_objectivecard_color")));
				player.setPatternCard(patternCard.getPatterncardByID(rs.getInt("idpatterncard")));
				player.setGame(game.getGameByID(rs.getInt("idgame")));
				list.add(player);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Player getPlayerUsingSeqnrAndGame(int seqnr, Game game) {

		Player player = new Player(conn, null, game, null);
		String query = "SELECT * FROM player WHERE seqnr= " + seqnr + " AND idgame=" + game.getGameID() + ";";
		try {
			Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				PatternCardDBA patternCard = new PatternCardDBA(conn);
				AccountDBA account = new AccountDBA(conn);
				GameDBA g = new GameDBA(conn);
				player.setAccount(account.GetAccountDB(rs.getString("username")));
				player.setName(rs.getString("username"));
				player.setId(rs.getInt("idplayer"));
				player.setPlayerStatus(getPlayerStatusFromString(rs.getString("playstatus")));
				player.setSequenceNumber(rs.getInt("seqnr"));
				player.setScore(rs.getInt("score"));
				player.setPersonalObjectiveCardColorFromDB(getColorFromString(rs.getString("private_objectivecard_color")));
				player.setPatternCard(patternCard.getPatterncardByID(rs.getInt("idpatterncard")));
				player.setGame(g.getGameByID(rs.getInt("idgame")));
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return player;
	}

	public void setPlayerSeqNumber(int seqnr, Player player) {

		String query = "UPDATE player SET seqnr = " + seqnr + " WHERE idplayer = " + player.getId() + ";";
		try {
			Statement stmt = conn.getConn().createStatement();
			stmt.executeUpdate(query);
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getPlayerSeqNumber(Player player) {

		int seqnr = 0;
		String query = "SELECT * FROM player WHERE idplayer= " + player.getId() + ";";
		try {
			Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				seqnr = rs.getInt("seqnr");
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return seqnr;
		
		//139|140|140|139 139|140
	}

	public void setPlayerPatternCard(PatternCard patternCard, Player player) {

		String query = "UPDATE player SET idpatterncard = " + patternCard.getPatterncardID() + " WHERE idplayer = "
				+ player.getId() + ";";
		try {
			Statement stmt = conn.getConn().createStatement();
			stmt.executeUpdate(query);
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	public PlayerStatus getPlayerStatusFromDB(Player playerUsingID) {
		String playerstatusString = null;
		String query = "SELECT playstatus FROM player WHERE idplayer= " + playerUsingID.getId() + ";";
		try {
			Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				playerstatusString = rs.getString("playstatus");
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getPlayerStatusFromString(playerstatusString);
	}



	public ArrayList<Player> getChallengeePlayers(Account account) {
			ArrayList<Player> list = new ArrayList<>();
		
    	
		String username = "'" + account.getUsername() + "'";
		System.out.println("'" + account.getUsername() + "'");

		String query = "SELECT * FROM player WHERE username = "+username+" AND playstatus = 'CHALLENGEE';";
		try {
			Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Player player = new Player(conn);
				player.setAccount(account);
				PatternCardDBA patternCard = new PatternCardDBA(conn);
				player.setName(rs.getString("username"));
				player.setId(rs.getInt("idplayer"));
				player.setPlayerStatus(getPlayerStatusFromString(rs.getString("playstatus")));
				player.setSequenceNumber(rs.getInt("seqnr"));
				player.setScore(rs.getInt("score"));
				player.setPersonalObjectiveCardColorFromDB(getColorFromString(rs.getString("private_objectivecard_color")));
				player.setPatternCard(patternCard.getPatterncardByID(rs.getInt("idpatterncard")));
				player.setGame(game.getGameByID(rs.getInt("idgame")));
				list.add(player);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int getScoreFromDB(Player player) {
		int playerscore = 0;
		String query = "SELECT score FROM player WHERE idplayer= " + player.getId() + ";";
		try {
			Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				playerscore = rs.getInt("score");
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return playerscore;
	}



	public Player getPlayerById(int id) {
        Player player = new Player(conn);
        String query = "SELECT * FROM player WHERE idplayer = " + id + ";";
        
        try {
        	Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                AccountDBA accountDBA = new AccountDBA(conn);
                Account account = accountDBA.GetAccountDB(rs.getString("username"));
                player.setId(rs.getInt("idplayer"));
                player.setPlayerStatus(getPlayerStatusFromString(rs.getString("playstatus")));
                player.setPersonalObjectiveCardColorFromDB(getColorFromString(rs.getString("private_objectivecard_color")));
                player.setSequenceNumber(rs.getInt("seqnr"));
//                player.setCurrentPlayer(rs.getBoolean("isCurrentPlayer"));
                player.setAccount(account);
            }
        } catch (Exception e) {
            player = null;
            e.printStackTrace();
        }
        return player;
    }


}
