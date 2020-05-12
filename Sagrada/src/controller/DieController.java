package controller;

import database.DataBaseConnection;
import model.Die;
import model.Game;
import model.GameDie;
import model.ModelColor;

public class DieController {

	private GameDie[] die;
	private Game game;
	
	public DieController(DataBaseConnection conn) {
//		this.game = game;
//		die = game.getDie();
		game =  new Game(26);
		die = game.getDicePool();
	}
	
	
	public String getDieColor(int number) {
		String color;
		ModelColor modelColor = die[number].getColor();
		if(modelColor != null) {
			switch (modelColor) {
			case BLUE:
				color = "Blue";
				break;
			case GREEN:
				color = "Green";
				break;
			case RED:
				color = "Red";
				break;
			case PURPLE:
				color = "Purple";
				break;
			case YELLOW:
				color = "Yellow";
				break;
			default:
				color = null;
				break;
			}
			return color;
		}
		else {
			return null;
		}
	}
	public int getDieValue(int number) {
		
		return die[number].getEyes();
	}
}
