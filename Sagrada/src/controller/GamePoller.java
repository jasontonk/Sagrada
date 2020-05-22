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
		
		while(!finishedTurn) {
			try {
				Thread.sleep(1 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			gameController.getGame().playround();
		}
		
	}



	public void setFinishedTurn(boolean finishedTurn) {
		this.finishedTurn = finishedTurn;
	}
	
	
}
