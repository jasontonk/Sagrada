package view;


import controller.DieController;
import controller.PatterncardController;
import database.DataBaseConnection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Game;

public class Main extends Application  {
	DataBaseConnection conn = new DataBaseConnection("com.mysql.jdbc.Driver");
	private DieController controller= new DieController(conn);
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(new RoundtrackView(null));
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
