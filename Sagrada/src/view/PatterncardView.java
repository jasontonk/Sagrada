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
import javafx.scene.text.Text;
import model.ModelColor;

public class PatterncardView extends VBox{

	private PatterncardController patterncardController;
	private JavafxColor javafxColor = new JavafxColor();;
	private final int PATTERNCARDFIELD_SIZE = 50;
	private final double GRIDSPACING = 5.0;
	private Insets padding = new Insets(5);
	private final int[] xPos = new int[] {0,1,2,3,4};
	private final int[] yPos = new int[] {0,1,2,3};
	
	private String imgURL;
	
	public PatterncardView(PatterncardController patterncardController) {
		this.patterncardController = patterncardController;
		this.setPadding(new Insets(0, 30, 0, 30));
		this.getChildren().addAll(drawTitle(), drawPatterncard(), drawDifficulty());
	}
	
	public GridPane drawPatterncard() {
		GridPane patterncardfields = new GridPane();
		patterncardfields.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
		patterncardfields.setMinSize((5 * (PATTERNCARDFIELD_SIZE + GRIDSPACING) + GRIDSPACING), (4 * (PATTERNCARDFIELD_SIZE + GRIDSPACING) + GRIDSPACING));
		patterncardfields.setPadding(padding);
		patterncardfields.setHgap(GRIDSPACING);
		patterncardfields.setVgap(GRIDSPACING);
		
		for(int x = 0; x < 5; x++) {
			for (int y = 0; y < 4; y++) {
				StackPane stackpane = new StackPane();
				Button button = new Button();
				button.setPrefSize(PATTERNCARDFIELD_SIZE, PATTERNCARDFIELD_SIZE);
				
				String imgURL;
				
				ModelColor modelColor = patterncardController.getFieldColor(x, y);
				Color color = javafxColor.getJavafxColor(modelColor);
				int value = patterncardController.getFieldValue(x, y);
				
				if(color == null && value == 0) {
					button.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));	
				}
				else if(modelColor != null) {
					button.setBackground(new Background(new BackgroundFill(color, null, null)));
				}
				else if(value != 0) {
					imgURL = "/images/" + Integer.toString(value) + "_fieldValue.jpg";
					Image image = new Image(getClass().getResource(imgURL).toString());
					button.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1, 1, false, false, false, true))));
				}
				button.setOnMouseClicked(e-> checkPlacementAgainstRules(xPos[0], yPos[1], stackpane));
				stackpane.getChildren().add(button);
				patterncardfields.add(stackpane, x, y);
			}
		}
		return patterncardfields;
	}
	private void placeSelectedDie(StackPane stackpane) {
		Button die = new Button();
		
		imgURL = patterncardController.getSelectedDieUrl();
		System.out.println(imgURL);
		if(imgURL != null) {
			Image image = new Image(getClass().getResource(imgURL).toString());
			die.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1, 1, false, false, false, true))));
			die.setPrefSize(40, 40);
			stackpane.getChildren().add(die);
			patterncardController.deleteDieFromPool();
		}
		else {
			System.out.println("no die selected");
			//TODO make popup field for errors
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
	public boolean checkPlacementAgainstRules(int x, int y, StackPane stackpane) {
		if(patterncardController.checkPlacementAgainstRules(x, y, patterncardController.getSelectedDieColor(), patterncardController.getSelectedDieValue())) {
			placeSelectedDie(stackpane);
			return true;
		}
		else {
			System.out.println("unable to place die due to rules");
			return false;
		}
	}
	
	
}
