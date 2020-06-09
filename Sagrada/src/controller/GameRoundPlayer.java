package controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import model.Player;

public class GameRoundPlayer extends Task<Boolean> {

	private GameController gameController;
	private int seconds;
	private boolean finishedTurn;
	private volatile boolean isPaused;
	private volatile boolean isRunning;
	
	public GameRoundPlayer(GameController gameController, int seconds) {
		this.gameController = gameController;
		this.seconds = seconds ;
	}
	
	

	@Override
	public Boolean call() {
		isRunning = true;
		while(isRunning) {
			if(gameController.getGame().isFinishedGame()) {
				
			}
			else {
				if(!isPaused) {
					finishedTurn = false;
					gameController.getGame().setPlacedDie(false);
					while(!finishedTurn) {
						
						try {
							Thread.sleep(seconds * 1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
								gameController.getGame().playround();			
							}
						});
							
					}
					
					if(gameController.getGame().getRoundFromDB()%2 == 0 && gameController.getGame().getRound().get() != 1 && gameController.getGame().getCurrentPlayer().getSequenceNumber() == 1) {
						gameController.getGame().addLeftOverDiceToRoundTrack();
					}
					gameController.getGame().setNextPlayer();
					
					Platform.runLater(new Runnable() {
			
						@Override
						public void run() {
							gameController.getGame().getPersonalPlayer().calculateScore();
						}
						
					});
				}
				else {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}	
		return isRunning;
		
		// 1221 2112 1221
	}

	public void setFinishedTurn(boolean finishedTurn) {
		this.finishedTurn = finishedTurn;
		gameController.setFinishedTurnTrue();		
	}



	public void setIsPaused(boolean b) {
		isPaused = b;
	}
	
	
}
