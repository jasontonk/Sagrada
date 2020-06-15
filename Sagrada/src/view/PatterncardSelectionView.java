package view;

import java.util.ArrayList;

import controller.AccountController;
import controller.GameController;
import controller.PatterncardController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.PatternCard;
import model.Player;

public class PatterncardSelectionView extends VBox {
	
	private ArrayList<PatterncardView> patterncardView;
	private ArrayList<PatterncardController> patterncardControllers;
	private Insets padding = new Insets(5);
	
	
	public PatterncardSelectionView(AccountController accountController, Player player) {
		patterncardView = new ArrayList<PatterncardView>();
		patterncardControllers = new ArrayList<PatterncardController>();
		ArrayList<PatternCard> patterncardsToChoose = accountController.getPatternCardsToChoose(player);
		for (PatternCard patterncard : patterncardsToChoose) {
			patterncardControllers.add(new PatterncardController(patterncard));
		}
	
		for (int i = 0; i < 4; i++) {
			patterncardView.add(new PatterncardView(patterncardControllers.get(i)));
		}
		this.getChildren().addAll(drawTitle(),drawPatterncards());
	}


	private GridPane drawPatterncards() {
		GridPane gridpane = new GridPane();
		gridpane.setPadding(padding);
		gridpane.setVgap(10.0);
		for (int i = 0; i < patterncardView.size(); i++) {
			gridpane.add(patterncardView.get(i), i, 1);
			
			StackPane glass = new StackPane();
	        glass.setStyle("-fx-background-color: transparent;");
	        gridpane.add(glass, i, 1);
		}
		for (int i = 0; i < patterncardView.size(); i++) {
			Button button = new Button();
			button.setText("Selecteer mij!");
			button.setOnAction(e -> test());
			gridpane.add(button, i, 2);
			gridpane.setHalignment(button, HPos.CENTER);
		}
		return gridpane;
	}


	private void test() {
		System.out.println("patterncardview weergeven");
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
