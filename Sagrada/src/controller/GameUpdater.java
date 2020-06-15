package controller;

import java.util.ArrayList;

import com.sun.webkit.ContextMenu.ShowContext;

import javafx.application.Platform;
import model.GameDie;
import model.ModelColor;
import model.Player;
import model.PlayerStatus;

public class GameUpdater implements Runnable {

	private GameController gameCtrl;
	private ArrayList<GameDie> diceOnRoundTrack;
	private volatile boolean isRunning;
	private volatile boolean isPaused;
	
	public GameUpdater(GameController gamecontroller) {
		diceOnRoundTrack = new ArrayList<GameDie>();
		gameCtrl = gamecontroller;
		
		isPaused = false;
	}
	
	@Override
	public void run() {
		isRunning = true;
		while(isRunning){
			if(gameCtrl.getGame().isFinishedGame()) {
				isRunning = false;
			}
			else {
				if(!isPaused) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							updateAll();
							System.out.println("updated games");
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
		checkFinished();
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				updateScore();
				updateCurrentPlayer();
			}
		});
		
	}

	private void updateCurrentPlayer() {
		
		
		gameCtrl.getGame().setCurrentPlayer(gameCtrl.getCurrentPlayer());
		
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
	
	private void checkFinished() {
		if(gameCtrl.getGame().getCurrentPlayer().getPlayerStatus().equals(PlayerStatus.FINISHED)) {
			gameCtrl.getGame().finishGame();
		}
	}
}
