package database;

import java.sql.SQLException;
import java.sql.Statement;
import model.GameDie;
import model.Player;

public class PlayerFrameFieldDBA {
	
	private DataBaseConnection conn;
	
	public PlayerFrameFieldDBA(DataBaseConnection c) {
		this.conn = c;
	}
	
	public void addPlayerFrameField(Player player, int xposition, int yposition) {
		// TODO list van fields.

		String query = "INSERT INTO playerframefield(idplayer, position_x, position_y, idgame) VALUES("+player.getId()+","+xposition+","+xposition+
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
			String query = "UPDATE playerframefield SET dienumber = "+die.getNumber()+", diecolor= '" +color+"' WHERE idplayer = "+player.getId()+" AND position_x = "+xposition+" AND position_y ="+yposition+";";
			try {
				Statement stmt = conn.getConn().createStatement();
				stmt.executeUpdate(query);
				stmt.close();
			
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	public void removeDie(GameDie die, Player player, int xposition, int yposition){
		String color = null;
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
			String query = "UPDATE playerframefield SET dienumber = "+die.getNumber()+", diecolor= '" +color+"' WHERE idplayer = "+player.getId()+" AND position_x = "+xposition+" AND position_y ="+yposition+";";
			try {
				Statement stmt = conn.getConn().createStatement();
				stmt.executeUpdate(query);
				stmt.close();
			
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
}
