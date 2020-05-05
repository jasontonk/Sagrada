package model;

import java.util.ArrayList;

import database.AccountDBA;
import database.DataBaseConnection;
import database.PlayerDBA;

public class Account {
	
	private AccountStatus accountStatus;
	private String username;
	private String password;
	private ArrayList<Player> players;
	private ArrayList<Invitation> invitations;
	private DataBaseConnection connection;
	
	/**
     * 
     */
	public Account(DataBaseConnection c) {
		players = new ArrayList<Player>();
		invitations = new ArrayList<>();
		connection = c;
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

	/**
     * 
     */
	public String getPassword() {
		return password;
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
		players = playerDBA.getPlayersOfAccount(this);//TODO matheus
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
		AccountDBA accountDBA = new AccountDBA(connection);
		invitations = accountDBA.getInvitations();//TODO matheus
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
		AccountDBA aDB = new AccountDBA(connection);
		return aDB.getHighestScore(this);
	}
	
	/**
     * 
     */
	public int getMostUsedValue() {
		AccountDBA accountDBA = new AccountDBA(connection);
		return accountDBA.getMostUsedValue(this);
	}
	
	/**
     * 
     */
	public Color getMostUsedColor() {
		AccountDBA accountDBA = new AccountDBA(connection);
		String color = accountDBA.getMostUsedColor(this);
		//TODO kleur terug geven van string naar color
	}
	
	/**
     * 
     */
	public int getValueOfDifferentPlayedAccounts() {
		AccountDBA accountDBA = new AccountDBA(connection);
		return accountDBA.getValueOfDifferentPlayedAccounts(this);
	}
	
	/**
     * 
     */
	public int[] getWinsAndLoses() {
		AccountDBA accountDBA = new AccountDBA(connection);
		int wins = accountDBA.getWins(this);
		int loses = accountDBA.getLoses(this);
		int[] winsAndLoses = {wins, loses};
		return winsAndLoses;
	}
}
