package model;

import database.DataBaseConnection;
import database.FavorTokenDBA;

public class Toolcard {

	private String name;
	private int id;
	private String description;
	private int amountOfCoins;
	private  FavorTokenDBA favorTokenDBA;

	public Toolcard(String name, String description, int id, DataBaseConnection conn) {
		this.name = name;
		this.id = id;
		this.description = description;
		amountOfCoins = 0;
		favorTokenDBA = new FavorTokenDBA(conn);
		
	}

	public void addFavorToken(int amount) {
		amountOfCoins += amount;
	}

	public int returnAmountOfTokens(Game game) {
		return favorTokenDBA.getFavortokensOfToolcard(this,game);
	}

	public void setAmountOfCoins(int amountOfCoins) {
		this.amountOfCoins = this.amountOfCoins + amountOfCoins;
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

	public boolean eglomiseBorstel(Player player, PatternCard patternCard, BoardField boardField, Board board,
			ModelColor modelColor, int value) {

		if (board.checkIsNextToDie(boardField)) {
			if (!board.checkSidesValue(boardField, value)) {
				if (board.checkFieldColorAndDieColor(boardField, modelColor)
						|| board.checkFieldValueAndDieValue(boardField, value)
						|| !board.checkFieldColorAndDieColor(boardField, modelColor)
						|| (!board.checkFieldColorAndDieColor(boardField, modelColor)
								&& !board.checkFieldValueAndDieValue(boardField, value)
								&& patternCard.getFieldColor(boardField.getxPos(), boardField.getyPos()) == null
								&& patternCard.getFieldValue(boardField.getxPos(), boardField.getyPos()) == 0)) {
					board.placeDie(boardField, player.getSelectedDie());
					return true;
				}
			}
		}
		return false;
	}
}
