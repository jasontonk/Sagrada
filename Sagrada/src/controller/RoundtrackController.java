package controller;

import java.util.ArrayList;

import model.Game;
import model.GameDie;

public class RoundtrackController {

	private GameController gameController;
	
	public RoundtrackController(Game game, GameController gameController) {
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
		
		System.out.println("nnumber= "+ gameDie.getEyes()+ " ccolor= "+gameDie.getColorString());

		if(gameDie != null) {
			gameController.getGame().setSelectedDieRoundTrack(gameDie);
		}
		
		if (getGameController().getGame().getSelectedToolcard() != null) {
			if(getGameController().getGame().getSelectedToolcard().getId() == 5 && getGameController().getGame().getSelectedDieFromDicePool() != null) {
				System.out.println("number= "+ gameDie.getEyes()+ " color= "+gameDie.getColorString());
				getGameController().lensCutter(gameDie);
			}
		}
	}
}
