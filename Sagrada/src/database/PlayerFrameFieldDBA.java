package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.BoardField;
import model.GameDie;
import model.Player;

public class PlayerFrameFieldDBA {
	
	private DataBaseConnection conn;
	
	public PlayerFrameFieldDBA(DataBaseConnection c) {
		this.conn = c;
	}
	
	public void addPlayerFrameField(Player player, int xposition, int yposition) {

		String query = "INSERT INTO playerframefield(idplayer, position_x, position_y, idgame) VALUES("+player.getId()+","+(xposition+1)+","+(yposition+1)+
				","+player.getGame().getGameID()+");";
		
		try {
			Statement stmt = conn.getConn().createStatement();
			stmt.executeUpdate(query);
			stmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void setDie(GameDie die, Player player, int xposition, int yposition){
		String color = null;
		if(die != null) {
			if(die.getColor() != null) {
				switch(die.getColor()) {
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
				String query = "UPDATE playerframefield SET dienumber = "+die.getNumber()+", diecolor= '" +color+"' WHERE idplayer = "+player.getId()+" AND position_x = "+xposition+" AND position_y ="+yposition+ " AND idgame = "+player.getGame().getGameID()+";";
				
				try {
					Statement stmt = conn.getConn().createStatement();
					stmt.executeUpdate(query);
					stmt.close();
				
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}
	}
	
	public void removeDie(GameDie die, Player player, int xposition, int yposition){
			String query = "UPDATE playerframefield SET dienumber = NULL, diecolor= NULL WHERE idplayer = "+player.getId()+" AND position_x = "+xposition+" AND position_y ="+yposition+";";
			try {
				Statement stmt = conn.getConn().createStatement();
				stmt.executeUpdate(query);
				stmt.close();
			
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

	public BoardField getPlayerFrameField(Player player,int x, int y) {
		BoardField boardfield = null;
		String query = "SELECT position_x, position_y, playerframefield.dienumber, gamedie.dienumber, gamedie.eyes, gamedie.diecolor "
				+ "FROM playerframefield LEFT JOIN gamedie ON playerframefield.diecolor = gamedie.diecolor "
				+ "AND playerframefield.dienumber = gamedie.dienumber "
				+ "WHERE idplayer = "+player.getId()+" AND playerframefield.idgame = "+player.getGame().getGameID()+" AND position_x = "+(x + 1)+" AND position_y = "+(y + 1)+";";
		try {
			Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				boardfield = new BoardField(rs.getInt("position_x") - 1,rs.getInt("position_y") - 1);
				if(rs.getInt("playerframefield.dienumber") != 0) {
					GameDie die = new GameDie(player.getGame().getGameDieDBA().getColorFromString(rs.getString("gamedie.diecolor")), 
							rs.getInt("gamedie.dienumber"), rs.getInt("gamedie.eyes"), player.getGame(), conn, player.getGame().getGameDieDBA());
					boardfield.setDie(die);
				}
			}
			stmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return boardfield;
	}
}
