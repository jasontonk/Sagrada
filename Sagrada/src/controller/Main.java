package controller;

import database.DataBaseConnection;
import javafx.application.Application;
import javafx.stage.Stage;
import view.AccountView;
import view.LoginView;
import view.MyScene;

public class Main extends Application  {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		DataBaseConnection db = new DataBaseConnection("com.mysql.jdbc.Driver");
		MyScene scene = new MyScene(new AccountView(new Acc ountController(db)));
		MyScene scene1 = new MyScene(new LoginView(new AccountController(db)));
		primaryStage.setScene(scene1);
		primaryStage.setWidth(800);
		primaryStage.setHeight(600);
		primaryStage.show();
	}
}
