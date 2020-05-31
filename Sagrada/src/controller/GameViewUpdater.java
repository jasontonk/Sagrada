package controller;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import model.GameDie;
import model.ModelColor;
import model.Player;

public class GameViewUpdater extends Task<Boolean> {
	private GameController gameCtrl;
	private GameUpdater gameUpdates;
	private volatile boolean isRunning;
	private volatile boolean isPaused;
	
	public GameViewUpdater(GameController gameCtrl, GameUpdater gameUpdater ) {
		this.gameCtrl = gameCtrl;
		gameUpdates = gameUpdater;
	}
	
	@Override
	public Boolean call() {
		isRunning = true;
		while(isRunning){
			if(gameCtrl.getGame().isFinishedGame()) {
				isRunning = false;
				Player winner = gameCtrl.getGame().getWinnerOfGameWithID(gameCtrl.getGame().getGameID());
				String username = winner.getName();
				SimpleIntegerProperty score = winner.getScore();
				String winnerText = "DE WINNAAR IS: " +username+ "\n" + username + "heeft gewonnen met een score van: "+ score; 
				gameCtrl.showWarning("Game Over", winnerText);
			}
			else {
				if(!isPaused) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							updateAll();
							System.out.println("updated Views");
						}
					});
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return isRunning;
	}
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
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
