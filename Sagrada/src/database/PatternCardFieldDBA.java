package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.PatternCard;
import model.PatternCardField;
import model.Color;

public class PatternCardFieldDBA {
	
		private DataBaseConnection conn;
		
		public PatternCardFieldDBA(DataBaseConnection c) {
			this.conn = c;
		}
		
		 public ArrayList<PatternCardField> getPatternCardFieldsOfPatterncard(PatternCard patternCard) {
			 ArrayList<PatternCardField> list = new ArrayList<>();
			 Color color = Color.BLUE;
			 String query = "SELECT * FROM patterncardfield WHERE patterncard_idpatterncard= "+patternCard.getPatterncardID()+" ORDER BY position_x, position_y";
			 try {
					Statement stmt = conn.createStatemant();
					ResultSet rs = stmt.executeQuery(query);
					while(rs.next()) {
						int xpos = rs.getInt("position_x");
						int ypos = rs.getInt("position_y");
						String c = rs.getString("color");
						int value = rs.getInt("value");
						switch(c) {
						case "blue":
							color = Color.BLUE;
							break;
						case "green":
							color = Color.GREEN;
							break;
						case "purple":
							color = Color.PURPLE;
							break;
						case "red":
							color = Color.RED;
							break;
						case "yellow":
							color = Color.YELLOW;
							break;
						}
						
						PatternCardField patternCardField = new PatternCardField(color,value, ypos,xpos);
						list.add(patternCardField);
					}
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			 return list;
		 }
		 
		 public void addPatternCardField(ArrayList<PatternCardField> patterncardfield, PatternCard patterncard){
			 String color;
			 
			 for(int i = 0; i < patterncardfield.size(); i++) {
				 
				 switch(patterncardfield.get(i).getColor()) {
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
					default:
						color = null;
					}
				 String query = "INSERT INTO patterncardfield VALUES("+patterncard.getPatterncardID()+","+patterncardfield.get(i).getPositionX()+","+patterncardfield.get(i).getPositionY()+","+color+","+patterncardfield.get(i).getValue()+");";
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
