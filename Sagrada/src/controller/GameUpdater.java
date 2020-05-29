package controller;

import java.util.ArrayList;

import javafx.application.Platform;
import model.GameDie;
import model.ModelColor;
import model.Player;

public class GameUpdater implements Runnable {

	private GameController gameCtrl;
	private ArrayList<GameDie> diceOnRoundTrack;
	private volatile boolean isRunning;
	private volatile boolean isPaused;
	
	public GameUpdater(GameController gamecontroller) {
		diceOnRoundTrack = new ArrayList<GameDie>();
		gameCtrl = gamecontroller;
		isRunning = true;
		isPaused = false;
	}
	
	@Override
	public void run() {
		while(isRunning){
			if(!isPaused) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						updateAll();
						System.out.println("updated games");
					}				
				});
				
				try {
					Thread.sleep(10000);
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

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	private void updateAll() {
		updateDicePool();
		updatePatterncards();
		updateRountrack();
		updateScore();
	}

	private void updateRountrack() {
		diceOnRoundTrack = gameCtrl.getGame().getDiceOnRoundtrack();
//		for (GameDie gameDie : diceOnRoundTrack) {
//			gameCtrl.getGame().getRoundTrack().placeDie(gameDie,gameDie.isOnRoundTrack());
//		}
		
		gameCtrl.updateRoundTrack(diceOnRoundTrack);
	}

	private void updatePatterncards() {
		// TODO Auto-generated method stub
		
	}

	private void updateDicePool() {
		
		gameCtrl.getGame().getDicePoolFromDB();
	}
	
	private void updateScore() {
//		for (Player p : gameCtrl.getGame().getPlayers()) {
//			p.calculateScore();
//		}
//		gameCtrl.getGameView().getScoreView().makeScoreBoard();
//		System.out.println("gelukt 1");
		
		for (Player p : gameCtrl.getGame().getPlayers()) {
			p.getScore();
		}
	}
}
