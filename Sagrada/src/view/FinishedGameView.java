package view;

import java.util.ArrayList;

import controller.AccountController;
import controller.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Player;

public class FinishedGameView extends BorderPane {



	private VBox finishedGameView;
	private GameController gameController;
	
	public FinishedGameView(GameController gameController) {
		this.gameController = gameController;
		this.setCenter(makeFinishedGameView());;
	}
	
	public Pane makeFinishedGameView() {
		finishedGameView = new VBox();
		finishedGameView.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		finishedGameView.setPadding(new Insets(50));
		finishedGameView.setSpacing(15);

		
		BorderPane textPane = new BorderPane();
		ArrayList<String> playerInfo = gameController.getGame().getWinnerOfGameWithID(gameController.getGame().getGameID());
		
		Text winner = new Text(playerInfo.get(0) + " met een score van: " + playerInfo.get(1));
		winner.setStyle("-fx-font: 30 arial;");
		winner.setFill(Color.LIMEGREEN);
		
		String url = "/images/winner.jpg";
		Image image = new Image(url);
		ImageView imageView = new ImageView(image);
		
		imageView.setFitWidth(960);
		imageView.setFitHeight(540);
		
		VBox test = new VBox();
		test.getChildren().addAll(imageView, winner);
		textPane.setCenter(test);
		
		
		
		
		BorderPane leaderBoardPane = new BorderPane();
		Text title = new Text("Leaderboard:");
		
		title.setStyle("-fx-font: 20 arial;");
		title.setUnderline(true);
		title.setFill(Color.WHITE);
		
		VBox leaderBoard = new VBox();
		
		for(Player p : gameController.getGame().getPlayers()) {
			Text info = new Text("Speler: " + p.getName() + " heeft een score behaald van: " + p.getScore().get());
			info.setStyle("-fx-font: 20 arial; color: white;");
			info.setFill(Color.WHITE);
			leaderBoard.getChildren().add(info);
		}
		
		
		leaderBoardPane.setTop(title);
		leaderBoardPane.setCenter(leaderBoard);
		
		Button lobbyButton = new Button("Ga terug naar de lobby");
		lobbyButton.setOnAction(e -> gameController.setLobbyView());
		

		finishedGameView.getChildren().addAll(textPane, leaderBoardPane, lobbyButton);
		return finishedGameView;
	}

	

}
