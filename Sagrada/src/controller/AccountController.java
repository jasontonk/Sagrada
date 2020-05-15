package controller;

import java.util.ArrayList;

import database.DataBaseConnection;
import model.Account;
import model.Player;
import view.LobbyView;
import view.ChooseView;
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
		}
		// TODO Auto-generated method stub
		else System.out.println("fout");
	}  
 
	public void actionRegister(String username, String password) {
		if(account.accountExists(username) || (password.length() < 3)) {
//			registerView.addError();
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

}
