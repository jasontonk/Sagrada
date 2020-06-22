package controller;

import javafx.application.Platform;
import javafx.concurrent.Task;

public class GameRoundPlayer extends Task<Boolean> {

	private GameController gameController;
	private int seconds;
	private boolean finishedTurn;
	private volatile boolean isPaused;
	private volatile boolean isRunning;

	public GameRoundPlayer(GameController gameController, int seconds) {
		this.gameController = gameController;
		this.seconds = seconds;
	}

	@Override
	public Boolean call() {
		isRunning = true;
		while (isRunning) {
			if (gameController.getGame().isFinishedGame()) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						gameController.setFinishedGameView();
					}
				});

			} else {
				if (!isPaused) {
					finishedTurn = false;
					gameController.getGame().setPlacedDie(false);
					gameController.getGame().setUsedToolcard(false);
					while (!finishedTurn) {

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

					if (gameController.getGame().getRoundFromDB() % 2 == 0
							&& gameController.getGame().getRound().get() != 1
							&& gameController.getGame().getCurrentPlayer().getSequenceNumber() == 1) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						gameController.getGame().addLeftOverDiceToRoundTrack();
					}
					gameController.getGame().setNextPlayer();
					gameController.setShowTurnMessage(false);

					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							gameController.getGame().getPersonalPlayer().calculateScore();
							if(gameController.getGame().isFinishedGame()) {
								gameController.setFinishedGameView();
							}
						}
					});
				} else {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return isRunning;
	}

	public void setFinishedTurn(boolean finishedTurn) {
		this.finishedTurn = finishedTurn;
		gameController.setFinishedTurnTrue();
	}

	public void setIsPaused(boolean b) {
		isPaused = b;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}
