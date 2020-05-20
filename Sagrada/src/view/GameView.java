package view;


import controller.GameController;
import javafx.geometry.Pos;
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
		this.setPrefSize(800, 600);
		this.setAlignment(roundtrackView, Pos.CENTER);
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
