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
	
	public PatternCard getSelectedPatterncardOfPlayer(int idplayer, Player player) {
        PatternCard patternCard = null;
        String query = "SELECT patterncard.* FROM patterncard INNER JOIN player p on patterncard.idpatterncard = p.patterncard_idpatterncard WHERE p.idplayer= "+idplayer+";";
        try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				patternCard = new PatternCard(rs.getString("name"), rs.getInt("difficulty"));
				patternCard.setidpatterncard = (rs.getInt("idpatterncard"));
				patternCard.setstandard(rs.getBoolean("standard"));
				patternCard.setPlayer = (player);
			}
			stmt.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
	public ArrayList<PatternCard> getOptionalPatternCardsOfPlayer(int idplayer, Player player) {
		ArrayList<PatternCard> list = new ArrayList<>();
		String query = "SELECT * FROM patterncard INNER JOIN patterncardoption p on patterncard.idpatterncard = p.patterncard_idpatterncard WHERE player_idplayer= "+idplayer+";";
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				PatternCard patternCard = new PatternCard(rs.getString("name"), rs.getInt("difficulty"));
				patternCard.setIdpatterncard = (rs.getInt("idpatterncard"));
				patternCard.setstandard(rs.getBoolean("standard"));
				patternCard.setPlayer = (player);
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
			 String query = "INSERT INTO patterncardoption VALUES("+patternCard.getIdpatterncard()+","+idplayer+");";
				
				try {
					Statement stmt = conn.createStatemant();
					stmt.executeUpdate(query);
					stmt.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}	
		 }
	 }
}

