package view;


import controller.GameController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameView extends BorderPane {

	private GameController gameController;
	private DicePoolView dicePoolView;
	private PatterncardView patterncardView;
	private RoundtrackView roundtrackView;
	private ScoreView scoreView;
	private PublicObjectiveCardsView publicObjectiveCardsView;
	private ToolcardPoolView toolcardPoolView;
	
	public GameView(GameController gameController){
		this.gameController = gameController;
		dicePoolView = new DicePoolView(gameController.getDieController());
		patterncardView = new PatterncardView(gameController.getPatterncardController());
		roundtrackView = new RoundtrackView(gameController.getRoundtrackController());
		scoreView = new ScoreView(gameController.getGame());
		publicObjectiveCardsView = new PublicObjectiveCardsView(
				gameController.getGame().getPublicObjectiveCardIDs()
				);
		toolcardPoolView = new ToolcardPoolView(gameController);
		Button button = new Button("Beurt beëindigen.");//TODO temporary
		button.setOnMouseClicked(e-> gameController.stopround());
//		Button button2 = new Button("Beurt beginnen");
//		button2.setOnMouseClicked(e-> gameController.playround());
		
		HBox currentPlayerText = new HBox();
		Text text = new Text();
		text.textProperty().bind(gameController.getGame().getCurrentPlayerName());
		Label currentPlayerLabel = new Label("De huidige speler = ");
		currentPlayerText.getChildren().addAll(currentPlayerLabel, text);
		
		HBox topView = new HBox();
		topView.getChildren().add(publicObjectiveCardsView);
		topView.getChildren().add(toolcardPoolView);
		
		this.setTop(topView);
		this.setLeft(patterncardView);
		this.setRight(dicePoolView);
		HBox bottom = new HBox();
		bottom.getChildren().addAll(roundtrackView, scoreView);
		button.setPadding(new Insets(10));
//		button2.setPadding(new Insets(10));
		this.setBottom(bottom);
		VBox center = new VBox();
//		center.getChildren().addAll(button,button2, currentPlayerText);
		center.getChildren().addAll(button, currentPlayerText);
		center.setSpacing(50);
		this.setCenter(center);
		this.setPrefSize(1000, 800);
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
	
	public ScoreView getScoreView() {
		return scoreView;
	}
	
	public void updateDicePoolView(int amountofdice) {
		dicePoolView.updateDicePool(amountofdice);
	}
	
}
