package controller;

import model.Game;
import model.GameDie;
import model.RoundTrack;

public class RoundtrackController {

	private RoundTrack roundtrack;
	private GameController gameController;
	
	public RoundtrackController(Game game, GameController gameController) {
		roundtrack = new RoundTrack(game); 
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
		
		if(gameDie != null) {
			gameController.setSelectedDie(gameDie);
		}
	}

}
