package controller;

import java.util.ArrayList;

import javafx.concurrent.Task;
import model.GameDie;
import model.ModelColor;

public class GameViewUpdater extends Task<Void> {
	private GameController gameCtrl;
	private GameUpdater gameUpdates;
	
	public GameViewUpdater(GameController gameCtrl, GameUpdater gameUpdater ) {
		this.gameCtrl = gameCtrl;
		gameUpdates = gameUpdater;
	}
	
	@Override
	public Void call() throws Exception {
		while(true){
			System.out.println("threadtester");
			updateAll();
			System.out.println("threadtester2");
			System.out.println("updated Views");
			Thread.sleep(2000);
		}
	}
	private void updateAll() {
		updateDicePoolView();
		System.out.println("threadtester2");
		updatePatterncardsView();
		System.out.println("threadtester3");
		updateRountrackView();
		System.out.println("threadtester4");
	}
	
	private void updateRountrackView() {
		ArrayList<GameDie> diceOnRoundTrack = new ArrayList<GameDie>();
		diceOnRoundTrack = gameUpdates.getDiceOnRoundTrack();
		System.out.println("threadtester4");
		ArrayList<ModelColor> colors = new ArrayList<>();
		ArrayList<Integer> values = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			System.out.println("threadtester5");
			for (int j = 0; j < diceOnRoundTrack.size(); j++) {
				System.out.println("System.out.println(\"threadtester2\");");
				if(diceOnRoundTrack.get(j).isOnRoundTrack() == i) {
					System.out.println("Hier zou hij niet moeten komen");
					colors.add(diceOnRoundTrack.get(j).getColor());
					values.add(diceOnRoundTrack.get(j).getEyes());
				}
			}
			if(colors.size() != 0) {
				gameCtrl.getGameView().getRoundtrackView().addDice(i, colors, values);
				colors.clear();
				values.clear();
			}
		}
		
	}
	private void updatePatterncardsView() {
		// TODO Auto-generated method stub
		
	}

	private void updateDicePoolView() {
		// TODO Auto-generated method stub
		
	}


}
