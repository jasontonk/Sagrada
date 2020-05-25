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



	public void addPlayer(Player player) {
		int playerid = autoIdPlayer();
		player.setId(playerid);
	
		String query = "INSERT INTO player (idplayer,username,idgame,playstatus,private_objectivecard_color) VALUES("
				+ playerid + ",'" + player.getName() + "'," + player.getGame().getGameID() + ",'"
				+ player.getPlayerStatus() + "','" + getStringFromColor(player) + "');";


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
		if (player.getColor() != null) {
			switch (player.getColor()) {
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
			}
		}

		return playerStatus;
	}

	private PlayerStatus getPlayerStatusFromString(String status) {
		PlayerStatus playerStatus = null;
		if (status != null) {
			switch (status) {
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

	public void setPlayerStatus(Player player) {

		String query = "UPDATE player SET playstatus = '" + player.getPlayerStatus() + "' WHERE idplayer = "
				+ player.getId() + ";";
		try {
			Statement stmt = conn.getConn().createStatement();
			stmt.executeUpdate(query);
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setScore(Player player) {
		String query = "UPDATE player SET score = " + player.getScore() + " WHERE idplayer = " + player.getId() + ";";
		try {
			Statement stmt = conn.getConn().createStatement();
			stmt.executeUpdate(query);
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
				player.setColor(getColorFromString(rs.getString("private_objectivecard_color")));
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

}
