package view;

import java.util.ArrayList;

import controller.AccountController;
import controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Player;

public class FinishedGameView extends Pane {



	private VBox finishedGameView;
	private GameController gameController;
	
	public FinishedGameView(GameController gameController) {
		this.gameController = gameController;
		this.getChildren().add(makeFinishedGameView());
	}
	
	public Pane makeFinishedGameView() {
		finishedGameView = new VBox();
		finishedGameView.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));

		
		BorderPane textPane = new BorderPane();
		ArrayList<String> playerInfo = gameController.getGame().getWinnerOfGameWithID(gameController.getGame().getGameID());
		
		Text winner = new Text("De winnaar van de game is: " + playerInfo.get(0) + " met een score van: " + playerInfo.get(1));
		textPane.setCenter(winner);
		
		
		BorderPane leaderBoardPane = new BorderPane();
		Text title = new Text("Leaderboard");
		VBox leaderBoard = new VBox();
		
		for(Player p : gameController.getGame().getPlayers()) {
			Text info = new Text("Speler: " + p.getName() + " heeft een score behaald van: " + p.getScore().get());
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
