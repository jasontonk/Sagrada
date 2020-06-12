package view;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;

public class ToolcardView extends BorderPane {

	private Button toolcard = new Button();
	
	public ToolcardView(int id) {
		System.out.println("TOOL CARD OF GAME");
		
		String imgURL = "/images/toolcard" + id + ".png";
		System.out.println("IMGURL " + imgURL);
		Image image = new Image(getClass().getResource(imgURL).toString());
		toolcard.setMinSize(220, 320);
		toolcard.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1, 1, false, false, false, true))));
		toolcard.setOnMouseClicked((e)-> System.out.println("big pog"));
		this.setCenter(toolcard);
		
	}

}
	

