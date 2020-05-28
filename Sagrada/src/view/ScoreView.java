package view;

import java.util.ArrayList;

import controller.CalculateScoreTask;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import model.Game;
import model.Player;

public class ScoreView extends BorderPane {

	private Game game;
	 
	public ScoreView (Game game) {
        this.game = game;
        setPrefSize(100, 100);
        setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        makeScoreBoard();
    }

	private void makeScoreBoard() {
        VBox scoreBoardContent = new VBox();
        ArrayList<HBox> scoreLines = new ArrayList<HBox>();

        StackPane scoreBoardTitlePane = new StackPane();
        Label titleScoreBoard = new Label("Scorebord");
        titleScoreBoard.setTextAlignment(TextAlignment.CENTER);
        scoreBoardTitlePane.getChildren().add(titleScoreBoard);
        scoreBoardTitlePane.setAlignment(Pos.CENTER);

        for (int i = 0; i < game.getPlayers().size(); i++) {
            Player player = game.getPlayers().get(i);
            HBox playerLine = new HBox();

            String playerName = game.getPlayers().get(i).getAccount().getUsername();
            String[] playerNameArray = playerName.split("");
            if (playerNameArray.length > 7) {
                playerName = "";
                for (int x = 0; x < playerNameArray.length; x++) {
                    playerName = playerName + playerNameArray[x];
                    if (x == 7) {
                        break;
                    }
                }
            }
            Label playerNameLabel =
                    new Label(playerName + ": ");
            playerNameLabel.setTextAlignment(TextAlignment.CENTER);
            
            Label playerScore = new Label();
            
            CalculateScoreTask cst = new CalculateScoreTask(player);
            cst.setOnSucceeded(e -> {
                playerScore.setText("" + cst.getValue());
                playerScore.setTextAlignment(TextAlignment.CENTER);
                playerLine.getChildren().addAll(playerNameLabel, playerScore);
                playerLine.setAlignment(Pos.CENTER_LEFT);
                playerLine.setMargin(playerNameLabel, new Insets(10));
            });
            Thread thread = new Thread(cst);
            thread.setName("Calculate score");
            thread.setDaemon(true);
            thread.start();
            scoreLines.add(i, playerLine);
        }
        scoreBoardContent.getChildren().addAll(scoreLines);
        setTop(scoreBoardTitlePane);
        setCenter(scoreBoardContent);
    }

}
