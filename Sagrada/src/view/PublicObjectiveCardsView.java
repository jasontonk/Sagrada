package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import controller.GameController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class PublicObjectiveCardsView extends HBox {
	
	private GameController gameController;
	private int[] objectivecardIDs;
	
	public PublicObjectiveCardsView(int[] objectivecardIDs) {
		this.objectivecardIDs = objectivecardIDs;
		drawPublicObjectiveCards();
	}

	private void drawPublicObjectiveCards() {
		System.out.println("PUBLIC OBJECTIVE CARDS OF GAME: " + objectivecardIDs);
		for (int i = 0; i < objectivecardIDs.length; i++) {
			String imgURL = "/images/publicObjectiveCard" + objectivecardIDs[i] + ".png";
			System.out.println("IMGURL " + imgURL);
			Image image = new Image
				(getClass().getResource(imgURL).toString()); 
			
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(200);
			imageView.setFitHeight(270);
			this.getChildren().add(imageView);
		}
		
		
	}

}
