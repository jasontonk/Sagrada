package view;
import controller.AccountController;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LoginView extends VBox {
	
	private AccountController accountController;
	private Pane choosePane;
	private Pane loginPane;
	private Pane registerPane;
	private final int BUTTON_WIDTH = 100;
	private final int BUTTON_HEIGHT = 50;
	private final int TEXTFIELD_WIDTH = 200;
    private final int TEXTFIELD_HEIGHT = 25;
	
	public LoginView(AccountController accountController) {
		this.accountController = accountController;
		this.makeChoosePane();
		this.setPrefSize(800, 600);
	}
	
	private void makeChoosePane() {
		this.getChildren().clear();
		VBox cp = new VBox();
		choosePane = cp;
		choosePane.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
		
		BorderPane textPane = new BorderPane();
		Text text = new Text("Heb je een account?");
		text.setStyle("-fx-font: 24 arial;");
		textPane.setCenter(text);
		
		Button yes = new Button("Ja");
		Button no = new Button("Nee");
		
		yes.setOnAction(e -> makeLoginPane());
		no.setOnAction(e -> makeRegisterPane());
		
		HBox yesno = new HBox();
		yesno.getChildren().addAll(yes,no);
		yesno.setAlignment(Pos.CENTER);
		
		choosePane.getChildren().addAll(textPane, yesno);
		
		yes.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		no.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		this.getChildren().addAll(choosePane);
		
	}
	
	private void makeLoginPane() {
		this.getChildren().clear();
		VBox lp = new VBox();
		loginPane = lp;
		loginPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
		
		BorderPane textPane = new BorderPane();
		Text text = new Text("Login");
		text.setStyle("-fx-font: 24 arial;");
		textPane.setCenter(text);
		
		VBox loginFields = new VBox();
		Label usernameLabel = new Label("Gebruikersnaam");
		TextField usernameTextField = new TextField();
		usernameTextField.setPromptText("Gebruikersnaam");
		usernameTextField.setMaxSize(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
		
		Label passwordLabel = new Label("Wachtwoord");
		TextField passwordTextField = new TextField();
		passwordTextField.setPromptText("Wachtwoord");
		passwordTextField.setMaxSize(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
		
		loginFields.getChildren().addAll(usernameLabel, usernameTextField, passwordLabel, passwordTextField);
		
		Button login = new Button("Login");
		login.setOnAction(e -> accountController.actionLogin(usernameTextField.getText(), passwordTextField.getText()));
		
		Button back = new Button("Terug");
		back.setOnAction(e -> makeChoosePane());
		
		HBox yesno = new HBox();
		yesno.getChildren().addAll(login,back);
		yesno.setAlignment(Pos.CENTER);
		
		loginPane.getChildren().addAll(textPane, loginFields, yesno);
		
		login.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		back.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		this.getChildren().addAll(loginPane);
	}
	
	private void makeRegisterPane() {
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
		TextField passwordTextField = new TextField();
		passwordTextField.setPromptText("Wachtwoord");
		passwordTextField.setMaxSize(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
		
		loginFields.getChildren().addAll(usernameLabel, usernameTextField, passwordLabel, passwordTextField);
		
		Button register = new Button("Maak account");
		register.setOnAction(e -> accountController.actionRegister(usernameTextField.getText(), passwordTextField.getText()));
		
		Button back = new Button("Terug");
		back.setOnAction(e -> makeChoosePane());
		
		HBox yesno = new HBox();
		yesno.getChildren().addAll(register,back);
		yesno.setAlignment(Pos.CENTER); 
		 //test
		
		registerPane.getChildren().addAll(textPane, loginFields, yesno);
		
		register.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		back.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		this.getChildren().addAll(registerPane);
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
