package view;

import java.util.ArrayList;

import controller.PatterncardController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.FavorToken;
import model.ModelColor;

public class PatterncardView extends VBox {

	private PatterncardController patterncardController;
	private JavafxColor javafxColor = new JavafxColor();;
	private final int PATTERNCARDFIELD_SIZE = 50;
	private final double GRIDSPACING = 5.0;
	private Insets padding = new Insets(5);
	private String imgURL;
	private final int CIRCLE_SIZE =5;
	private final int SPACING = 6;
	private final int TEXT_SIZE = 12;
	private BorderPane favortokenView; 

	public PatterncardView(PatterncardController patterncardController) {
		this.patterncardController = patterncardController;
		this.setPadding(new Insets(0, 30, 0, 30));
		favortokenView = new BorderPane();
		this.getChildren().addAll(drawTitle(), drawPatterncard(), drawDifficulty(), drawPersonalObjectiveCardColor(),favortokenView);
		
		drawFavorToken(patterncardController.getDifficulty());
	}

	public GridPane drawPatterncard() {
		GridPane patterncardfields = new GridPane();
		patterncardfields.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		patterncardfields.setMinSize((5 * (PATTERNCARDFIELD_SIZE + GRIDSPACING) + GRIDSPACING),
				(4 * (PATTERNCARDFIELD_SIZE + GRIDSPACING) + GRIDSPACING));
		patterncardfields.setPadding(padding);
		patterncardfields.setHgap(GRIDSPACING);
		patterncardfields.setVgap(GRIDSPACING);

		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 4; y++) {
				StackPane stackpane = new StackPane();
				Button button = new Button();
				button.setPrefSize(PATTERNCARDFIELD_SIZE, PATTERNCARDFIELD_SIZE);
				int xPos = x;
				int yPos = y;
				String imgURL;

				ModelColor modelColor = patterncardController.getFieldColor(x, y);
				Color color = javafxColor.getJavafxColor(modelColor);
				int value = patterncardController.getFieldValue(x, y);

				if (color == null && value == 0) {
					button.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
				} else if (modelColor != null) {
					button.setBackground(new Background(new BackgroundFill(color, null, null)));
				} else if (value != 0) {
					imgURL = "/images/" + Integer.toString(value) + "_fieldValue.jpg";
					Image image = new Image(getClass().getResource(imgURL).toString());
					button.setBackground(new Background(
							new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
									BackgroundPosition.CENTER, new BackgroundSize(1, 1, false, false, false, true))));
				}
				button.setOnMouseClicked(e -> checkPlacementAgainstRules(xPos, yPos, stackpane));
				stackpane.getChildren().add(button);
				if (patterncardController.getGameController() != null && patterncardController.getGameController()
						.getGame().getPersonalPlayer().getBoard().getBoardFieldFromDB(xPos, yPos).hasDie()) {
					Button die = new Button();
					imgURL = "/images/"
							+ patterncardController.getGameController().getGame().getPersonalPlayer().getBoard()
									.getBoardField(xPos, yPos).getDie().getColor().toString()
							+ patterncardController.getGameController().getGame().getPersonalPlayer().getBoard()
									.getBoardField(xPos, yPos).getDie().getEyes()
							+ "_Die.png";
					if (imgURL != null) {
						Image image = new Image(getClass().getResource(imgURL).toString());
						die.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
								BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
								new BackgroundSize(1, 1, false, false, false, true))));
						die.setPrefSize(40, 40);
						die.setOnMouseClicked(e -> setSelectedDie(xPos, yPos, die, stackpane));
						stackpane.getChildren().add(die);
					}
				}
				patterncardfields.add(stackpane, x, y);
			}
		}
		return patterncardfields;
	}

	private void placeSelectedDie(int x, int y, StackPane stackpane) {
		Button die = new Button();

		imgURL = patterncardController.getSelectedDieUrl();
		if (imgURL != null) {
			Image image = new Image(getClass().getResource(imgURL).toString());
			die.setBackground(
					new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
							BackgroundPosition.CENTER, new BackgroundSize(1, 1, false, false, false, true))));
			die.setPrefSize(40, 40);
			stackpane.getChildren().add(die);
			patterncardController.deleteDieFromPool();
		}
		die.setOnMouseClicked(e -> setSelectedDie(x, y, die, stackpane));
	}

	private void setSelectedDie(int x, int y, Button button, StackPane stackPane) {
		patterncardController.setSelectedDie(x, y);
		
		if (patterncardController.getGameController().getGame().getSelectedToolcard() != null) {
			int id = patterncardController.getGameController().getGame().getSelectedToolcard().getId();
			if(id == 2) {
				stackPane.getChildren().remove(button);
				patterncardController.getGameController().getGame().deleteDieFromPatternCard(x, y);
				patterncardController.getGameController().setToolCardUnused();
			}
		}
	}

	public BorderPane drawTitle() {
		BorderPane titlePane = new BorderPane();
		Text title = new Text();
		title.setText(patterncardController.getName());
		titlePane.setPadding(padding);
		titlePane.setCenter(title);
		return titlePane;
	}

	public BorderPane drawDifficulty() {
		BorderPane difficultyPane = new BorderPane();
		Text difficulty = new Text();
		difficulty.setText("Moeilijkheidsgraad = " + patterncardController.getDifficulty());
		difficultyPane.setPadding(padding);
		difficultyPane.setRight(difficulty);
		return difficultyPane;
	}

	public BorderPane drawPersonalObjectiveCardColor() {
		BorderPane personalObjectiveCardColor = new BorderPane();
		if (patterncardController.getGameController() != null) {
			Text color = new Text();
			color.setText("Persoonlijke doelkaartkleur = " + patterncardController.getGameController().getGame()
					.getPersonalPlayer().getPersonalObjectiveCardColor().toString());
			color.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, TEXT_SIZE));
			personalObjectiveCardColor.setPadding(padding);
			personalObjectiveCardColor.setCenter(color);
		}
		return personalObjectiveCardColor;
	}
	
	public void drawFavorToken(int favortokens) {
		favortokenView.getChildren().clear();
		
		
		System.out.println("totaalfavortokens = " + favortokens);
		
		HBox hbox = new HBox();
		hbox.setSpacing(SPACING);
		hbox.setAlignment(Pos.CENTER);
		
		Text favortoken = new Text();
		favortoken.setText("Betaal stenen = " + favortokens);
		favortoken.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, TEXT_SIZE));
		
		hbox.getChildren().add(favortoken);	
		
		for(int i = 0; i < favortokens; i++) {
			Circle circle = new Circle(CIRCLE_SIZE,Color.DARKSLATEBLUE);
			hbox.getChildren().add(circle);	
		}
		
		favortokenView.setCenter(hbox);
	}

	public boolean checkPlacementAgainstRules(int x, int y, StackPane stackpane) {
		if (patterncardController.checkPlacementAgainstRules(x, y, patterncardController.getSelectedDieColor(),
				patterncardController.getSelectedDieValue())) {
			placeSelectedDie(x, y, stackpane);
			return true;
		} else {
			return false;
		}
	}
}
