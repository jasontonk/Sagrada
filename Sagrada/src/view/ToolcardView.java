package view;

import controller.GameController;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class ToolcardView extends BorderPane {

	private Button toolcard = new Button();
	private GameController gamecontroller;
	
	public ToolcardView(int id, GameController gamecontroller) {
		this.gamecontroller = gamecontroller;
		
		String imgURL = "/images/toolcard" + id + ".png";
		System.out.println("IMGURL " + imgURL);	Image image = new Image(getClass().getResource(imgURL).toString());
		toolcard.setMinSize(200, 270);
		toolcard.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1, 1, false, false, false, true))));
		toolcard.setOnMouseClicked((e)-> setSelectedToolcard(id));
		this.setCenter(toolcard);
	}
	
	public void setSelectedToolcard(int id) {
		gamecontroller.removeAllBordersFromToolcard();
		setBorder();
		gamecontroller.setSelectedToolcard(id);
		gamecontroller.selectedToolCard(id,this);
	}
	
	public void setBorder(){
		toolcard.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
	}
	public void removeBorder() {
		toolcard.setBorder(null);
	}
}
	

