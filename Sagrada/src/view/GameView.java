package view;


import controller.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameView extends HBox {

	private DicePoolView dicePoolView;
	private PatterncardView patterncardView;
	private RoundtrackView roundtrackView;
	private ScoreView scoreView;
	private PublicObjectiveCardsView publicObjectiveCardsView;
	private ToolcardPoolView toolcardPoolView;
	private PatternCardsOfOtherPlayersView patternCardsOfOtherPlayersView;
	private ChatView chatView;
	
	
	public GameView(GameController gameController){
		BorderPane right = new BorderPane();
		chatView = new ChatView(gameController);
		dicePoolView = new DicePoolView(gameController.getDieController());
		patterncardView = new PatterncardView(gameController.getPatterncardController());
		roundtrackView = new RoundtrackView(gameController.getRoundtrackController());
		scoreView = new ScoreView(gameController.getGame());
		publicObjectiveCardsView = new PublicObjectiveCardsView(
				gameController.getGame().getPublicObjectiveCardIDs()
				);
		toolcardPoolView = new ToolcardPoolView(gameController);
		patternCardsOfOtherPlayersView = new PatternCardsOfOtherPlayersView(gameController.getPatterncardController());
		Button button = new Button("Beurt beŽindigen.");//TODO temporary
		button.setOnMouseClicked(e-> gameController.stopround());
		
		HBox currentPlayerText = new HBox();
		Text text = new Text();
		text.textProperty().bind(gameController.getGame().getCurrentPlayerName());
		Label currentPlayerLabel = new Label("De huidige speler = ");
		currentPlayerText.getChildren().addAll(currentPlayerLabel, text);
		
		HBox topView = new HBox();
		topView.getChildren().add(publicObjectiveCardsView);
		topView.getChildren().add(toolcardPoolView);
		
		right.setTop(topView);
		right.setLeft(patterncardView);
		HBox dicepoolChat = new HBox(dicePoolView, chatView);
		right.setRight(dicepoolChat);
		HBox bottom = new HBox();
		bottom.getChildren().addAll(roundtrackView, scoreView);
		button.setPadding(new Insets(10));
		right.setBottom(bottom);
		VBox center = new VBox();
		center.getChildren().addAll(button, currentPlayerText);
		center.setSpacing(50);
		right.setCenter(center);
		right.setPrefSize(1000, 800);
		right.setAlignment(roundtrackView, Pos.CENTER);
		this.getChildren().addAll(patternCardsOfOtherPlayersView, right);
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
	
	public ChatView getChatView() {
		return chatView;
	}

	
	public ToolcardPoolView getToolcardPoolView() {
		return toolcardPoolView;
	}

	/**
	 * @return the patternCardsOfOtherPlayersView
	 */
	public PatternCardsOfOtherPlayersView getPatternCardsOfOtherPlayersView() {
		return patternCardsOfOtherPlayersView;
	}

	public void updateDicePoolView(int amountofdice) {
		dicePoolView.updateDicePool(amountofdice);
	}

	public void updateFavorTokenView(int favortokens) {
		patterncardView.drawFavorToken(favortokens);
	}
}
