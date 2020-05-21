package view;


import controller.GameController;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
		Button button = new Button("Klik om uw beurt te beëindigen.");//TODO temporary
		button.setOnMouseClicked(e-> gameController.setFinishedTurnTrue());
		Button button2 = new Button("Klik om beurt te beginnen");
		button2.setOnMouseClicked(e-> gameController.playround());
		this.setLeft(patterncardView);
		this.setRight(dicePoolView);
		this.setBottom(roundtrackView);
		this.setTop(button);
		this.setCenter(button2);
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
