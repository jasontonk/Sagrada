package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Game;
import model.PublicObjectiveCard;

public class PublicObjectiveCardDBA {

	private DataBaseConnection conn;
	
	public PublicObjectiveCardDBA(DataBaseConnection c) {
		this.conn = c;
	}
	
	public ArrayList<PublicObjectiveCard> getAllPublicObjectiveCards() {
       ArrayList<PublicObjectiveCard> list = new ArrayList<PublicObjectiveCard>();
       String query = "SELECT * FROM public_objectivecard";
        try {
        	Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				PublicObjectiveCard publicObjectiveCard = new PublicObjectiveCard(rs.getString("name"),rs.getString("description"),rs.getInt("points"),rs.getInt("idpublic_objectivecard"));
				list.add(publicObjectiveCard);
			}
			stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
	
	public ArrayList<PublicObjectiveCard> getAllPublicObjectiveCardsFromGame(Game game) {
	       ArrayList<PublicObjectiveCard> list = new ArrayList<PublicObjectiveCard>();
	       String query = "SELECT public_objectivecard.* FROM public_objectivecard INNER JOIN gameobjectivecard_public s on public_objectivecard.idpublic_objectivecard = s.idpublic_objectivecard WHERE s.idgame="+game.getGameID()+";";
	        try {
	        	Statement stmt = conn.getConn().createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()) {
					PublicObjectiveCard publicObjectiveCard = new PublicObjectiveCard(rs.getString("name"),rs.getString("description"),rs.getInt("points"),rs.getInt("idpublic_objectivecard"));
					list.add(publicObjectiveCard);
				}
				stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return list;
	    }
	
	public void addPublicObjectiveCardToGame(PublicObjectiveCard publicObjectiveCard, Game game) {
		
		String query = "INSERT INTO gameobjectivecard_public VALUES("+game.getGameID()+","+publicObjectiveCard.getId()+");";
		try {
			Statement stmt = conn.getConn().createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public PublicObjectiveCard getPublicObjectiveCard(int id) {
		PublicObjectiveCard publicObjectiveCard = null;
	       String query = "SELECT * FROM public_objectivecard WHERE idpublic_objectivecard="+id+";";
	        try {
	        	Statement stmt = conn.getConn().createStatement();
				ResultSet rs = stmt.executeQuery(query);
				if(rs.next()) {
					publicObjectiveCard = new PublicObjectiveCard(rs.getString("name"),rs.getString("description"),rs.getInt("points"),id);
				}
				stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return publicObjectiveCard;
	    }
	
	
}
