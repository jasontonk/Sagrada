package model;

public class Toolcard {

	private String name;
	private String description;
	private int amountOfCoins;
	private ToolCardDBA toolcardDB;
	
	public Toolcard(String name, String description) {
		this.name = name;
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
}
