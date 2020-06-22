package controller;

import javafx.application.Platform;
import javafx.concurrent.Task;

public class ChatViewUpdater extends Task<Boolean> {
	private GameController gameCtrl;
	private volatile boolean isRunning;
	private volatile boolean isPaused;
	
	public ChatViewUpdater(GameController gameCtrl, GameUpdater gameUpdater ) {
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
							updateChat();
							System.out.println("updated Chat");
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
	
	public void updateChat() {
		gameCtrl.updateChat();
	}
}
