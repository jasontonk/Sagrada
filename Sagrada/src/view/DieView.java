package view;

import controller.DieController;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;



public class DieView extends BorderPane {

	private DieController dieController;
	private final int DIE_SIZE = 60;
	private int diePoolID;

	
	
	public DieView(DieController dieController, String color, int value, int dieID) {
		this.dieController = dieController;
		this.diePoolID = dieID;
		Button die = new Button();
		String imgURL;
		die.setMinSize(DIE_SIZE, DIE_SIZE);
		
		imgURL = "/images/" + color + value + "_Die.png";
		System.out.println(imgURL);
		Image image = new Image(getClass().getResource(imgURL).toString());
		die.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1, 1, false, false, false, true))));
		
		die.setOnMouseClicked((e)-> setSelectedDie(imgURL));
		
		this.setMinSize(DIE_SIZE, DIE_SIZE);
		this.setCenter(die);
	}
	public void setDieID(int ID) {
		this.diePoolID = ID;	
	}
	public void decreaseDiePoolID() {
		diePoolID--;
	}
	private void setSelectedDie(String imgURL) {
		dieController.setSelectedDie(diePoolID, imgURL);
	}

}
