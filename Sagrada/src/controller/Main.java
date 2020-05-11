package controller;

import database.DataBaseConnection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.AccountView;
import view.LoginView;

public class Main extends Application  {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		DataBaseConnection db = new DataBaseConnection("com.mysql.jdbc.Driver");
		Scene scene = new Scene(new AccountView(new AccountController(db)));
		Scene scene1 = new Scene(new LoginView(new AccountController(db)));
		primaryStage.setScene(scene1);
		primaryStage.setWidth(800);
		primaryStage.setHeight(600);
		primaryStage.show();
	}
}
//test