package view;

import controller.PatterncardController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class PatterncardView extends VBox{

	private PatterncardController patterncardController;
	private final int PATTERNCARDFIELD_SIZE = 40;
	private final double GRIDSPACING = 5.0;
	private Insets padding = new Insets(5); 
	
	public PatterncardView(PatterncardController patterncardController) {
		this.patterncardController = patterncardController;
//		this.setPrefSize(800, 600);
		this.getChildren().addAll(drawTitle(), drawPatterncard(), drawDifficulty());
	}
	
	public GridPane drawPatterncard() {
		GridPane patterncardfields = new GridPane();
		patterncardfields.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
		patterncardfields.setPrefSize((5 * (PATTERNCARDFIELD_SIZE + GRIDSPACING) + GRIDSPACING), (4 * (PATTERNCARDFIELD_SIZE + GRIDSPACING) + GRIDSPACING));
		patterncardfields.setPadding(padding);
		patterncardfields.setHgap(GRIDSPACING);
		patterncardfields.setVgap(GRIDSPACING);
		
		for(int x = 0; x < 5; x++) {
			for (int y = 0; y < 4; y++) {
				Button button = new Button();
				button.setPrefSize(PATTERNCARDFIELD_SIZE, PATTERNCARDFIELD_SIZE);
//				button.setOnAction(e-> );
				
				Color color = patterncardController.getFieldColor(x, y);
				int value = value
				
				if(patterncardController.getFieldColor(x, y) == null && patterncardController.getFieldValue(x, y) == 0) {
					button.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));	
				}
				else if(patterncardController.getFieldColor(x, y) != null) {
					button.set
				}
				else if(patterncardController.getFieldValue(x, y) != 0) {
					
				}
				
				patterncardfields.add(button, x, y);
			}
		}
		
		
		
		
		
		
		return patterncardfields;
	}
	public BorderPane drawTitle() {
		BorderPane titlePane = new BorderPane();
//		Text title = new Text(patterncardController.getName());
		Text title = new Text("Test");
		titlePane.setPadding(padding);
		titlePane.setCenter(title);
		return titlePane;
	}
	public BorderPane drawDifficulty() {
		BorderPane difficultyPane = new BorderPane();
//		Text difficulty = new Text("Difficulty =" + patterncardController.getDifficulty());
		Text difficulty = new Text("Difficulty = 6");
		difficultyPane.setPadding(padding);
	    difficultyPane.setRight(difficulty);
	    return difficultyPane;
	}
	
	
}
