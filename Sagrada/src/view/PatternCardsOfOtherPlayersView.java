package view;

import controller.PatterncardController;
import javafx.geometry.Insets;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.GameDie;
import model.ModelColor;
import model.Player;

public class PatternCardsOfOtherPlayersView extends VBox {

	private PatterncardController patterncardController;
	private JavafxColor javafxColor = new JavafxColor();;
	private final int PATTERNCARDFIELD_SIZE = 50;
	private final double GRIDSPACING = 5.0;
	private Insets padding = new Insets(5);
	private VBox allPatternCards;
//	private final int[] xPos = new int[] {0,1,2,3,4};
//	private final int[] yPos = new int[] {0,1,2,3};

	private String imgURL;

	public PatternCardsOfOtherPlayersView(PatterncardController patterncardController) {
		this.patterncardController = patterncardController;
		this.setPadding(new Insets(0, 30, 0, 30));
		allPatternCards = new VBox();
		for(Player player : patterncardController.getGameController().getGame().getPlayers()) {
			if(!player.equals(patterncardController.getGameController().getGame().getPersonalPlayer())) {
				allPatternCards.getChildren().add(drawPatterncard(player));
			}
		}
		this.getChildren().addAll(allPatternCards);
	}

	public GridPane drawPatterncard(Player player) {
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
				
				
				ModelColor modelColor = patterncardController.getPatterncard().getFieldColorFromDB(xPos, yPos);

				Color color = javafxColor.getJavafxColor(modelColor);
				
				int value = patterncardController.getPatterncard().getFieldValueFromDB(xPos, yPos);
				
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
				stackpane.getChildren().add(button);
				
				
				
				
				
				if (patterncardController.getGameController() != null && patterncardController.getGameController()
						.getGame().getPersonalPlayer().getBoard().getBoardFieldFromDB(xPos, yPos).hasDie()) {
					//TODO
					
					
					
					
					Button die = new Button();
					imgURL = "/images/"
							+ patterncardController.getGameController().getGame().getPersonalPlayer().getBoard()
									.getBoardField(xPos, yPos).getDie().getColor().toString()
							+ patterncardController.getGameController().getGame().getPersonalPlayer().getBoard()
									.getBoardField(xPos, yPos).getDie().getEyes()
							+ "_Die.png";
					//TODO
					
					
					
					
					
					
					if (imgURL != null) {
						Image image = new Image(getClass().getResource(imgURL).toString());
						die.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
								BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
								new BackgroundSize(1, 1, false, false, false, true))));
						die.setPrefSize(40, 40);
						stackpane.getChildren().add(die);

					}
				}

				patterncardfields.add(stackpane, x, y);
			}
		}
		return patterncardfields;
	}

}
