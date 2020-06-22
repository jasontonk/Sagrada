package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import model.Game;
import model.Toolcard;

public class ToolCardDBA {

	private DataBaseConnection conn;
	private Random random;
	private ArrayList<Integer> toolcardIDS;
	
	public ToolCardDBA(DataBaseConnection c) {
		this.conn = c;
		random = new Random();
		toolcardIDS = new ArrayList<Integer>();
	}
	
	public ArrayList<Toolcard> get3ToolcardsForAGame() {
		
		toolcardIDS.clear();
		int randomid = random.nextInt((11- 1)+1)+1; 
		int counter = 0;
		while(counter < 3) {
			if(!toolcardIDS.contains(randomid) && (randomid == 1 || randomid == 2 || randomid == 5
					|| randomid == 6 || randomid == 10 || randomid == 11)) {
					
					toolcardIDS.add(randomid);
					counter++;
					
				}else {
					randomid = random.nextInt((11- 1)+1)+1; 
			}
		}
		
		
		int id1 = toolcardIDS.get(0);
		int id2 = toolcardIDS.get(1);
		int id3 = toolcardIDS.get(2);

		
		ArrayList<Toolcard> list = new ArrayList<Toolcard>();
	    String query = "SELECT * from toolcard where idtoolcard = "+id1+" OR idtoolcard = "+id2+" OR idtoolcard = "+id3+";";
	    try {
	        Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				Toolcard toolcard = new Toolcard(rs.getString("name"), rs.getString("description"),rs.getInt("idtoolcard"), conn);
				list.add(toolcard);
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
					toolcard = new Toolcard(rs.getString("name"),rs.getString("description"),rs.getInt("idtoolcard"),conn);
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
