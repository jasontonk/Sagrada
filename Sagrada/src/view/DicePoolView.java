package view;

import java.util.ArrayList;

import controller.DieController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class DicePoolView extends VBox {

	private ArrayList<DieView> dieView;
	private DieController dieController;
	private final int DIE_SIZE = 60;
	private final double GRIDSPACING = 10.0;
	private Insets padding = new Insets(10);
	private GridPane dicePool = new GridPane();
	
	public DicePoolView(DieController dieController) {
		dieView = new ArrayList<>();
		int number = 0;
		this.dieController = dieController;
		dicePool.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
		dicePool.setMinSize((3 * (DIE_SIZE + GRIDSPACING) + GRIDSPACING), (3 * (DIE_SIZE + GRIDSPACING) + GRIDSPACING));
		dicePool.setMaxSize((3 * (DIE_SIZE + GRIDSPACING) + GRIDSPACING), (3 * (DIE_SIZE + GRIDSPACING) + GRIDSPACING));
		dicePool.setPadding(padding);
		dicePool.setHgap(GRIDSPACING);
		dicePool.setVgap(GRIDSPACING);
		this.setPadding(new Insets(0, 30, 0, 30));
		this.getChildren().addAll(drawTitle(), dicePool);
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if(dieController.getDieColor(number) != null) {
					dieView.add(new DieView(dieController, dieController.getDieColor(number), dieController.getDieValue(number), number));
					dicePool.add(dieView.get(number),i,j);
					number++;
				}
			}
			
		}
	}
	
	public BorderPane drawTitle() {
		BorderPane titlePane = new BorderPane();
		Text title = new Text();
		title.setText("Aanbod");
		titlePane.setPadding(padding);
		titlePane.setCenter(title);
		return titlePane;
	}
	
	//TODO Remove the right dieview on the right index Also set new indexes for the other dieviews when a dieview is removed
	public void deleteDie(int index) {
		System.out.println(index);
		for(int i = 0; i < dicePool.getChildren().size()-index;i++) {
			if(dicePool.getChildren().get(index + i) != null && index + 1 != 9) {
				dieView.get(index+i).decreaseDiePoolID();
				//this.getChildren().get((index + i)).decreaseDiePoolID();
			}	
		}
		dieView.remove(index);
		dicePool.getChildren().remove(index);
	}

	public void removeAllBorders() {
		for (int i = 0; i < dieView.size(); i++) {
			dieView.get(i).removeBorder();
			
		}
		
	}
} 
