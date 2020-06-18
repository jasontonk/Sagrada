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

	public Toolcard(String name, String description, int id, DataBaseConnection conn) {
		this.name = name;
		this.id = id;
		this.description = description;
		amountOfCoins = 0;
		this.conn = conn;
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

	public boolean eglomiseBorstel(Player player, PatternCard patternCard, BoardField boardField, Board board,
			ModelColor modelColor, int value) {
		System.out.println("patterncard1" + patternCard.getName());
		System.out.println("patterncard2" + player.getPatternCard().getName());
		System.out.println(boardField.getxPos() + " " + boardField.getyPos());

		if (board.checkIsNextToDie(boardField)) {
//		if(!board.checkSidesColor(boardField, modelColor)) {
			System.out.println("ik kom in de 1e|| " + value);
			if (!board.checkSidesValue(boardField, value)) {
				System.out.println("ik kom in de 2e");
				if (board.checkFieldColorAndDieColor(boardField, modelColor)
						|| board.checkFieldValueAndDieValue(boardField, value)
						|| !board.checkFieldColorAndDieColor(boardField, modelColor)
						|| !board.checkFieldColorAndDieColor(boardField, modelColor)
								&& !board.checkFieldValueAndDieValue(boardField, value)
								&& patternCard.getFieldColor(boardField.getxPos(), boardField.getyPos()) == null
								&& patternCard.getFieldValue(boardField.getxPos(), boardField.getyPos()) == 0) {
					System.out.println("ik kom in de 3e");

					board.placeDie(boardField, player.getSelectedDie());

					return true;
				}
			}
		}
//				}
		return false;
	}
	
	public void grozingPliers(Player palyer, PatternCard patternCard, BoardField boardfield, Board board) {
		
		
	}

}
