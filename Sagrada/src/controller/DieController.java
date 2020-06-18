package controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import database.DataBaseConnection;
import model.GameDie;
import model.ModelColor;
import view.DicePoolView;

public class DieController {

	private ArrayList<GameDie> die;
	private GameController gameController;
	private String selectedDieURL;
	
	public DieController(DataBaseConnection conn, GameController gameController) {
		this.gameController = gameController;
		die = new ArrayList<GameDie>();
		die = gameController.getDicePool();
	}
	
	
	public String getDieColor(int number) {
		String color = null;
		if(die.get(number) != null) {
			ModelColor modelColor = die.get(number).getColor();
			if(modelColor != null) {
				switch (modelColor) {
				case BLUE:
					color = "BLUE";
					break;
				case GREEN:
					color = "GREEN";
					break;
				case RED:
					color = "RED";
					break;
				case PURPLE:
					color = "PURPLE";
					break;
				case YELLOW:
					color = "YELLOW";
					break;
				default:
					color = null;
					break;
				}
			}
		}
		return color;
	}
	public int getDieValue(int number) {
		return die.get(number).getEyes();
	}

	public int getDieID(int number) {
			return die.get(number).getNumber();
	}

	public void setSelectedDieFromDiePool(int dieID, String selectedDieURL) {
		System.out.println("de die size bij het slecteren van een die : "+ die.size());
		gameController.getGame().setSelectedDieFromDicePool(die.get(dieID));
		gameController.setSelectedDie(die.get(dieID));
		this.selectedDieURL = selectedDieURL;
	}


	public String getSelectedDieURL() {
		if(selectedDieURL != null) {
		return selectedDieURL;
		}
		else {
			return null;
		}
	}


	public void deleteSelectedDie(GameDie selectedDie) {
		for(int i = 0; i < die.size(); i++) {
			if(selectedDie == die.get(i)) {
				gameController.getGameView().getDicePoolView().deleteDie(i);
				die.remove(i);
				gameController.getGame().setSelectedDieFromDicePool(null);
				gameController.getGame().setSelectedDie(null);
				break;
			}
		}
		System.out.println("de die size na het plaatsen van een die : "+ die.size());
		selectedDieURL = null;
		
	}


	public void removeAllBorders() {
		gameController.removeAllBorders();
	}

	public GameController getGameController() {
		return gameController;
	}


	public void updateDicePool(ArrayList<GameDie> offer) {
		die = offer;
		int offersize = offer.size();
		System.out.println("Die size in diecontroller: " + die.size());
		System.out.println("offer: " + offersize);
		gameController.getGameView().updateDicePoolView(offersize);
		
		
//		int index = die.size();
//		for (int i = index; i > 0; i--) {
//			System.out.println("werk dit nog");
//			gameController.getGameView().getDicePoolView().deleteDie(i);
//		}
//		gameController.getGameView().getDicePoolView().addAllDiceFromDicepool();
		
	}


}
