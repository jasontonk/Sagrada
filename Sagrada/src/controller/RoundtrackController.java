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
	
	public void setSelectedDie(int round, int index, ArrayList<Integer> diceIDs) {
		ArrayList<GameDie> dieces = getGameController().getDiceOnRoundTrack();
		
		GameDie gameDie = null;
		
		for(int i = 0; i < dieces.size();i++ ) {
			if(dieces.get(i).getNumber() == diceIDs.get(index)) {
				gameDie = dieces.get(i);
			}
		}
	
		
		if(gameDie != null) {
			gameController.getGame().setSelectedDieRoundTrack(gameDie);
		}
		
		if (getGameController().getGame().getSelectedToolcard() != null) {
			if(getGameController().getGame().getSelectedToolcard().getId() == 5 && getGameController().getGame().getSelectedDieFromDicePool() != null) {
				getGameController().lensCutter(gameDie);
			}
		}
	}
}
