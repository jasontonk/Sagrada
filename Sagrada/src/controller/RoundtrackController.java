package controller;

import model.Game;
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

}
