package controller;

import java.util.ArrayList;

import model.GameDie;
import model.ModelColor;

public class GameUpdater implements Runnable {

	private GameController gameCtrl;
	private ArrayList<GameDie> diceOnRoundTrack;
	
	public GameUpdater(GameController gamecontroller) {
		diceOnRoundTrack = new ArrayList<GameDie>();
		gameCtrl = gamecontroller;
	}
	
	@Override
	public void run() {
		while(true){
			updateAll();
			System.out.println("updated games");
			try {
				Thread.sleep(10000);
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
}
