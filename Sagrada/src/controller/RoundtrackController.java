package controller;

import java.util.ArrayList;

import model.Game;
import model.GameDie;
import model.ModelColor;

public class RoundtrackController {

	private GameController gameController;
	
	public RoundtrackController(Game game, GameController gameController) {
		this.gameController = gameController;
	}
	
	public GameController getGameController() {
		return gameController;
	}
	
	public void setSelectedDie(int round, ArrayList<ModelColor> colors, int index, ArrayList<Integer> diceIDs) {
		ArrayList<GameDie> dices = getGameController().getDiceOnRoundTrack();
		
		GameDie gameDie = null;
		
		for(int i = 0; i < dices.size();i++ ) {
			if(dices.get(i).getNumber() == diceIDs.get(index) && dices.get(i).getColor().equals(colors.get(index))) {
				gameDie = dices.get(i);
				break;
			}
		}
		
		System.out.println("number= "+ gameDie.getEyes()+ " color= "+gameDie.getColorString());

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
