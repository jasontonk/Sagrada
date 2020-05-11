package view;

import controller.PatterncardController;
import database.DataBaseConnection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application  {
	DataBaseConnection conn = new DataBaseConnection("com.mysql.jdbc.Driver");
	private PatterncardController controller= new PatterncardController(conn);
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(new PatterncardView(controller));
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
