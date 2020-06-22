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
	private int roundtrackcounter = 0;
	
	public GameViewUpdater(GameController gameCtrl, GameUpdater gameUpdater ) {
		this.gameCtrl = gameCtrl;
	}
	
	@Override
	public Boolean call() {
		isRunning = true;
		while(isRunning){
			if(gameCtrl.getGame().isFinishedGame() || gameCtrl.getGame().getPersonalPlayer().getPlayerStatus().equals(PlayerStatus.FINISHED)) {
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
		updateRoundtrackView(false);
		if(updateCounter == 5) {
			updatePatternCardsOfOtherPlayers();
			updateCounter = 0;
		}
		updateCounter++; 
	}
	
	public void updateRoundtrackView(boolean completeUpdate) {
		ArrayList<GameDie> diceOnRoundTrack = new ArrayList<GameDie>();
		ArrayList<Integer> diceNumber = new ArrayList<Integer>();
		ArrayList<ModelColor> colors = new ArrayList<>();
		ArrayList<Integer> values = new ArrayList<>();
		
		diceOnRoundTrack = gameCtrl.getChangedDiceOnRoundTrack();
		
		if(roundtrackcounter > 5 || completeUpdate){
			System.out.println("updated roundtrackview");
			gameCtrl.getGameView().getRoundtrackView().removeAllDice();
			diceOnRoundTrack = gameCtrl.getDiceOnRoundTrack();
			roundtrackcounter = 0;
		}
		roundtrackcounter++;
		
		for (int i = 1; i <= 10; i++) {
			for (int j = 0; j < diceOnRoundTrack.size(); j++) {
				if(diceOnRoundTrack.get(j).isOnRoundTrack() == i) {
					colors.add(diceOnRoundTrack.get(j).getColor());
					values.add(diceOnRoundTrack.get(j).getEyes());
					diceNumber.add(diceOnRoundTrack.get(j).getNumber());
				}
			}
			if(colors.size() != 0) {
				gameCtrl.getGameView().getRoundtrackView().addDice(i, colors, values, diceNumber);
			}
			colors.clear();
			values.clear();
			diceNumber.clear();
		}
		colors.clear();
		values.clear(); 
		diceNumber.clear();
		gameCtrl.clearChangedDiceOnRoundTrack(); 
	}

	public void updateDicePoolView() {
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
			System.out.println("IF 1");
			gameCtrl.getGameView().getPatternCardsOfOtherPlayersView().makePatternCards();
			counter++;
	}
	
	
}
