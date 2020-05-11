package controller;

import database.DataBaseConnection;
import model.ModelColor;
import model.PatternCard;

public class PatterncardController {
  
	PatternCard patterncard;
	public PatterncardController(DataBaseConnection conn) {
		patterncard = new PatternCard(conn); 
		patterncard.setpattern(true);
	}
	
	public ModelColor getFieldColor(int x, int y){
		return patterncard.getFieldColor(x, y);
	}
	
	public int getFieldValue(int x, int y){
		return patterncard.getFieldValue(x, y);
	}
	
	public String getName(){
		return patterncard.getName();
		
	}
	
	public int getDifficulty(){
		return patterncard.getDifficulty();
	}
}
  