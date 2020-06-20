package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.FavorToken;
import model.Game;
import model.Player;
import model.Toolcard;

public class FavorTokenDBA {

private DataBaseConnection conn;
	
	public FavorTokenDBA(DataBaseConnection c) {
		this.conn = c;
	}
	
	public ArrayList<FavorToken> getFavortokensOfPlayer(Player player) {
		ArrayList<FavorToken> list = new ArrayList<FavorToken>();
		int playerid = player.getId();
		String query = "SELECT * FROM gamefavortoken WHERE idplayer= "+playerid+" AND gametoolcard IS NULL;";
		
		try {
			Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
                FavorToken favorToken = new FavorToken(player, rs.getInt("idgame"), conn); //edited to fix error
                list.add(favorToken);
            }
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		}
	
		return list;
	}
	
	public int getFavortokensOfToolcard(Toolcard toolcard) {
		int index = 0;
		String query = "SELECT * FROM gamefavortoken WHERE gametoolcard= "+toolcard.getId()+" ;";
		
		try {
			Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
                index ++;
            }
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		}
	
		return index;
	}
	
	private int autoIdFavorToken() {
		int favorTokenId = 0;
		String query = "SELECT MAX(idfavortoken) AS highestFavorTokenId FROM gamefavortoken;";
		
		try {
			Statement stmt = conn.getConn().createStatement();
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
	
	public boolean addFavorToken(int gameid, int playerid) {
		
		int favortokenid = autoIdFavorToken();
		String query = "INSERT INTO gamefavortoken (idfavortoken, idgame, idplayer) VALUES("+favortokenid+","+gameid+","+playerid+");";
		
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
	
	public void setFavortokenForPlayer(int favortokenid, int playerid) {
		String query = "UPDATE gamefavortoken SET idplayer= "+playerid+" WHERE idfavortoken= "+favortokenid+";";

			try {
					Statement stmt = conn.getConn().createStatement();
					stmt.executeUpdate(query);
					stmt.close();
				
			}catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public void setFavortokensForToolCard(int playerid, int gametoolcard, Game game, int idfavortoken) {
		String query = "UPDATE gamefavortoken SET idplayer = "+playerid+", gametoolcard = "+gametoolcard+", roundID = "+game.getRoundFromDB()+" WHERE idfavortoken ="+idfavortoken+" And idgame = "+game.getGameID()+";";
		try {
			Statement stmt = conn.getConn().createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		}catch (SQLException e) {
		e.printStackTrace();
		}
	}
}
