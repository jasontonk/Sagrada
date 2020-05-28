package controller;

import java.util.ArrayList;

import javafx.concurrent.Task;
import model.GameDie;
import model.ModelColor;
import model.Player;

public class GameViewUpdater extends Task<Void> {
	private GameController gameCtrl;
	private GameUpdater gameUpdates;
	
	public GameViewUpdater(GameController gameCtrl, GameUpdater gameUpdater ) {
		this.gameCtrl = gameCtrl;
		gameUpdates = gameUpdater;
	}
	
	@Override
	public Void call() {
		while(true){
			updateAll();
			System.out.println("updated Views");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private void updateAll() {
		updateDicePoolView();
		updatePatterncardsView();
		updateRountrackView();
//		updateScoreBoard();
	}
	
	private void updateRountrackView() {
		ArrayList<GameDie> diceOnRoundTrack = new ArrayList<GameDie>();
		diceOnRoundTrack = gameCtrl.getChangedDiceOnRoundTrack();
		ArrayList<ModelColor> colors = new ArrayList<>();
		ArrayList<Integer> values = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			for (int j = 0; j < diceOnRoundTrack.size(); j++) {
				if(diceOnRoundTrack.get(j).isOnRoundTrack() == i) {
					colors.add(diceOnRoundTrack.get(j).getColor());
					values.add(diceOnRoundTrack.get(j).getEyes());
				}
			}
			if(colors.size() != 0) {
				gameCtrl.getGameView().getRoundtrackView().addDice(i, colors, values);
			}
			colors.clear();
			values.clear();
		}
		colors.clear();
		values.clear();
		gameCtrl.clearChangedDiceOnRoundTrack(); 
	}
	private void updatePatterncardsView() {
		// TODO Auto-generated method stub
		
	}

	private void updateDicePoolView() {
		ArrayList<GameDie> offer = gameCtrl.getGame().getOffer();
		System.out.println("got offer");
//		for (int i = 0; i < offer.length; i++) {
//			System.out.println(offer[i] +", ");
//		}
		gameCtrl.getDieController().updateDicePool(offer);
		System.out.println("updated dicepool");
		
	}
	
//	private void updateScoreBoard() {
//		for (Player p : gameCtrl.getGame().getPlayers()) {
//			p.calculateScore();
//		}
//		gameCtrl.getGameView().getScoreView().makeScoreBoard();
//		System.out.println("gelukt 2");
//	}


}
