package model;

import java.util.ArrayList;

import database.AccountDBA;
import database.DataBaseConnection;
import database.PlayerDBA;
import javafx.scene.paint.Color;

public class Account {
	
	private AccountStatus accountStatus;
	private String username;
	private String password;
	private ArrayList<Player> players;
	private ArrayList<Invitation> invitations; 
	private DataBaseConnection connection;
	private AccountDBA accountDBA;
	
	private int mostUsedValue;
	private Color mostUsedColor;
	private int[] winsAndLoses;
	private int highestScore;
	private int differentOpponents;
	
	/**
     * 
     */
	public Account(DataBaseConnection c) {
		players = new ArrayList<Player>();
		invitations = new ArrayList<>();
		connection = c;
		accountDBA = new AccountDBA(c);
	}
	
	/**
     * 
     */
	public Account(String username, String password, DataBaseConnection c) {
		this.username = username;
		this.password = password;
		players = new ArrayList<>();
		invitations = new ArrayList<>();	
		connection = c;
		accountDBA = new AccountDBA(c);
	}

	/**
     * 
     */
	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	/**
     * 
     */
	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
     * 
     */
	public String getUsername() {
		return username;
	}

	/**
     * 
     */
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setAccount(String username, String password) {
		accountDBA.addAccountDB(username, password);
	}
	
	public boolean accountExists(String username) {
		return accountDBA.accountExists(username);
	}

	/**
     * 
     */
	public String getPassword(String username) {
		//TODO
		return accountDBA.GetPassword(username);
	}

	/**
     * 
     */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
     * 
     */
	public ArrayList<Player> getPlayers() {
		PlayerDBA playerDBA = new PlayerDBA(connection);
//		players = playerDBA.getPlayersOfAccount(this);//TODO matheus
		return players;
	}

	/**
     * 
     */
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	/**
     * 
     */
	public ArrayList<Invitation> getInvitations() {
//		invitations = accountDBA.getInvitations();//TODO matheus
		return invitations;
	}

	/**
     * 
     */
	public void setInvitations(ArrayList<Invitation> invitations) {
		this.invitations = invitations;
	}
	
	//=====================================================================================================
	//=DATABASE STATS======================================================================================
	
	/**
     * 
     */
	public int getHighestScore() {
		return accountDBA.getHighestScore(this);
	}
	
	/**
     * 
     */
	public int getMostUsedValue() {
		return accountDBA.getMostUsedValue(this);
	}
	
	/**
     * 
     */
	public ModelColor getMostUsedColor() {
		String color = accountDBA.getMostUsedColor(this);
		return getColorFromString(color);
	}
	
	private ModelColor getColorFromString(String c) {
		ModelColor modelColor = ModelColor.BLUE;
		switch(c) {
		case "blue":
			modelColor = ModelColor.BLUE;
			break;
		case "green":
			modelColor = ModelColor.GREEN;
			break;
		case "purple":
			modelColor = ModelColor.PURPLE;
			break;
		case "red":
			modelColor = ModelColor.RED;
			break;
		case "yellow":
			modelColor = ModelColor.YELLOW;
			break;
		default:
			modelColor = null;
		}
		return modelColor;
	}
	
	/**
     * 
     */
	public int getValueOfDifferentPlayedAccounts() {
		return accountDBA.getValueOfDifferentPlayedAccounts(this);
	}
	
	/**
     * 
     */
	public int[] getWinsAndLoses() {
		int wins = accountDBA.getWins(this);
		int loses = accountDBA.getLoses(this);
		int[] winsAndLoses = {wins, loses};
		return winsAndLoses; 
	}
	
	public ArrayList<Account> getAllAccounts(){
		return accountDBA.GetAllAccountsDB();
	}

}
