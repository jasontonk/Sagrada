package controller;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import model.GameDie;
import model.ModelColor;
import model.Player;
import model.PlayerStatus;

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
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						isRunning = false;
						System.out.println("test");
						ArrayList<String> winner = gameCtrl.getGame().getWinnerOfGameWithID(gameCtrl.getGame().getGameID());
						System.out.println("test 2");
//						String username = winner.get(0);
						System.out.println("test 3");
//						String score = winner.get(1);
						System.out.println("test 4");
//						String winnerText = "DE WINNAAR IS: " +username+ "\n" + username + "heeft gewonnen met een score van: "+ score; 
						System.out.println("test 5");
//						gameCtrl.showWarning("Game Over", winnerText);
						System.out.println("test 6");
						gameCtrl.setFinishedGameView();
					}
				});
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else {
				if(!isPaused) {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							updateAll();
							System.out.println("updated Views");
						}
					});
				}
				else {
					try {
						Thread.sleep(2000);
//						gameCtrl.getGame().getPersonalPlayer().calculateScore();
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
		updateRoundtrackView();
		updatePatternCardsOfOtherPlayers();
		
//		updateScoreBoard();
		System.out.println();
	}
	


	private void updateRoundtrackView() {
		GameDie gamedie = null;
		ArrayList<GameDie> diceOnRoundTrack = new ArrayList<GameDie>();
		diceOnRoundTrack = gameCtrl.getChangedDiceOnRoundTrack();
		ArrayList<ModelColor> colors = new ArrayList<>();
		ArrayList<Integer> values = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			for (int j = 0; j < diceOnRoundTrack.size(); j++) {
				if(diceOnRoundTrack.get(j).isOnRoundTrack() == i) {
					colors.add(diceOnRoundTrack.get(j).getColor());
					values.add(diceOnRoundTrack.get(j).getEyes());
					gamedie = diceOnRoundTrack.get(j);
				}
			}
			if(colors.size() != 0 && diceOnRoundTrack.size() != 0 && i <= diceOnRoundTrack.size()) {
				gameCtrl.getGameView().getRoundtrackView().addDice(i, colors, values,diceOnRoundTrack.get(i-1));
			}
			colors.clear();
			values.clear();
		}
		colors.clear();
		values.clear();
		gameCtrl.clearChangedDiceOnRoundTrack(); 
	}
	private void updatePatterncardsView() {
		
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
	
	public void updateScoreBoard() {
		gameCtrl.getGameView().getScoreView().makeScoreBoard();
		System.out.println("gelukt 2");
	}

	public void checkFinishedGame(){
		if(gameCtrl.getGame().getPersonalPlayer().getPlayerStatus().equals(PlayerStatus.FINISHED)) {
			gameCtrl.setFinishedGameView();
			setRunning(false);
		}
	}

	public void updatePatternCardsOfOtherPlayers() {
		System.out.println("HIJ KOMT IN DE GAMEVIEWUPDATER");
		if(gameCtrl.isNewCurrentPlayer()) {
			System.out.println("HIJ KOMT IN DE GAMEVIEWUPDATER 2");
			for(Player p : gameCtrl.getGame().getPlayers()) {
				gameCtrl.getGameView().getPatternCardsOfOtherPlayersView().drawPatterncard(p);
			}
			gameCtrl.setNewCurrentPlayer(false);
		}
	}
}
