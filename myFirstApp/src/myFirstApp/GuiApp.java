package myFirstApp;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GuiApp extends Application {
	
	public static void main(String[] args) {
		launch(args);
		}
		
		@Override
		public void start(Stage stage) throws Exception {
			stage.show();
			Pane panel = new Pane();
			panel.setBackground(new Background(new BackgroundFill(Color.DARKGRAY,null, new Insets(2))));
		
			
			BorderPane panel1 = new BorderPane();
			Scene scene = new Scene(panel,300,200,Color.CORNFLOWERBLUE);
			stage.setScene(scene);
			
			Button button = new Button();
			button.setText("Dit is een button");
			panel1.setCenter(button);
			
			button.setOnAction(e -> test());
		}
		
		@Override
		public void stop() throws Exception {
			System.out.println("Dit programma is beëindigd");
			super.stop();
		}
		
		public void test() {
			System.out.println("wieeeehh");
		}
		
		
	}

