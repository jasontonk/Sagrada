package controller;

import database.DataBaseConnection;
import model.Game;
import model.RoundTrack;

public class RoundtrackController {

	private RoundTrack roundtrack;
	GameController gameController;
	
	public RoundtrackController(Game game, GameController gameController) {
		roundtrack = new RoundTrack(game); 
		this.gameController = gameController;
	}
	public int getRound() {
		return gameController.getCurrentRound();
	}

}
