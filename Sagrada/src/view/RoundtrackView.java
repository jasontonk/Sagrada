package view;

import controller.RoundtrackController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class RoundtrackView extends VBox {

	private RoundtrackController roundtrackController;
	private final int ROUNDTRACKFIELD_SIZE = 60;
	private final double SPACING = 5.0;
	private Insets padding = new Insets(5);  
	
	public RoundtrackView(RoundtrackController roundtrackController) {
		this.roundtrackController = roundtrackController;
		this.getChildren().addAll(drawTitle(), drawRoundtrack(), drawRound());
	}
	
	public GridPane drawRoundtrack() {
		GridPane roundtrack = new GridPane();
		roundtrack.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
		roundtrack.setMinSize((10 * (ROUNDTRACKFIELD_SIZE + SPACING) + SPACING), (ROUNDTRACKFIELD_SIZE + SPACING + SPACING));
		roundtrack.setPadding(padding);
		roundtrack.setHgap(SPACING);
		
		for (int i = 0; i < 10; i++) {
			Text text =  new Text("ronde " + (i+1));
			text.setFill(Color.WHITE);
			Button button = new Button();
			button.setPrefSize(ROUNDTRACKFIELD_SIZE, ROUNDTRACKFIELD_SIZE);
			button.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
			roundtrack.add(text, i, 1);
			roundtrack.add(button, i, 2);
			
		}
		
		return roundtrack;
	}
	public BorderPane drawTitle() {
		BorderPane titlePane = new BorderPane();
		Text title = new Text();
		title.setText("Rondespoor");
		titlePane.setPadding(padding);
		titlePane.setCenter(title);
		return titlePane;
	}
	public BorderPane drawRound() {
		BorderPane roundPane =  new BorderPane();
		Text round = new Text();
		round.setText("ronde: 6");
//		round.setText("Ronde: " + roundtrackController.getRound());
		roundPane.setPadding(padding);
		roundPane.setRight(round);
		return roundPane;
	}
}
