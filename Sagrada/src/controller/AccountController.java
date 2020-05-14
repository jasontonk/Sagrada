package controller;

import java.util.ArrayList;

import database.DataBaseConnection;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.Account;
import model.Player;
import view.LobbyView;
import view.ChooseView;
import view.LoginView;
import view.MyScene;
import view.Player;
import view.RegisterView;

public class AccountController {

	private Account account;
	private LoginView loginView;
	private DataBaseConnection connection;
	private LobbyView accountView;
	private MyScene myScene;
	
	public AccountController(DataBaseConnection c, MyScene myScene) {
		this.connection = c;
		this.myScene = myScene;
		loginView = new LoginView(this);
		account = new Account(c);
		// TODO Auto-generated constructor stub
	}
	
	public void actionLogin(String username, String password) {
		if(account.accountExists(username)) {
			
			if(password.equals(account.getPassword(username))) {
				viewLobby();
			}
		}
		// TODO Auto-generated method stub
		else System.out.println("fout");
	}  //test
 
	public void actionRegister(String username, String password) {
		if(account.accountExists(username) || (password.length() < 3)) {
//			loginView.addError();
		} else {
			account.setAccount(username, password);
			viewLobby();
		}
	}
	
	public ArrayList<Account> getAllAccounts() {
		return account.getAllAccounts();
	}
	
	public void viewLogin() {
//		Pane pane = new Pane();
//		LoginView loginView = new LoginView(this);
//		pane.getChildren().add(loginView);
//		myScene.setContentPane(pane);
		
		LoginView loginView = new LoginView(this);
		myScene.setContentPane(loginView.makeLoginPane());
	}
	
	public void viewRegister() {
//		Pane pane = new Pane();
//		RegisterView registerView = new RegisterView(this);
//		pane.getChildren().add(registerView);
//		myScene.setContentPane(pane);
		
		RegisterView registerView = new RegisterView(this);
		myScene.setContentPane(registerView.makeRegisterPane());
	}
	
	public void viewChoose() {
//		Pane pane = new Pane();
//		chooseView.showPane();
//		pane.getChildren().add(chooseView);
		
		ChooseView chooseView = new ChooseView(this);
		myScene.setContentPane(chooseView.makeChoosePane());
	}
	
	public void viewLobby() {
		LobbyView accountView = new LobbyView(this);
		myScene.setContentPane(accountView.makeAccountPane());
	}

	public ArrayList<Player> getAllPlayersOfThisAccount() {
		return account.getPlayers();
	}

}
