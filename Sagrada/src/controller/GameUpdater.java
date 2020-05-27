package controller;

import java.util.ArrayList;

import model.GameDie;
import model.ModelColor;

public class GameUpdater implements Runnable {

	private GameController gameCtrl;
	private ArrayList<GameDie> changedDiceOnRoundTrack;
	private ArrayList<GameDie> diceOnRoundTrack;
	private ArrayList<GameDie> diceOnRoundTrackFromDB;
	
	public GameUpdater(GameController gamecontroller) {
		changedDiceOnRoundTrack = new ArrayList<GameDie>();
		diceOnRoundTrack = new ArrayList<GameDie>();
		diceOnRoundTrackFromDB = new ArrayList<GameDie>();
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
		diceOnRoundTrackFromDB = gameCtrl.getGame().getDiceOnRoundtrack();
		changedDiceOnRoundTrack = diceOnRoundTrackFromDB;
		System.out.println("DB arraysize:"+diceOnRoundTrackFromDB.size());
		for (GameDie gameDieFromDB : diceOnRoundTrackFromDB) {
			for (GameDie gameDie : diceOnRoundTrack) {
				if(gameDieFromDB.getColor() == gameDie.getColor() && gameDieFromDB.getNumber() == gameDie.getNumber()) {
					System.out.println("test");
					changedDiceOnRoundTrack.remove(gameDie);
					
				}
			}
		}
		System.out.println("amount of changes:"+changedDiceOnRoundTrack.size());
		diceOnRoundTrack = diceOnRoundTrackFromDB;
		
		
//		diceOnRoundTrackFromDB = gameCtrl.getGame().getDiceOnRoundtrack();
//		for (GameDie gameDie : diceOnRoundTrackFromDB) {
//			if(!diceOnRoundTrack.contains(gameDie)) {
//				changedDiceOnRoundTrack.add(gameDie);
//			}
//		}
//		diceOnRoundTrack = diceOnRoundTrackFromDB;
		
//		diceOnRoundTrack = gameCtrl.getGame().getDiceOnRoundtrack();
//		for (GameDie gameDie : diceOnRoundTrack) {
//			gameCtrl.getGame().getRoundTrack().placeDie(gameDie,gameDie.isOnRoundTrack());
//		}
		
		
	}

	private void updatePatterncards() {
		// TODO Auto-generated method stub
		
	}

	private void updateDicePool() {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<GameDie> getChangedDiceOnRoundTrack() {
		return changedDiceOnRoundTrack;
	}

	public void clearChangedDiceOnRoundTrack() {
		System.out.println("arraysize:"+changedDiceOnRoundTrack.size());
		changedDiceOnRoundTrack.clear();
		System.out.println("arraysize:"+changedDiceOnRoundTrack.size());
	}
}
