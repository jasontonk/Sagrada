package controller;

import database.AccountDBA;
import database.DataBaseConnection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.AccountView;

public class Maintest extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataBaseConnection db = new DataBaseConnection("com.mysql.jdbc.Driver");
		AccountDBA ac = new AccountDBA(db);
		System.out.println(ac.addAccountDB("Matheus", "password"));
	
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = new Scene(new AccountView());
		stage.setScene(scene);
		stage.setWidth(800);
		stage.setHeight(600); 
		stage.show();
	} 

}
