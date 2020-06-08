package view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
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
        setPrefSize(200, 100);
        setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
        makeScoreBoard();
    }

	public void makeScoreBoard() {
		getChildren().clear();
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
            
            Label playerNameLabel =
                    new Label(playerName + ": ");
            playerNameLabel.setTextAlignment(TextAlignment.CENTER);
            
            Label playerScore = new Label();
            playerScore.textProperty().bind(game.getPlayers().get(i).getScore().asString());
            
            playerScore.setTextAlignment(TextAlignment.CENTER);
            playerLine.getChildren().addAll(playerNameLabel, playerScore);
            playerLine.setAlignment(Pos.CENTER);
            scoreLines.add(i, playerLine);
        }
        scoreBoardContent.getChildren().addAll(scoreLines);
        setTop(scoreBoardTitlePane);
        setCenter(scoreBoardContent);
        
    }

}
