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
//		System.out.println("diceonrountracksize = "+diceOnRoundTrack.size()+"    diceonroundtrackfromdbsize = "+ diceOnRoundTrackFromDB.size());
//		diceOnRoundTrackFromDB = gameCtrl.getGame().getDiceOnRoundtrack();
//		changedDiceOnRoundTrack = diceOnRoundTrackFromDB;
//		System.out.println("diceonrountracksize = "+diceOnRoundTrack.size()+"    diceonroundtrackfromdbsize = "+ diceOnRoundTrackFromDB.size());
//		for (GameDie gameDieFromDB : diceOnRoundTrackFromDB) {
//			System.out.println("test1");
//			for (int i = 0; i < diceOnRoundTrack.size(); i++) {
//				System.out.println("test2");
//				if(gameDieFromDB.getColor() == diceOnRoundTrack.get(i).getColor() && gameDieFromDB.getNumber() == diceOnRoundTrack.get(i).getNumber()) {
//					System.out.println("test3");
//					changedDiceOnRoundTrack.remove(diceOnRoundTrack.get(i));
//					
//				}
//			}
//		}
//		System.out.println("amount of changes:"+changedDiceOnRoundTrack.size());
//		System.out.println("diceonrountracksize = "+diceOnRoundTrack.size()+"    diceonroundtrackfromdbsize = "+ diceOnRoundTrackFromDB.size());
//		diceOnRoundTrack = diceOnRoundTrackFromDB;
//		System.out.println("diceonrountracksize = "+diceOnRoundTrack.size()+"    diceonroundtrackfromdbsize = "+ diceOnRoundTrackFromDB.size());
		
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
		
		gameCtrl.updateRoundTrack(gameCtrl.getGame().getDiceOnRoundtrack());
	}

	private void updatePatterncards() {
		// TODO Auto-generated method stub
		
	}

	private void updateDicePool() {
		// TODO Auto-generated method stub
		
	}
}
