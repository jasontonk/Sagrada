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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.ModelColor;

public class PatterncardView extends VBox{

	private PatterncardController patterncardController;
	private JavafxColor javafxColor = new JavafxColor();;
	private final int PATTERNCARDFIELD_SIZE = 40;
	private final double GRIDSPACING = 5.0;
	private Insets padding = new Insets(5);  
	
	public PatterncardView(PatterncardController patterncardController) {
		this.patterncardController = patterncardController;
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
				Button button = new Button();
				button.setPrefSize(PATTERNCARDFIELD_SIZE, PATTERNCARDFIELD_SIZE);
//				TODO button.setOnAction(e-> );
				
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
					//TODO get images
					imgURL = "/images/" + Integer.toString(value) + "_fieldValue.jpg";
					Image image = new Image(getClass().getResource(imgURL).toString());
					button.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1, 1, false, false, false, true))));
				}
				patterncardfields.add(button, x, y);
			}
		}
		return patterncardfields;
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
		difficulty.setText("Difficulty = " + patterncardController.getDifficulty());
		difficultyPane.setPadding(padding);
	    difficultyPane.setRight(difficulty);
	    return difficultyPane;
	}
	
	
}
