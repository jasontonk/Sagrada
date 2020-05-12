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
	
	public GameView(GameController gameController, DieController dieController, PatterncardController patterncardController, RoundtrackController roundtrackController){
		this.gameController = gameController;
		dicePoolView = new DicePoolView(dieController);
		patterncardView = new PatterncardView(patterncardController);
		roundtrackView = new RoundtrackView(roundtrackController);
		this.setLeft(patterncardView);
		this.setRight(dicePoolView);
		this.setBottom(roundtrackView);
	}
}
