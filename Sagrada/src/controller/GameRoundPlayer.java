package controller;

import javafx.application.Platform;
import model.Player;

public class GameRoundPlayer implements Runnable {

	private GameController gameController;
	private int seconds;
	private boolean finishedTurn;
	
	public GameRoundPlayer(GameController gameController, int seconds) {
		this.gameController = gameController;
		this.seconds = seconds ;
	}
	
	

	@Override
	public void run() {
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
		if(gameController.getGame().getRound().get()%2 == 0 && gameController.getGame().getRound().get() != 1 && gameController.getGame().getCurrentPlayer().getSequenceNumber() == 1) {
			gameController.getGame().addLeftOverDiceToRoundTrack();
		}
		gameController.getGame().setNextPlayer();
		
		
		// 1221 2112 1221
	}

	public void setFinishedTurn(boolean finishedTurn) {
		this.finishedTurn = finishedTurn;
		gameController.setFinishedTurnTrue();		
	}
	
	
}
