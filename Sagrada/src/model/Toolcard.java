package model;

import database.DataBaseConnection;
import database.ToolCardDBA;

public class Toolcard {

	private String name;
	private int id;
	private String description;
	private int amountOfCoins;
	private ToolCardDBA toolcardDB;
	private DataBaseConnection conn;
	
	public Toolcard(String name, String description,int id, DataBaseConnection conn) {
		this.name = name;
		this.id = id;
		this.description = description;
		amountOfCoins = 0;
		this.conn=conn;
		toolcardDB = new ToolCardDBA(conn);
	}
	
	public void addFavorToken(int amount) {
		amountOfCoins += amount;
	}
	public int returnAmountOfTokens() {
		return amountOfCoins;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
