package model;

import java.util.List;

import database.PatternCardDBA;

public class PatternCard {

	private String name;
	private int difficulty; 
	private int patterncardID;
	private PatternCardField[][] patterncard;
	private PatternCardDBA patterncardDB;
	private Color color;
	private Player player;
	
	//TODO remove
	private PatternCardField temp = new PatternCardField();
	
	public PatternCard(String name, int difficulty) {
		this.name = name;
		this.difficulty = difficulty;
		patterncard = new PatternCardField[5][4]();
		patterncardDB = new PatternCardDBA();
	}
	//TODO figure out how to get patterncard from database with matheus
	/*
	 * Sets up patterncard. If random patterncard is requested, 
	 * method generates a random patterncard with 4-6 colored fields and 4-8 numbered fields. 
	 * To request random, give parameter true.
	 */
	public void setpattern(boolean random) {
		if(random) {
			int amountOfColoredFields = (int) (Math.random() * 3) + 4; 								//generates a random number between 4 and 6 for the amount of colored fields
			for(int i = 0; i < amountOfColoredFields; i++) {
				int coloredFieldLocationx = (int)(Math.random() * 5) + 1; 							//generates a random number between 1 and 5 for the x location of a colored field
				int coloredFieldLocationy = (int)(Math.random() * 4) + 1; 							//generates a random number between 1 and 4 for the y location of a colored field
				if(patterncard[x][y].getColor() == null && patterncard[x][y].getValue() == null) { 	//check to see if field already has a color or value
					int randomColorNumber = (int)(Math.random() * 5) + 1; 							//generates a rondom number for the switch to get random color
					switch(randomColorNumber) {														//sets random color to color based on random number
						case 1: 
							color = RED;
							break;
						case 2: 
							color = GREEN;
							break;
						case 3: 
							color = YELLOW;
							break;
						case 4: 
							color = PURPLE;
							break;
						case 5: 
							color = BLUE;
							break;
					}
					patterncard[x][y].setColor(color);
				}
				else {
					i--;
				}
			}
			int amountOfNumberedFields = (int) (Math.random() * 5) + 4; 							//generates a random number between 4 and 8 for the amount of numbered fields
			for(int i = 0; i < amountOfNumberedFields; i++) {
				int numberedFieldLocationx = (int)(Math.random() * 5) + 1; 							//generates a random number between 1 and 5 for the x location of a numbered field
				int numberedFieldLocationy = (int)(Math.random() * 4) + 1; 							//generates a random number between 1 and 4 for the y location of a numbered field
				int randomValue = (int)(Math.random() * 6) + 1; 									//generates an random number to give to field
				if(patterncard[x][y].getColor() == null && patterncard[x][y].getValue() == null) { 	//check to see if field already has a color or value
					patterncard[x][y].setValue(randomValue);
				}
				else {
					i--;
				}
			}
			int amountOfSpecialFields = amountOfColoredFields + amountOfNumberedFields;
			switch(amountOfSpecialFields) {
			case 8:
				difficulty = 3;
				break;
			case 9: case 10:
				difficulty = 4;
				break;
			case 11: case 12:
				difficulty = 5;
				break;
			case 13: case 14:
				difficulty = 6;
				break;
			}
			this();
		}
		else {
			private patterncard[][] = patterncardDB.getPatternCard();
		}
	}
	public Color checkFieldColor(int xPos, int yPos) {
		return patterncard[xPos][yPos].getColor();
	}
	public int checkFieldValue(int xPos, int yPos) {
		return patterncard[xPos][yPos].getValue();
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPatterncardID(int patterncardID) {
		this.patterncardID = patterncardID;
	}
	public int getPatterncardID() {
		return patterncardID;
	}
	public int getDifficulty() {
		return difficulty;
	}
	
}
