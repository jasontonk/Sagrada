package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.PatternCard;
import model.PatternCardField;
import model.ModelColor;

public class PatternCardFieldDBA {
	
		private DataBaseConnection conn;
		
		public PatternCardFieldDBA(DataBaseConnection c) {
			this.conn = c;
		}
		
		 public ArrayList<PatternCardField> getPatternCardFieldsOfPatterncard(PatternCard patternCard) {
			 ArrayList<PatternCardField> list = new ArrayList<>();
			 ModelColor modelColor = null;
			 String query = "SELECT * FROM patterncardfield WHERE idpatterncard= "+patternCard.getPatterncardID()+" ORDER BY position_x, position_y";
			 try {
					Statement stmt = conn.getConn().createStatement();
					ResultSet rs = stmt.executeQuery(query);
					while(rs.next()) {
						int xpos = rs.getInt("position_x");
						int ypos = rs.getInt("position_y");
						String c = rs.getString("color");
						int value = rs.getInt("value");
						if(c == null) {
							modelColor = null;
						}
						else {
							switch(c) {
							case "blue":
								modelColor = ModelColor.BLUE;
								break;
							case "green":
								modelColor = ModelColor.GREEN;
								break;
							case "purple":
								modelColor = ModelColor.PURPLE;
								break;
							case "red":
								modelColor = ModelColor.RED;
								break;
							case "yellow":
								modelColor = ModelColor.YELLOW;
								break;
							}
						}
						
						PatternCardField patternCardField = new PatternCardField(modelColor,value, ypos,xpos);
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
			 String query;			 
		
			 for(int i = 0; i < patterncardfield.size(); i++) {
				 color = null;
				 if(patterncardfield.get(i).getColor() != null) {
					 switch(patterncardfield.get(i).getColor()) {
						case BLUE:
							color = "'blue'" ;
							break;
						case GREEN:
							color = "'green'";
							break;
						case PURPLE:
							color = "'purple'";
							break;
						case RED:
							color = "'red'";
							break;
						case YELLOW:
							color = "'yellow'";
							break;
						default:
							color = null;
						}
				 }
				 if(patterncardfield.get(i).getValue() == 0) {
					 query = "INSERT INTO patterncardfield (idpatterncard, position_x, position_y, color, value) VALUES("+patterncard.getPatterncardID()+","+patterncardfield.get(i).getPositionX()+","+patterncardfield.get(i).getPositionY()+","+color+","+null+");"; 
				 }
				 else {
					 query = "INSERT INTO patterncardfield (idpatterncard, position_x, position_y, color, value) VALUES("+patterncard.getPatterncardID()+","+patterncardfield.get(i).getPositionX()+","+patterncardfield.get(i).getPositionY()+","+color+","+patterncardfield.get(i).getValue()+");";
				 }
				 try {
						Statement stmt = conn.getConn().createStatement();
						stmt.executeUpdate(query);
						stmt.close();
						
					}catch(SQLException e) {
						e.printStackTrace();
						
					}	
			 }
		 } 
		 
		 public ModelColor getColorOfField(int id, int xpos, int ypos) {
		
			 ModelColor modelColor = null;
			 String query = "SELECT color FROM patterncardfield WHERE idpatterncard= "+id+" AND position_x = "+(xpos + 1)+" AND position_y = "+(ypos + 1)+";";
			 try {
					Statement stmt = conn.getConn().createStatement();
					ResultSet rs = stmt.executeQuery(query);
					if(rs.next()) {
						
						String c = rs.getString("color");
						if(c == null) {
							modelColor = null;
						} else {
							switch(c) {
							case "blue":
								modelColor = ModelColor.BLUE;
								break;
							case "green":
								modelColor = ModelColor.GREEN;
								break;
							case "purple":
								modelColor = ModelColor.PURPLE;
								break;
							case "red":
								modelColor = ModelColor.RED;
								break;
							case "yellow":
								modelColor = ModelColor.YELLOW;
								break;
							default: modelColor = null;
							}
						}
					}
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			 return modelColor;
		 }
		 
		 public int getValueOfField(int id, int xpos, int ypos) {
			
			 int value = 0;
			 String query = "SELECT value FROM patterncardfield WHERE idpatterncard= "+id+" AND position_x = "+(xpos + 1)+" AND position_y = "+(ypos + 1)+";";
			 try {
					Statement stmt = conn.getConn().createStatement();
					ResultSet rs = stmt.executeQuery(query);
					if(rs.next()) {
						value = rs.getInt("value");
					}
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			 return value;
		 }
}
