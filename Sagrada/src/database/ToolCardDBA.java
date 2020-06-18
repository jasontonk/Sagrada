package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Game;
import model.Toolcard;

public class ToolCardDBA {

	private DataBaseConnection conn;
	
	public ToolCardDBA(DataBaseConnection c) {
		this.conn = c;
	}
	
	public ArrayList<Toolcard> get3ToolcardsForAGame() {
				
//		int id1 =  (int)(Math.random() * 12 + 1);
//		int id2 = (int)(Math.random() * 12 + 1);
//		while(id2 == id1) {
//			id2 = (int)(Math.random() * 12 + 1);
//			System.out.println("while loop 1");
//		}
//		int id3 = (int)(Math.random() * 12 + 1);
//		while(id3 == id1 || id3 == id2) {
//			id3 = (int)(Math.random() * 12 + 1);
//			System.out.println("while loop 2");
//		}
		
		int id1 = 1;
		int id2 = 6;
		int id3 = 10;
		
		System.out.println(id1+ "   " + id2 + "   " + id3);
	       
		ArrayList<Toolcard> list = new ArrayList<Toolcard>();
	    String query = "SELECT * from toolcard where idtoolcard = "+id1+" OR idtoolcard = "+id2+" OR idtoolcard = "+id3+";";
	    try {
	        Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				Toolcard toolcard = new Toolcard(rs.getString("name"), rs.getString("description"),rs.getInt("idtoolcard"),conn);
				list.add(toolcard);
				System.out.println("toolcard id is"+ toolcard.getId());
			}
			stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return list;
	    }
	
	public ArrayList<Toolcard> getToolcardsFromGame(Game game) {
		   
		ArrayList<Toolcard> list = new ArrayList<Toolcard>();
	    String query = "SELECT toolcard.* FROM toolcard INNER JOIN gametoolcard g on toolcard.idtoolcard = g.idtoolcard WHERE g.idgame= "+game.getGameID()+";";
	    try {
	        Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				Toolcard toolcard = new Toolcard(rs.getString("name"), rs.getString("description"),rs.getInt("idtoolcard"),conn);
				list.add(toolcard);
			}
			stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return list;
	    }
	
	public Toolcard getToolCard(int id) {
		Toolcard toolcard = null;
	       String query = "SELECT * FROM public_objectivecard WHERE idpublic_objectivecard="+id+";";
	        try {
	        	Statement stmt = conn.getConn().createStatement();
				ResultSet rs = stmt.executeQuery(query);
				if(rs.next()) {
					toolcard = new Toolcard(rs.getString("name"),rs.getString("description"),rs.getInt("idtoolvard"),conn);
				}
				stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return toolcard;
	    }
	
	public void addToolCardToGame(Game game,Toolcard toolcard) {
			String query = "INSERT INTO gametoolcard VALUES("+autoToolcardId()+","+toolcard.getId()+","+game.getGameID()+");";
			try {
				Statement stmt = conn.getConn().createStatement();
				stmt.executeUpdate(query);
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}	
		}
	
	private int autoToolcardId() {
        int id = 0;
        String query = "SELECT MAX(gametoolcard) AS highestGameToolcardId FROM gametoolcard";
        try {
        	Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                id = rs.getInt("highestGameToolcardId") + 1;
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
