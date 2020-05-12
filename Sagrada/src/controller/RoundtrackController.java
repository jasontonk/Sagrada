package controller;

import model.Game;
import model.RoundTrack;

public class RoundtrackController {

	private RoundTrack roundtrack;
	
	public RoundtrackController(Game game) {
		roundtrack = new RoundTrack(game); 
	}
	public int getRound() {
		return roundtrack.getCurrentRound();
	}

}
