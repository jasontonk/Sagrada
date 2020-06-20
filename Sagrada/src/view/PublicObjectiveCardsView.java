package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class PublicObjectiveCardsView extends HBox {
	
	private int[] objectivecardIDs;
	
	public PublicObjectiveCardsView(int[] objectivecardIDs) {
		this.objectivecardIDs = objectivecardIDs;
		drawPublicObjectiveCards();
	}

	private void drawPublicObjectiveCards() {
		for (int i = 0; i < objectivecardIDs.length; i++) {
			String imgURL = "/images/publicObjectiveCard" + objectivecardIDs[i] + ".png";
			Image image = new Image
				(getClass().getResource(imgURL).toString()); 
			
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(200);
			imageView.setFitHeight(270);
			this.getChildren().add(imageView);
		}
	}
}
