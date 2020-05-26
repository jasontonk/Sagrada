package controller;

import java.util.ArrayList;

import model.GameDie;
import model.ModelColor;

public class GameUpdater implements Runnable {

	GameController gameCtrl;
	
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
		ArrayList<ModelColor> colors = new ArrayList<>();
		ArrayList<Integer> values = new ArrayList<>();
		ArrayList<GameDie> diceOnRoundTrack;
		diceOnRoundTrack = gameCtrl.getGame().getDiceOnRoundtrack();
		for (int i = 1; i <= 10; i++) {
			for (int j = 0; j < diceOnRoundTrack.size(); j++) {
				if(diceOnRoundTrack.get(j).isOnRoundTrack() == i) {
					colors.add(diceOnRoundTrack.get(j).getColor());
					values.add(diceOnRoundTrack.get(j).getEyes());
				}
			}
			gameCtrl.getGameView().getRoundtrackView().addDice(i, colors, values);
			colors.clear();
			values.clear();
		}
		
	}

	private void updatePatterncards() {
		// TODO Auto-generated method stub
		
	}

	private void updateDicePool() {
		// TODO Auto-generated method stub
		
	}
}
