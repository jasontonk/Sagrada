package controller;

import database.DataBaseConnection;
import model.ModelColor;
import model.PatternCard;

public class PatterncardController {
  
	PatternCard patterncard;
	GameController gameController;
	
	public PatterncardController(DataBaseConnection conn, GameController gameController) {
		this.gameController = gameController;
		patterncard = new PatternCard(conn); 
		patterncard.setpattern(false);
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

	public void getSelectedDie() {
		gameController.getSelectedDie();
		
	}

	public String getSelectedDieUrl() {
		return gameController.getDieController().getSelectedDieURL();
		
	}

	public void deleteDieFromPool() {
		gameController.deleteSelectedDie();
		
	}
}
  