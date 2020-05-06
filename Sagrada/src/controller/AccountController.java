package controller;

import database.DataBaseConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Account;
import view.LoginView;

public class AccountController {

	private Account account;
	private LoginView loginView;
	private DataBaseConnection connection;
	
	public AccountController(Account account, DataBaseConnection c) {
		this.account = account;
		this.connection = c;
		loginView = new LoginView(this);
		// TODO Auto-generated constructor stub
	}
	
	public EventHandler<ActionEvent> actionLogin(String username, String password) {
		if(account.accountExists(username)) {
			if(password.equals(account.getPassword())) {
				
			}
		}
		// TODO Auto-generated method stub
		return null;
	}

	public EventHandler<ActionEvent> actionRegister(String username, String password) {
		if(account.accountExists(username)) {
			loginView.addError();
		} else {
			//TODO vragen of het alleen in db opgeslagen wordt, of ook lokaal
			account.setUsername(username);
			account.setPassword(password);
			account.setAccount(username, password);
		}
	}

}
