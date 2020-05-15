package controller;

import java.util.ArrayList;
import java.util.Optional;

import database.DataBaseConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import model.Account;
import view.ChooseView;
import view.LobbyView;
import view.LoginView;
import view.MyScene;
import view.RegisterView;

public class AccountController {

	private Account account;
	private LoginView loginView;
	private RegisterView registerView;
	private ChooseView chooseView;
	private DataBaseConnection connection;
	private LobbyView lobbyView;
	private MyScene myScene;
	
	public AccountController(DataBaseConnection c, MyScene myScene) {
		this.connection = c;
		this.myScene = myScene;
		loginView = new LoginView(this);
		registerView = new RegisterView(this);
		chooseView = new ChooseView(this);
		lobbyView = new LobbyView(this);
		account = new Account(c);
		// TODO Auto-generated constructor stub
	}
	
	public void actionLogin(String username, String password) {
		if(account.accountExists(username)) {
			if(password.equals(account.getPassword(username))) {
				viewLobby();
			}
			else {
				showWarning("wachtwoord", "Dit is niet het juiste wachtwoord");
			}
		}
		else {
			showWarning("gebruikersnaam", "Dit is niet de juiste gebruikersnaam");
		}
	}  
 
	public void actionRegister(String username, String password) {
		if(account.accountExists(username)) {
			showWarning("gebruikersnaam", "gebruikersnaam is al bezet");		
		} else if(password.length() < 3) {
			showWarning("wachtwoord", "Wachtwoord moet minimaal 3 tekens zijn");
		} else {
			account.setAccount(username, password);
			viewLobby();
		}
	}
	
	public ArrayList<Account> getAllAccounts() {
		return account.getAllAccounts();
	}
	
//	public ArrayList<Player> getAllPlayersOfThisAccount() {
//		return account.getPlayers();
//	}
	
	public void viewLogin() {
		myScene.setContentPane(loginView.makeLoginPane());
	}
	
	public void viewRegister() {
		myScene.setContentPane(registerView.makeRegisterPane());
	}
	
	public void viewChoose() {
		myScene.setContentPane(chooseView.makeChoosePane());
	}
	
	public void viewLobby() {
		myScene.setContentPane(lobbyView.makeAccountPane());
	}
	
	public void viewInvitation() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Invite");
		alert.setHeaderText("Deze speler wil je inviten voor een game");
		alert.setContentText("Wil je dit accepteren?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    // ... user chose OK
		} else {
		    // ... user chose CANCEL or closed the dialog
		}
	}
	
	public void showWarning(String header, String text) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Error");
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();
	}

}
