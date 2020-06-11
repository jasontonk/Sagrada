package view;

import controller.AccountController;
import database.DataBaseConnection;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ChooseView extends Pane{

	private final int BUTTON_WIDTH = 100;
	private final int BUTTON_HEIGHT = 50;
	private Pane choosePane;
	private AccountController accountController;
	
	public ChooseView(AccountController accountController) {
		this.accountController = accountController;
	}
	
	public Pane makeChoosePane() {
		choosePane = new VBox();
		choosePane.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));

		
//		choosePane.setBackground(new Background(new BackgroundImage(new Image(getClass().getResource(url).toString()), null, null, null, null)));
		String url = "/images/loginbackground.jpg";
		Image image = new Image(url);
		choosePane.setBackground(new Background(new BackgroundImage(image, null, null, null, null)));
		
		BorderPane textPane = new BorderPane();
		Text text = new Text("Heb je een account?");
		text.setStyle("-fx-font: 34 arial;");
		text.setFill(Color.WHITE);
		
		textPane.setCenter(text);
		
		Button yes = new Button("Ja");
		yes.setMinWidth(150);
		yes.setMinHeight(75);
		Button no = new Button("Nee");
		no.setMinWidth(150);
		no.setMinHeight(75);
		
		yes.setOnAction(e -> accountController.viewLogin());
		no.setOnAction(e -> accountController.viewRegister());
		
		
		
		HBox yesno = new HBox();
		yesno.getChildren().addAll(yes,no);
		yesno.setAlignment(Pos.CENTER);
		
		yes.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		no.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		
		String url1 = "/images/regels1.jpg";
		Image image1 = new Image(url1);
		ImageView imageView1 = new ImageView(image1);
		
		String url2 = "/images/regels2.jpg";
		Image image2 = new Image(url2);
		ImageView imageView2 = new ImageView(image2);

		String url3 = "/images/regels3.jpg";
		Image image3 = new Image(url3);
		ImageView imageView3 = new ImageView(image3);
		
		String url4 = "/images/regels4.jpg";
		Image image4 = new Image(url4);
		ImageView imageView4 = new ImageView(image4);
		
		VBox rulesView = new VBox(imageView1, imageView2, imageView3, imageView4);
		ScrollPane scrollPane = new ScrollPane(rulesView);
		scrollPane.setMaxWidth(720);
		BorderPane centerScroll = new BorderPane();
		centerScroll.setCenter(scrollPane);
		choosePane.getChildren().addAll(textPane, yesno, centerScroll);
		return choosePane;
	}

	
}
