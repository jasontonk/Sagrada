package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.FavorToken;
import model.Player;

public class FavorTokenDBA {

private DataBaseConnection conn;
	
	public FavorTokenDBA(DataBaseConnection c) {
		this.conn = c;
	}
	
	public ArrayList<FavorToken> getFavortokensOfPlayer(int playerid, Player player) {
		ArrayList<FavorToken> list = new ArrayList<FavorToken>();
		String query = "SELECT * FROM gamefavortoken WHERE idplayer= "+playerid+" AND gametoolcard IS NULL;";
		
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
                FavorToken favorToken = new FavorToken(rs.getInt("idfavortoken"), player, rs.getInt("idgame"), conn); //edited to fix error
                list.add(favorToken);
            }
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		}
	
		return list;
	}
	
	private int autoIdFavorToken() {
		int favorTokenId = 0;
		String query = "SELECT MAX(idfavortoken) AS highestFavorTokenId FROM gamefavortoken;";
		
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			 if (rs.next()) {
	                favorTokenId = rs.getInt("highestFavorTokenId") + 1;
	            }
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		}
	
		return favorTokenId;
	}
	
	public boolean addFavorToken(int gameid) {
		
		int favortokenid = autoIdFavorToken();
		String query = "INSERT INTO gamefavortoken ((idfavortoken, idgame) VALUES("+favortokenid+","+gameid+");";
		
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
	
	public void setFavortokenForPlayer(int favortokenid, int playerid) {
		String query = "UPDATE gamefavortoken SET idplayer= "+playerid+" WHERE idfavortoken= "+favortokenid+";";

			try {
					Statement stmt = conn.createStatemant();
					stmt.executeUpdate(query);
					stmt.close();
				
			}catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public ArrayList<FavorToken> getUnusedFavorTokensOfGame(int gameid){//removed game to fix error
		ArrayList<FavorToken> list = new ArrayList<>();
		String query = "SELECT * FROM gamefavortoken WHERE idgame= "+gameid+" AND idplayer IS NULL;";
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
                FavorToken favorToken = new FavorToken(rs.getInt("idfavortoken"), gameid, conn);// changed to add gameid and conn to constructor
                favorToken.setGameId(gameid); //changed to setgameID to fix error
                list.add(favorToken);
            }
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		}
		return list;
	}
	
	public void setFavortokensForToolCard(int playerid, int gametoolcard, int round, int idfavortoken) {
		String query = "UPDATE gamefavortoken SET idplayer= "+playerid+", gametoolcard= "+gametoolcard+", round= "+round+" WHERE idfavortoken="+idfavortoken+";";
		try {
			Statement stmt = conn.createStatemant();
			stmt.executeUpdate(query);
			stmt.close();
		}catch (SQLException e) {
		e.printStackTrace();
		}
	}
}
