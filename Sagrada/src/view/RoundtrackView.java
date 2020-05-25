package view;

import java.util.ArrayList;

import controller.RoundtrackController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.GameDie;
import model.ModelColor;

public class RoundtrackView extends VBox {

	private RoundtrackController roundtrackController;
	private JavafxColor javafxColor;
	private final int ROUNDTRACKFIELD_SIZE = 60;
	private final double SPACING = 5.0;
	private Insets padding = new Insets(5);  
	private GridPane roundtrack = new GridPane();
	
	public RoundtrackView(RoundtrackController roundtrackController) {
		this.roundtrackController = roundtrackController;
		javafxColor = new JavafxColor();
		this.setPadding(new Insets(0, 30, 0, 30));
		this.setMaxWidth((10 * (ROUNDTRACKFIELD_SIZE + SPACING) + SPACING));
		this.getChildren().addAll(drawTitle(), drawRoundtrack(), drawRound());
	}
	
	public GridPane drawRoundtrack() {
		
		roundtrack.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
		roundtrack.setMinSize((10 * (ROUNDTRACKFIELD_SIZE + SPACING) + SPACING), (ROUNDTRACKFIELD_SIZE + SPACING + SPACING));
		roundtrack.setPadding(padding);
		roundtrack.setHgap(SPACING);
		
		for (int i = 0; i < 10; i++) {
			Text text =  new Text(""+(i+1));
			text.setFill(Color.WHITE);
			text.setStyle("-fx-font-size: 20px;");
			Button button = new Button();
			button.setPrefSize(ROUNDTRACKFIELD_SIZE, ROUNDTRACKFIELD_SIZE);
			button.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
			roundtrack.add(text, i, 1);
			roundtrack.setHalignment(text, HPos.CENTER);
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
		round.setText("Ronde: " + roundtrackController.getRound());
		roundPane.setPadding(padding);
		roundPane.setRight(round);
		return roundPane;
	}
	public void addDice(int round, ArrayList<ModelColor> colors, ArrayList<Integer> values) {
		roundtrack.getChildren().remove(round,2);
		String imgURL;
		for(int i = 0; i < colors.size(); i++) {
			Button button = new Button();
			Color color = javafxColor.getJavafxColor(colors.get(i));
			
			imgURL = "/images/" + color + values.get(i) + "_Die.png";
			Image image = new Image(getClass().getResource(imgURL).toString());
			button.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1, 1, false, false, false, true))));
			
			roundtrack.add(button, round, i+2);
		}
	}
}
