package view;

import controller.GameController;
import database.DataBaseConnection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Game;

public class Main extends Application  {
	DataBaseConnection conn = new DataBaseConnection("com.mysql.jdbc.Driver");
	Game game = new Game(conn, false);
	private GameController gameController = new GameController(conn, null, game);
	private PatterncardSelectionView patterncardSelectionView = new PatterncardSelectionView(gameController);
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(gameController.getGameView());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sagrada - Projectgroep B");
		Image icon = new Image("/images/SagradaIcon.png");
		primaryStage.getIcons().add(icon);
		primaryStage.show();
		primaryStage.setResizable(true);
		primaryStage.setMinHeight(600);
		primaryStage.setMinWidth(800);
		
		
//		Scene scene = new Scene(patterncardSelectionView);
//		primaryStage.setScene(scene);
//		primaryStage.setTitle("Sagrada - Projectgroep B");
//		Image icon = new Image("/images/SagradaIcon.png");
//		primaryStage.getIcons().add(icon);
//		primaryStage.show();
	}
	
	@Override
	public void stop() {
		Platform.exit();
		System.exit(0);
	}
}
