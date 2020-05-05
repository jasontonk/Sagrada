package model;

import java.util.ArrayList;
import java.util.List;

import database.DataBaseConnection;
import database.PatternCardDBA;
import database.PatternCardFieldDBA;

public class PatternCard {

	private String name;
	private int difficulty; 
	private int patterncardID;
	private PatternCardField[][] patterncard = new PatternCardField[5][4];
	private PatternCardDBA patterncardDB;
	private PatternCardFieldDBA patterncardfieldDB;
	private Color color;
	private Player player;
	private DataBaseConnection conn;
	
	
	
	public PatternCard(String name, int difficulty, DataBaseConnection conn) {
		this.name = name;
		this.difficulty = difficulty;
		this.conn = conn;
		for(int i = 0; i < 5; i++) {
			for(int c = 0; c < 4; c++) {
				patterncard[i][c].setPositionX(i);
				patterncard[i][c].setPositionY(c);
			}
		}
		patterncardDB = new PatternCardDBA(conn);
		
	}
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
				if(patterncard[coloredFieldLocationx][coloredFieldLocationy].getColor() == null 
						&& patterncard[coloredFieldLocationx][coloredFieldLocationy].getValue() == 0) { 	//check to see if field already has a color or value
							int randomColorNumber = (int)(Math.random() * 5) + 1; 							//generates a rondom number for the switch to get random color
							switch(randomColorNumber) {														//sets random color to color based on random number
								case 1: 
									color = Color.RED;
									break;
								case 2: 
									color = Color.GREEN;
									break;
								case 3: 
									color = Color.YELLOW;
									break;
								case 4: 
									color = Color.PURPLE;
									break;
								case 5: 
									color = Color.BLUE;
									break;
					}
					patterncard[coloredFieldLocationx][coloredFieldLocationy].setColor(color);
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
				if(patterncard[numberedFieldLocationx][numberedFieldLocationy].getColor() == null 
						&& patterncard[numberedFieldLocationx][numberedFieldLocationy].getValue() == 0) { 	//check to see if field already has a color or value
							patterncard[numberedFieldLocationx][numberedFieldLocationy].setValue(randomValue);
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
			patterncardDB.addPatterncard(this);
			ArrayList<PatternCardField> patterncardfields = new ArrayList<>();
			for(int i = 0; i < 5; i++) {
				for(int c = 0; c < 4; c++) {
					patterncardfields.add(patterncard[i][c]);
				}
			}
			patterncardfieldDB.addPatternCardField(patterncardfields, this);
		}
		else {
			PatternCard temp = patterncardDB.getPatterncard();
			setPatterncardID(temp.getPatterncardID());
			setName(temp.getName());
			setDifficulty(temp.getDifficulty());
			ArrayList<PatternCardField> list = patterncardfieldDB.getPatternCardFieldsOfPatterncard(this);
			for(PatternCardField p:list) {
				patterncard[p.getPositionX()][p.getPositionY()] = p;
			}
		}
	}
	public Color getFieldColor(int xPos, int yPos) {
		return patterncard[xPos][yPos].getColor();
	}
	public int getFieldValue(int xPos, int yPos) {
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
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
}
