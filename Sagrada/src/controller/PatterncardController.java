package controller;

import java.util.ArrayList;

import database.DataBaseConnection;
import model.ModelColor;
import model.PatternCard;

public class PatterncardController {
  
	private ArrayList<PatternCard> patterncardOptions;
	private PatternCard patterncard;
	private GameController gameController;
	
	public PatterncardController(DataBaseConnection conn, GameController gameController) {
		this.gameController = gameController;
		System.out.println("de personalplayer is: "+ gameController.getGame().getPersonalPlayer());
		patterncard = gameController.
				getGame().
				getPersonalPlayer().
				getBoard().
				getPatternCard();
		System.out.println("patterncardcontroller patterncard = " + patterncard.getName());
		if (patterncard.getPatterncardID() == 0) {
			patterncard = new PatternCard(conn);
		}
//		patterncard.setpattern(false);
	}
	
	public PatterncardController(PatternCard patterncard) {
		this.patterncard = patterncard;
//		patterncard.setpattern(false);
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

	public boolean checkPlacementAgainstRules(int x, int y, ModelColor modelColor, int value) {
		return gameController.checkPlacementAgainstRules(x, y, modelColor, value);
	}

	public ModelColor getSelectedDieColor() {
		return gameController.getSelectedDieColor();
	}

	public int getSelectedDieValue() {
		return gameController.getSelectedDieValue();
	}
	
	public GameController getGameController() {
		return gameController;
	}
	
	public PatternCard getPatterncard() {
		return patterncard;
	}
}
  