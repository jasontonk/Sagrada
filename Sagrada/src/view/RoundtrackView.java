package view;

import java.util.ArrayList;

import controller.RoundtrackController;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.GameDie;
import model.ModelColor;

public class RoundtrackView extends VBox {

	private RoundtrackController roundtrackController;
	private JavafxColor javafxColor;
	private final int ROUNDTRACKFIELD_SIZE;
	private final double SPACING;
	private Insets padding;
	private GridPane roundtrack;
	private ArrayList<StackPane> stackpanes;
 	
	
	public RoundtrackView(RoundtrackController roundtrackController) {
		ScrollPane roundTrackScroll = new ScrollPane();
		this.roundtrackController = roundtrackController;
		javafxColor = new JavafxColor();
		roundtrack = new GridPane();
		padding = new Insets(5,20,5,5);
		SPACING = 5.0;
		ROUNDTRACKFIELD_SIZE = 60;
		stackpanes = new ArrayList<StackPane>();
		this.setPadding(new Insets(0, 30, 0, 30));
		this.setMinWidth((10 * (ROUNDTRACKFIELD_SIZE + SPACING) + SPACING) + 50);
		this.setMaxHeight(200);
		VBox content = new VBox(drawTitle(), drawRoundtrack(), drawRound());
		roundTrackScroll.setContent(content);
		this.getChildren().add(roundTrackScroll);
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
			StackPane stackpane = new StackPane();
			button.setPrefSize(ROUNDTRACKFIELD_SIZE, ROUNDTRACKFIELD_SIZE);
			button.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
			roundtrack.add(text, i, 1);
			roundtrack.setHalignment(text, HPos.CENTER);
			stackpane.getChildren().add(button);
			stackpanes.add(stackpane);
			roundtrack.add(stackpanes.get(i), i, 2);
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
		Label roundnr = new Label();
		roundnr.textProperty().bind(roundtrackController.getGameController().getGame().getRound().asString());
		
		round.setText("Ronde: "); 
		roundPane.setPadding(padding);
		HBox text = new HBox();
		text.getChildren().addAll(round,roundnr);
		roundPane.setRight(text);
		return roundPane;
	}
	public void addDice(int round, ArrayList<ModelColor> colors, ArrayList<Integer> values) {
		
				for(int i = 0; i < colors.size(); i++) {
					String imgURL;
					Button button = new Button();
					String color = javafxColor.getStringColorFromModelColor(colors.get(i)).toUpperCase();
					final int index = i;
					
					imgURL = "/images/" + color + values.get(i) + "_Die.png";
					Image image = new Image(getClass().getResource(imgURL).toString());
					button.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1, 1, false, false, false, true))));
					button.setPrefSize(50, 50);
					button.setOnMouseClicked(e -> setSelectedDie(round, index));
					if(i == 0) {
						stackpanes.get(round-1).getChildren().add(button);
					}
					else {
						
						Button button2 = new Button();
						StackPane stackpane = new StackPane();
						button2.setPrefSize(ROUNDTRACKFIELD_SIZE, ROUNDTRACKFIELD_SIZE);
						button2.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
						stackpane.getChildren().add(button2);
						roundtrack.add(stackpane, round-1, 2+i);
						stackpane.getChildren().add(button);
						stackpanes.add(stackpane);
						}
					}
	}

	private void setSelectedDie(int round, int index) {
		roundtrackController.setSelectedDie(round, index);
	}
}
