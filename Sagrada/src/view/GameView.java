package view;

import controller.DieController;
import controller.GameController;
import controller.PatterncardController;
import controller.RoundtrackController;
import javafx.scene.layout.BorderPane;

public class GameView extends BorderPane {

	private GameController gameController;
	private DicePoolView dicePoolView;
	private PatterncardView patterncardView;
	private RoundtrackView roundtrackView;
	
	public GameView(GameController gameController){
		this.gameController = gameController;
		dicePoolView = new DicePoolView(gameController.getDieController());
		patterncardView = new PatterncardView(gameController.getPatterncardController());
		roundtrackView = new RoundtrackView(gameController.getRoundtrackController());
		this.setLeft(patterncardView);
		this.setRight(dicePoolView);
		this.setBottom(roundtrackView);
	}

	public DicePoolView getDicePoolView() {
		return dicePoolView;
	}

	public PatterncardView getPatterncardView() {
		return patterncardView;
	}

	public RoundtrackView getRoundtrackView() {
		return roundtrackView;
	}
	
}
