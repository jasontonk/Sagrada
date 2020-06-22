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
	
	public void setSelectedDie(int round, ModelColor color, int index, int diceID) {
		ArrayList<GameDie> dices = getGameController().getDiceOnRoundTrack();
		
		GameDie gameDie = null;
		System.out.println("de kleur en ID van dobbelsteen is "+ color.toString() + diceID);
		for(int i = 0; i < dices.size();i++ ) {
			if(dices.get(i).getNumber() == diceID && dices.get(i).getColor().equals(color)) {
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
