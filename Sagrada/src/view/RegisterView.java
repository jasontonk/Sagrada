package view;

import controller.AccountController;
import database.DataBaseConnection;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class RegisterView extends Pane {

	private final int BUTTON_WIDTH = 100;
	private final int BUTTON_HEIGHT = 50;
	private final int TEXTFIELD_WIDTH = 200;
    private final int TEXTFIELD_HEIGHT = 25;
    private AccountController accountController;
    private Pane registerPane;
    
	public RegisterView(AccountController accountController) {
		this.accountController = accountController;
	}
	
	public Pane makeRegisterPane() {
		this.getChildren().clear();
		VBox rp = new VBox();
		registerPane = rp;
		
		registerPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
		
		BorderPane textPane = new BorderPane();
		Text text = new Text("Registreer");
		text.setStyle("-fx-font: 24 arial;");
		textPane.setCenter(text);
		
		VBox loginFields = new VBox();
		Label usernameLabel = new Label("Gebruikersnaam");
		TextField usernameTextField = new TextField();
		usernameTextField.setPromptText("Gebruikersnaam");
		usernameTextField.setMaxSize(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
		
		Label passwordLabel = new Label("Wachtwoord");
		PasswordField passwordTextField = new PasswordField();
		passwordTextField.setPromptText("Wachtwoord");
		passwordTextField.setMaxSize(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
		
		loginFields.getChildren().addAll(usernameLabel, usernameTextField, passwordLabel, passwordTextField);
		
		Button register = new Button("Maak account");
		register.setOnAction(e -> accountController.actionRegister(usernameTextField.getText(), passwordTextField.getText()));
		
		Button back = new Button("Terug");
		back.setOnAction(e -> accountController.viewChoose());
		
		HBox yesno = new HBox();
		yesno.getChildren().addAll(register,back);
		yesno.setAlignment(Pos.CENTER); 

		register.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		back.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		
		registerPane.getChildren().addAll(textPane, loginFields, yesno);
		
		
		return registerPane;
	}
	
	public void addError() {
		Text text = new Text("Gebruikersnaam is al bezet");
		text.setStyle("-fx-font: 24 arial;");
		text.setFill(Color.RED);
		registerPane.getChildren().add(text);
		this.getChildren().addAll(registerPane);
		System.out.println("test");
		
	}
}
