package controller;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.concurrent.Task;
import model.GameDie;
import model.ModelColor;
import model.Player;
import model.PlayerStatus;

public class GameViewUpdater extends Task<Boolean> {
	private GameController gameCtrl;
	private volatile boolean isRunning;
	private volatile boolean isPaused;
	private int counter = 0;
	private int updateCounter = 0;
	
	public GameViewUpdater(GameController gameCtrl, GameUpdater gameUpdater ) {
		this.gameCtrl = gameCtrl;
		
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
		System.out.println("UPDATING ALL");
		updateDicePoolView();
		updateRoundtrackView();
		if(updateCounter == 10) {
			updatePatternCardsOfOtherPlayers();
			updateCounter = 0;
		}
		updateCounter++; 
	}
	
	private void updateRoundtrackView() {
		ArrayList<GameDie> diceOnRoundTrack = new ArrayList<GameDie>();
		ArrayList<Integer> diceNumber = new ArrayList<Integer>();
		diceOnRoundTrack = gameCtrl.getChangedDiceOnRoundTrack();
		ArrayList<ModelColor> colors = new ArrayList<>();
		ArrayList<Integer> values = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			for (int j = 0; j < diceOnRoundTrack.size(); j++) {
				if(diceOnRoundTrack.get(j).isOnRoundTrack() == i) {
					colors.add(diceOnRoundTrack.get(j).getColor());
					values.add(diceOnRoundTrack.get(j).getEyes());
					diceNumber.add(diceOnRoundTrack.get(j).getNumber());
				}
			}
			if(colors.size() != 0) {
				gameCtrl.getGameView().getRoundtrackView().addDice(i, colors, values,diceNumber);
			}
			colors.clear();
			values.clear();
		}
		colors.clear();
		values.clear(); 
		gameCtrl.clearChangedDiceOnRoundTrack(); 
	}

	private void updateDicePoolView() {
		ArrayList<GameDie> offer = gameCtrl.getGame().getOffer();
		gameCtrl.getDieController().updateDicePool(offer);
	}
	
	public void updateScoreBoard() {
		gameCtrl.getGameView().getScoreView().makeScoreBoard();
	}

	public void checkFinishedGame(){
		if(gameCtrl.getGame().getPersonalPlayer().getPlayerStatus().equals(PlayerStatus.FINISHED)) {
			gameCtrl.setFinishedGameView();
			setRunning(false);
		}
	}

	public void updatePatternCardsOfOtherPlayers() {
		if(true) {
			System.out.println("IF 1");
			for(Player p : gameCtrl.getGame().getPlayers()) {
				gameCtrl.getGameView().getPatternCardsOfOtherPlayersView().makePatternCards();
			}
			counter++;
		}
		else {
			System.out.println("IF 2");
			for(Player p : gameCtrl.getGame().getPlayers()) {
				for (int x = 0; x < 5; x++) {
					for (int y = 0; y < 4; y++) {
						gameCtrl.getGameView().getPatternCardsOfOtherPlayersView().updatePatternCards(p, x, y);
					}
				}
			}
		}
	}
}
