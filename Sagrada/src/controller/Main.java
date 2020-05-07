package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.LoginView;

public class Main extends Application  {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(new LoginView(null));
		primaryStage.setScene(scene);
		primaryStage.setWidth(800);
		primaryStage.setHeight(600);
		primaryStage.show();
	}
}
