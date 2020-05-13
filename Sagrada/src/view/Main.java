package view;

import controller.DieController;
import controller.GameController;
import controller.PatterncardController;
import controller.RoundtrackController;
import database.DataBaseConnection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Game;

public class Main extends Application  {
	DataBaseConnection conn = new DataBaseConnection("com.mysql.jdbc.Driver");
	private GameController gameController = new GameController(conn);
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(gameController.getGameView());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
