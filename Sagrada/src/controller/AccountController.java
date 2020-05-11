package controller;

import java.util.ArrayList;

import database.DataBaseConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Account;
import view.AccountView;
import view.LoginView;

public class AccountController {

	private Account account;
	private LoginView loginView;
	private DataBaseConnection connection;
	private AccountView accountView;
	
	public AccountController(DataBaseConnection c) {
		this.connection = c;
		loginView = new LoginView(this);
		account = new Account(c);
		// TODO Auto-generated constructor stub
	}
	
	public void actionLogin(String username, String password) {
		if(account.accountExists(username)) {
			if(password.equals(account.getPassword(password))) {
				accountView.makeAccountPane();
			}
		}
		// TODO Auto-generated method stub
		else System.out.println("fout");
	}  //test
 
	public void actionRegister(String username, String password) {
		if(account.accountExists(username)) {
			loginView.addError();
		} else {
			account.setAccount(username, password);
			accountView.makeAccountPane();
		}
	}
	
	public ArrayList<Account> getAllAccounts() {
		return account.getAllAccounts();
	}

}
