package view;
import controller.AccountController;
import database.DataBaseConnection;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LoginView extends VBox {
	
	private AccountController accountController;
	
	private final int BUTTON_WIDTH = 150;
	private final int BUTTON_HEIGHT = 75;
	private final int TEXTFIELD_WIDTH = 200;
    private final int TEXTFIELD_HEIGHT = 25;
	private Pane loginPane;
	
	
	public LoginView(AccountController accountController) {
		this.accountController = accountController;
		this.setPrefSize(800, 600);
		makeLoginPane();
	}

	
	public Pane makeLoginPane() {
		this.getChildren().clear();
		VBox lp = new VBox();
		loginPane = lp;
		loginPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
		
		BorderPane textPane = new BorderPane();
		Text text = new Text("Login");
		text.setStyle("-fx-font: 34 arial;");
		text.setFill(Color.WHITE);
		textPane.setCenter(text);
		
		VBox loginFields = new VBox();
		Label usernameLabel = new Label("Gebruikersnaam");
		usernameLabel.setTextFill(Color.WHITE);
		usernameLabel.setStyle("-fx-font: 18 arial;");
		
		TextField usernameTextField = new TextField();
		usernameTextField.setPromptText("Gebruikersnaam");
		usernameTextField.setMaxSize(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
		
		Label passwordLabel = new Label("Wachtwoord");
		passwordLabel.setTextFill(Color.WHITE);
		passwordLabel.setStyle("-fx-font: 18 arial;");
		PasswordField passwordTextField = new PasswordField();
		passwordTextField.setPromptText("Wachtwoord");
		passwordTextField.setMaxSize(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
		
		loginFields.getChildren().addAll(usernameLabel, usernameTextField, passwordLabel, passwordTextField);
		
		Button login = new Button("Login");
		login.setOnAction(e -> accountController.actionLogin(usernameTextField.getText(), passwordTextField.getText()));
		
		login.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		
		
		Button back = new Button("Terug");
		back.setOnAction(e -> accountController.viewChoose());
		back.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		
		HBox yesno = new HBox();
		yesno.getChildren().addAll(login,back);
		yesno.setAlignment(Pos.CENTER);
		
		String url = "/images/loginbackground.jpg";
		Image image = new Image(url);
		loginPane.setBackground(new Background(new BackgroundImage(image, null, null, null, null)));
		
		loginPane.getChildren().addAll(textPane, loginFields, yesno);
		return loginPane;
	}
	
	
	
	
}
