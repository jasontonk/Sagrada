package view;

import controller.GameController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PatterncardSelectionView extends VBox {
	
	private PatterncardView[] patterncardView;
	private Insets padding = new Insets(5);
	
	
	public PatterncardSelectionView(GameController gameController) {
		patterncardView = new PatterncardView[4];
		for (int i = 0; i < patterncardView.length; i++) {
			patterncardView[i] = new PatterncardView(gameController.getPatterncardController());
		}
		this.getChildren().addAll(drawTitle(),drawPatterncards());
	}


	private GridPane drawPatterncards() {
		GridPane gridpane = new GridPane();
		for (int i = 0; i < patterncardView.length; i++) {
			gridpane.add(patterncardView[i], i, 1);
		}
		for (int i = 0; i < patterncardView.length; i++) {
			Button button = new Button();
			button.setText("Selecteer mij!");
			gridpane.add(button, i, 2);
		}
		return gridpane;
	}


	private BorderPane drawTitle() {
		BorderPane titlePane = new BorderPane();
		Text title = new Text();
		title.setText("Selecteer een Patroonkaart om het spel mee te spelen.");
		titlePane.setPadding(padding);
		titlePane.setCenter(title);
		return titlePane;
	}
	
}
