package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.PatternCard;
import model.PatternCardField;

public class PatternCardFieldDBA {
	
		private DataBaseConnection conn;
		
		public PatternCardFieldDBA(DataBaseConnection c) {
			this.conn = c;
		}
		
		 public ArrayList<PatternCardField> getPatternCardFieldsOfPatterncard(PatternCard patternCard) {
			 ArrayList<PatternCardField> list = new ArrayList<>();
			 String query = "SELECT * FROM patterncardfield WHERE patterncard_idpatterncard= "+patternCard.getIdpatterncard()+" ORDER BY position_x, position_y";
			 try {
					Statement stmt = conn.createStatemant();
					ResultSet rs = stmt.executeQuery(query);
					while(rs.next()) {
						int xpos = rs.getInt("position_x");
						int ypos = rs.getInt("position_y");
						String color = rs.getString("color");
						int value = rs.getInt("value");
						PatternCardField patternCardField = new PatternCardField(color,value, ypos,xpos);
						list.add(patternCardField);
					}
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			 return list;
		 }
		        
}
