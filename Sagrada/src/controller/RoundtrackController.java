package controller;

import model.Game;
import model.GameDie;
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
	
	public void setSelectedDie(int round, int index) {
		
		GameDie gameDie = gameController.
				getGame().
				getRoundTrack().
				getRoundTrackField(round-1).
				getDie(index);
			 System.out.println("GAMEDIE IS MISSCHIEN GESELECTEERD");
			 
		if(gameDie != null) {
			System.out.println("GAMEDIE IS GESELECTEERD");
			gameController.setSelectedDie(gameDie);
		}
	}

}
