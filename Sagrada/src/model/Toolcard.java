package model;

import database.ToolCardDBA;

public class Toolcard {

	private String name;
	private int id;
	private int seqnr;
	private String description;
	private int amountOfCoins;
	private ToolCardDBA toolcardDB;
	
	public Toolcard(String name, String description,int id) {
		this.name = name;
		this.id = id;
		this.description = description;
		amountOfCoins = 0;
		toolcardDB = new ToolCardDBA();
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

	public int getSeqnr() {
		return seqnr;
	}

	public void setSeqnr(int seqnr) {
		this.seqnr = seqnr;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
