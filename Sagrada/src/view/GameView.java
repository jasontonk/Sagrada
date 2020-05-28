package view;


import controller.GameController;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameView extends BorderPane {

	private GameController gameController;
	private DicePoolView dicePoolView;
	private PatterncardView patterncardView;
	private RoundtrackView roundtrackView;
	private ScoreView scoreView;
	
	public GameView(GameController gameController){
		this.gameController = gameController;
		dicePoolView = new DicePoolView(gameController.getDieController());
		patterncardView = new PatterncardView(gameController.getPatterncardController());
		roundtrackView = new RoundtrackView(gameController.getRoundtrackController());
		scoreView = new ScoreView(gameController.getGame());
		Button button = new Button("Klik om uw beurt te beëindigen.");//TODO temporary
		button.setOnMouseClicked(e-> gameController.getGamePoller().setFinishedTurn(true));
		Button button2 = new Button("Klik om beurt te beginnen");
		button2.setOnMouseClicked(e-> gameController.playround());
		this.setLeft(patterncardView);
		this.setRight(dicePoolView);
		VBox bottom = new VBox();
		bottom.getChildren().addAll(roundtrackView, scoreView);
		this.setBottom(bottom);
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
	public void updateDicePoolView() {
		dicePoolView.updateDicePool();
	}
	
}
