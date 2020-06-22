package view;

import controller.PatterncardController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.BoardField;
import model.ModelColor;
import model.Player;

public class PatternCardsOfOtherPlayersView extends VBox {

	private JavafxColor javafxColor = new JavafxColor();;
	private final int PATTERNCARDFIELD_SIZE = 50;
	private final double GRIDSPACING = 5.0;
	private Insets padding = new Insets(5);
	private VBox allPatternCards;
	private StackPane[][][] stackpanes;
	private PatterncardController patterncardController;

	private String imgURL;

	public PatternCardsOfOtherPlayersView(PatterncardController patterncardController) {
		this.setPadding(new Insets(10));
		this.patterncardController = patterncardController;
		stackpanes = new StackPane[500][5][4];
		makePatternCards();
	}

	public void makePatternCards() {
		allPatternCards = new VBox();
		boolean allPlayersHavePatternCards = true;
		for(Player player : patterncardController.getGameController().getGame().getPlayers()) {
			if(player.getPatternCard() == null) {
				allPlayersHavePatternCards = false;
				break;
			}
		}
		for(Player player : patterncardController.getGameController().getGame().getPlayers()) {
			if(!player.isDrawnPatternCard()) {
				System.out.println("EERSTE IF");
				if(player.getId() != patterncardController.getGameController().getGame().getPersonalPlayer().getId()) {
					
					allPatternCards.getChildren().add(drawPatterncard(player, allPlayersHavePatternCards));
				}
			}
			else {
				System.out.println("IN DE ELSE");
				for (int x = 0; x < 5; x++) {
					for (int y = 0; y < 4; y++) {
						updatePatternCards(player, x, y);
					}
				}
			}
		}
		
		this.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, null, null)));
		this.getChildren().addAll(allPatternCards);
	}
	
	public VBox drawPatterncard(Player player, boolean allPlayersHavePatternCards) {
		
		this.getChildren().clear();
		VBox vbox = new VBox();
		GridPane patterncardfields = new GridPane();
		patterncardfields.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		patterncardfields.setMinSize((5 * (PATTERNCARDFIELD_SIZE + GRIDSPACING) + GRIDSPACING),
				(4 * (PATTERNCARDFIELD_SIZE + GRIDSPACING) + GRIDSPACING));
		patterncardfields.setPadding(padding);
		patterncardfields.setHgap(GRIDSPACING);
		patterncardfields.setVgap(GRIDSPACING);
		 
		Label label = new Label("Bord van speler: " + player.getName());
		label.setFont(new Font(15));
		label.setTextFill(Color.WHITE);
		
		if(player.getPatternCard() != null && allPlayersHavePatternCards) {
			player.setDrawnPatternCard(true);
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 4; y++) {
					StackPane stackpane = new StackPane();
					Button button = new Button();
					button.setPrefSize(PATTERNCARDFIELD_SIZE, PATTERNCARDFIELD_SIZE);
					int xPos = x;
					int yPos = y;
					String imgURL;
										
					ModelColor modelColor = player.getPatternCard().getFieldColorFromDB(xPos, yPos);
	
					Color color = javafxColor.getJavafxColor(modelColor);
					
					int value = player.getPatternCard().getFieldValueFromDB(xPos, yPos);
					
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
					stackpanes[player.getId()][x][y] = stackpane;
//					updatePatternCards(player, x, y);
					patterncardfields.add(stackpane, x, y);
				}
			}
			vbox.setSpacing(5);
			vbox.setPadding(new Insets(0, 0, 0, 5));
			vbox.getChildren().addAll(label, patterncardfields);
		}
		else {
			Label label2 = new Label("Niet elke speler heeft een patterncard gekozen");
			label2.setFont(new Font(15));
			label2.setTextFill(Color.RED);
			
			vbox.setSpacing(5);
			vbox.setPadding(new Insets(0, 0, 0, 5));
			vbox.getChildren().addAll(label, label2);
		}
		return vbox;
	}
	
	public void updatePatternCards(Player player, int x, int y) {
		BoardField boardField = player.getPlayerFrameFieldDBA().getPlayerFrameField(player, x, y);
		if (boardField.hasDie()) {
			Button die = new Button();
			imgURL = "/images/"
					+ boardField.getDie().getColor().toString()
					+ boardField.getDie().getEyes()
					+ "_Die.png";

			if (imgURL != null) {
				Image image = new Image(getClass().getResource(imgURL).toString());
				die.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
						new BackgroundSize(1, 1, false, false, false, true))));
				die.setPrefSize(40, 40);
				stackpanes[player.getId()][x][y].getChildren().add(die);
			}
		}
	}
}
