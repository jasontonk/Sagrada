package view;

import controller.AccountController;
import database.DataBaseConnection;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class MyScene extends Scene {

	private Pane rootPane;
    private Pane contentPane;
    private AccountController accountController;
    
    
    
	public MyScene(DataBaseConnection db) {
		super(new Pane());
		accountController =  new AccountController(db, this);
		
//		rootPane = new StackPane();
        contentPane = new Pane();
        contentPane.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
//		
//		rootPane.getChildren().add(contentPane);
        setRoot(contentPane);
        accountController.viewChoose();
	}

	public void setContentPane(Pane pane) {
		contentPane.getChildren().clear();
        contentPane = pane;
        setRoot(contentPane);
	}
}
