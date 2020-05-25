package controller;

public class GamePoller implements Runnable {

	private GameController gameController;
	private int seconds;
	private boolean finishedTurn;
	
	public GamePoller(GameController gameController, int seconds) {
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
			gameController.getGame().playround();
		}
		gameController.getGame().setNextPlayer();
		
		// 1221 2112 1221
	}

	public void setFinishedTurn(boolean finishedTurn) {
		this.finishedTurn = finishedTurn;
		gameController.setFinishedTurnTrue();
	}
	
	
}
