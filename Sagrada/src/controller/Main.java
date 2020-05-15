package controller;

import database.DataBaseConnection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.LobbyView;
import view.LoginView;
import view.MyScene;

public class Main extends Application  {
	

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		DataBaseConnection db = new DataBaseConnection("com.mysql.jdbc.Driver");
		MyScene scene = new MyScene(db);
		primaryStage.setScene(scene);
		primaryStage.setWidth(800);
		primaryStage.setHeight(600);
		primaryStage.setTitle("Sagrada - Projectgroep B");
		Image icon = new Image("/images/SagradaIcon.png");
		primaryStage.getIcons().add(icon);
		primaryStage.show();
	}
}
//test