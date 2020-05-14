package view;

import controller.DieController;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class DicePoolView extends GridPane {

	private DieView[] dieView;
	private DieController dieController;
	private final int DIE_SIZE = 60;
	private final double GRIDSPACING = 10.0;
	private Insets padding = new Insets(10);  
	
	public DicePoolView(DieController dieController) {
		dieView = new DieView[9];
		int number = 0;
		this.dieController = dieController;
		this.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
		this.setMinSize((3 * (DIE_SIZE + GRIDSPACING) + GRIDSPACING), (3 * (DIE_SIZE + GRIDSPACING) + GRIDSPACING));;
		this.setPadding(padding);
		this.setHgap(GRIDSPACING);
		this.setVgap(GRIDSPACING);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if(dieController.getDieColor(number) != null) {
					dieView[number] = new DieView(dieController, dieController.getDieColor(number), dieController.getDieValue(number), number);
					this.add(dieView[number],i,j);
					number++;
				}
			}
			
		}
	}
	
	//TODO Remove the right dieview on the right index Also set new indexes for the other dieviews when a dieview is removed
	public void deleteDie(int index) {
		System.out.println(index);
		this.getChildren().remove(index);
		for(int i = 0; i < this.getChildren().size()-index;i++) {
			if(this.getChildren().get(index + i) != null) {
				dieView[index+i].decreaseDiePoolID();
				//this.getChildren().get((index + i)).decreaseDiePoolID();
			}	
		}
	}
} 
