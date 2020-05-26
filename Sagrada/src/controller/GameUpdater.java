package controller;

import java.util.ArrayList;

import model.GameDie;
import model.ModelColor;

public class GameUpdater implements Runnable {

	private GameController gameCtrl;
	private ArrayList<GameDie> diceOnRoundTrack;
	
	public GameUpdater(GameController gamecontroller) {
		gameCtrl = gamecontroller;
	}
	
	@Override
	public void run() {
		while(true){
			updateAll();
			System.out.println("updated games");
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

		diceOnRoundTrack = gameCtrl.getGame().getDiceOnRoundtrack();
		
	}

	private void updatePatterncards() {
		// TODO Auto-generated method stub
		
	}

	private void updateDicePool() {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<GameDie> getDiceOnRoundTrack() {
		return diceOnRoundTrack;
	}
}
