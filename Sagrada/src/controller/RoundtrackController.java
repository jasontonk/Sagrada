package controller;

import java.util.ArrayList;

import model.Game;
import model.GameDie;
import model.ModelColor;
import model.RoundTrack;

public class RoundtrackController {

	private RoundTrack roundtrack;
	private GameController gameController;
	
	public RoundtrackController(Game game, GameController gameController) {
		roundtrack = game.getRoundTrack();
		this.gameController = gameController;
	}
	
	public GameController getGameController() {
		return gameController;
	}
	
	public void setSelectedDie(int round, int index,GameDie gameDie) {
			 
		if(gameDie != null) {
			System.out.println("Ik heb RoundTrackDie : " + gameDie.getEyes()+ " And Color= " + gameDie.getColorString());
			gameController.getGame().setSelectedDieRoundTrack(gameDie);
		}
	}
}
