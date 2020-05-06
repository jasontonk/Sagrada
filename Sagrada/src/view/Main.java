package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = new Scene(new LoginView(null));
		stage.setScene(scene);
		stage.show();
		
	}

}
