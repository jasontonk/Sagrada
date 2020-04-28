package model;

import java.util.ArrayList;

import database.AccountDBA;

public class Account {
	
	private AccountStatus accountStatus;
	private String username;
	private String password;
	private ArrayList<Player> players;
	private ArrayList<Invitation> invitations;
	
	/**
     * 
     */
	public Account() {
		players = new ArrayList<Player>();
		invitations = new ArrayList<>();	
	}
	
	/**
     * 
     */
	public Account(String username, String password) {
		this.username = username;
		this.password = password;
		players = new ArrayList<>();
		invitations = new ArrayList<>();	
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

//	/**
//     * 
//     */
//	public ArrayList<Player> getPlayers() {
//		return players;
//	}

	/**
     * 
     */
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

//	/**
//     * 
//     */
//	public ArrayList<Invitation> getInvitations() {
//		return invitations;
//	}

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
		return 0;//TODO
//		AccountDBA aDB = new AccountDBA();
//		aDB.getHighestScore(this);
	}
	
	/**
     * 
     */
	public int getMostUsedValue() {
		return 0;//TODO
//		AccountDBA aDB = new AccountDBA();
//		aDB.getMostUsedValue(this);
	}
	
	/**
     * 
     */
	public int getMostUsedColor() {
		return 0;//TODO
//		AccountDBA aDB = new AccountDBA();
//		aDB.getMostUsedColor(this);
	}
	
	/**
     * 
     */
	public int getValueOfDifferentPlayedAccounts() {
		return 0;//TODO
//		AccountDBA aDB = new AccountDBA();
//		aDB.getValueOfDifferentPlayedAccounts(this);
	}
	
	/**
     * 
     */
	public int[] getWinsAndLoses() {
		return null;
		//TODO
//		AccountDBA aDB = new AccountDBA();
//		int wins = aDB.getWins(this);
//		int loses = aDB.getLoses(this);
//		int[] winsAndLoses = {wins, loses};
//		return winsAndLoses;
	}
}
