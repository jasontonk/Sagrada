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
				PublicObjectiveCard publicObjectiveCard = new PublicObjectiveCard(rs.getInt("idpublic_objectivecard"),rs.getString("name"),rs.getString("description"),rs.getInt("points"));
				list.add(publicObjectiveCard);
			}
			stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
	
	public ArrayList<PublicObjectiveCard> getAllAvailablePublicObjectiveCards(Game game) {
	       ArrayList<PublicObjectiveCard> list = new ArrayList<PublicObjectiveCard>();
	       String query = "SELECT DISTINCt(public_objectivecard.idpublic_objectivecard), name, description, points FROM public_objectivecard "+
	       					"LEFT JOIN gameobjectivecard_public " + 
	    		   			"ON public_objectivecard.idpublic_objectivecard = gameobjectivecard_public.idpublic_objectivecard " + 
	    		   			"WHERE idgame != "+ game.getGameID() +" OR idgame IS NULL;";
	        try {
	        	Statement stmt = conn.getConn().createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()) {
					PublicObjectiveCard publicObjectiveCard = new PublicObjectiveCard(rs.getInt("idpublic_objectivecard"),rs.getString("name"),rs.getString("description"),rs.getInt("points"));
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
					PublicObjectiveCard publicObjectiveCard = new PublicObjectiveCard(rs.getInt("idpublic_objectivecard"),rs.getString("name"),rs.getString("description"),rs.getInt("points"));
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
					publicObjectiveCard = new PublicObjectiveCard(id,rs.getString("name"),rs.getString("description"),rs.getInt("points"));
				}
				stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return publicObjectiveCard;
	    }

	public ArrayList<PublicObjectiveCard> getPublicObjectiveCardsOfGame(Game game) {
		ArrayList<PublicObjectiveCard> publicObjectiveCards = new ArrayList<>();
	       String query = "SELECT * FROM gameobjectivecard_public LEFT JOIN public_objectivecard "
	       		+ "ON gameobjectivecard_public.idpublic_objectivecard = public_objectivecard.idpublic_objectivecard "
	       		+ "WHERE idgame = "+game.getGameID()+";";
	        try {
	        	Statement stmt = conn.getConn().createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()) {

					PublicObjectiveCard publicObjectiveCard = new PublicObjectiveCard(rs.getInt("idpublic_objectivecard"),rs.getString("name"),rs.getString("description"),rs.getInt("points"));
					publicObjectiveCards.add(publicObjectiveCard);
				}
				stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return publicObjectiveCards;
	}	
}
