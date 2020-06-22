package view;

import controller.AccountController;
import database.DataBaseConnection;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class MyScene extends Scene {

	private Pane contentPane;
	private AccountController accountController;

	public MyScene(DataBaseConnection db) {
		super(new Pane());
		accountController = new AccountController(db, this);

		contentPane = new Pane();

		setRoot(contentPane);
		accountController.viewChoose();
	}

	public void setContentPane(Pane pane) {
		contentPane.getChildren().clear();
		contentPane = pane;
		setRoot(contentPane);
	}

	public void exit() {
		Platform.exit();
	}
}
