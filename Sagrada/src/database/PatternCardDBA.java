package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.PatternCard;
import model.Player;

public class PatternCardDBA {

	private DataBaseConnection conn;
	
	public PatternCardDBA(DataBaseConnection c) {
		this.conn = c;
	}
	
	public PatternCard getPatterncard() {
        PatternCard patternCard = null;
        
        String query = "SELECT * FROM patterncard WHERE idpatterncard= "+getRandomPattercardID()+";";
        try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				patternCard = new PatternCard(rs.getString("name"), rs.getInt("difficulty"),conn);
				patternCard.setPatterncardID(rs.getInt("idpatterncard"));
			}
			stmt.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
        return patternCard;
    }

	public void addPatterncard(PatternCard patternCard ) {
		 String query = "INSERT INTO patterncard (idpattercard,difficulty,standard) VALUES("+autoIdPatternCard()+","+patternCard.getDifficulty()+",0);";
		 
		 try {
				Statement stmt = conn.createStatemant();
				stmt.executeUpdate(query);
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}	
	}
	
	private int autoIdPatternCard() {
		int patterncardid = 0;
		
		ArrayList<Integer> results = new ArrayList<Integer>();
		
		String query = "SELECT idpatterncard FROM patterncard;";
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				int idgame = rs.getInt("idpatterncard");
				results.add(idgame);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < results.size();i++) {
			if(patterncardid < results.get(i)) {
				patterncardid = results.get(i);
			}
		}
		patterncardid = patterncardid +1;
		
		return patterncardid;
	}
	
	private int getRandomPattercardID() {
		int id = 0;
		int totaalid = 0;
		String query = "SELECT idpatterncard FROM patterncard;";
        try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				totaalid++;
			}
			stmt.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
        
        id =  (int)(Math.random() * totaalid + 1);
        
        return id;
	}
	
	public PatternCard getSelectedPatterncardOfPlayer(int idplayer, Player player) {
        PatternCard patternCard = null;
        String query = "SELECT patterncard.* FROM patterncard INNER JOIN player p on patterncard.idpatterncard = p.patterncard_idpatterncard WHERE p.idplayer= "+idplayer+";";
        try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				patternCard = new PatternCard(rs.getString("name"), rs.getInt("difficulty"),conn);
				patternCard.setPatterncardID(rs.getInt("idpatterncard"));
				patternCard.setPlayer(player);
			}
			stmt.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
        return patternCard;
    }
	
	public ArrayList<PatternCard> getOptionalPatternCardsOfPlayer(int idplayer, Player player) {
		ArrayList<PatternCard> list = new ArrayList<>();
		String query = "SELECT * FROM patterncard INNER JOIN patterncardoption p on patterncard.idpatterncard = p.patterncard_idpatterncard WHERE player_idplayer= "+idplayer+";";
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				PatternCard patternCard = new PatternCard(rs.getString("name"), rs.getInt("difficulty"),conn);
				patternCard.setPatterncardID(rs.getInt("idpatterncard"));
				patternCard.setPlayer(player);
				list.add(patternCard);
			}
			stmt.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	 public void saveOptionalPatternCardsOfPlayer(ArrayList<PatternCard> optionalPatterncards, int idplayer) {
		 for (PatternCard patternCard : optionalPatterncards) {
			 String query = "INSERT INTO patterncardoption VALUES("+patternCard.getPatterncardID()+","+idplayer+");";
				
				try {
					Statement stmt = conn.createStatemant();
					stmt.executeUpdate(query);
					stmt.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}	
		 }
	 }
	 
	 public PatternCard getPatterncardByID(int id) {
	        PatternCard patternCard = null;
	        
	        String query = "SELECT * FROM patterncard WHERE idpatterncard= "+id+";";
	        try {
				Statement stmt = conn.createStatemant();
				ResultSet rs = stmt.executeQuery(query);
				if(rs.next()) {
					patternCard = new PatternCard(rs.getString("name"), rs.getInt("difficulty"),conn);
					patternCard.setPatterncardID(rs.getInt("idpatterncard"));
				}
				stmt.close();
				
			}catch (SQLException e) {
				e.printStackTrace();
			}
	        return patternCard;
	    }
}
