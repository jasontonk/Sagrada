package view;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;

public class ToolcardView extends HBox {

	private int[] ToolcardIDs;
	private Button toolcard = new Button();
	
	public ToolcardView(int[] ToolcardIDs) {
		this.ToolcardIDs = ToolcardIDs;
		drawToolcards();
		
	}

	private void drawToolcards() {
		System.out.println("TOOL CARD OF GAME: " + ToolcardIDs);
		for (int i = 0; i < ToolcardIDs.length; i++) {
			String imgURL = "/images/toolcard" + ToolcardIDs[i] + ".png";
			System.out.println("IMGURL " + imgURL);
			Image image = new Image(getClass().getResource(imgURL).toString());
			toolcard.setMinSize(250, 320);
			toolcard.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1, 1, false, false, false, true))));
			
			toolcard.setOnMouseClicked((e)-> System.out.println("toolcard nummer: " + this.getId()));
			
			
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(250);
			imageView.setFitHeight(320);
			this.getChildren().add(imageView);
		}
	}

}
	

