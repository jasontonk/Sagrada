package controller;

import java.util.ArrayList;

import model.GameDie;

public class GameUpdater implements Runnable {

	GameController gameCtrl;
	
	public GameUpdater(GameController gamecontroller) {
		gameCtrl = gamecontroller;
	}
	
	@Override
	public void run() {
		while(true){
			updateAll();
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	private void updateAll() {
		updateDicePool();
		updatePatterncards();
		updateRountrack();
	}

	private void updateRountrack() {
		ArrayList<GameDie> diceOnRoundTrack;
		diceOnRoundTrack = gameCtrl.getGame().getDiceOnRoundtrack();
		
	}

	private void updatePatterncards() {
		// TODO Auto-generated method stub
		
	}

	private void updateDicePool() {
		// TODO Auto-generated method stub
		
	}
}
