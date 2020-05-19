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
	private ModelColor modelColor;
	private Player player;
	private DataBaseConnection conn;
	
	
	
	public PatternCard(String name, int difficulty, DataBaseConnection conn) {
		this.name = name;
		this.difficulty = difficulty;
		this.conn = conn;
		for(int i = 0; i < 5; i++) {
			for(int c = 0; c < 4; c++) {
				patterncard[i][c] = new PatternCardField();
				patterncard[i][c].setPositionX(i+1);
				patterncard[i][c].setPositionY(c+1);
			}
		}
		patterncardDB = new PatternCardDBA(conn);
		patterncardfieldDB = new PatternCardFieldDBA(conn);
		
	}
	public PatternCard(DataBaseConnection conn) {
		this.conn = conn;
		patterncardDB = new PatternCardDBA(conn);
		patterncardfieldDB = new PatternCardFieldDBA(conn);
		for(int i = 0; i < 5; i++) {
			for(int c = 0; c < 4; c++) {
				patterncard[i][c] = new PatternCardField();
				patterncard[i][c].setPositionX(i+1);
				patterncard[i][c].setPositionY(c+1);
			}
		}
		
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
				if(patterncard[coloredFieldLocationx-1][coloredFieldLocationy-1].getColor() == null 
						&& patterncard[coloredFieldLocationx-1][coloredFieldLocationy-1].getValue() == 0) { 	//check to see if field already has a color or value
							int randomColorNumber = (int)(Math.random() * 5) + 1; 							//generates a rondom number for the switch to get random color
							switch(randomColorNumber) {														//sets random color to color based on random number
								case 1: 
									modelColor = ModelColor.RED;
									break;
								case 2: 
									modelColor = ModelColor.GREEN;
									break;
								case 3: 
									modelColor = ModelColor.YELLOW;
									break;
								case 4: 
									modelColor = ModelColor.PURPLE;
									break;
								case 5: 
									modelColor = ModelColor.BLUE;
									break;
					}
					patterncard[coloredFieldLocationx-1][coloredFieldLocationy-1].setColor(modelColor);
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
				if(patterncard[numberedFieldLocationx-1][numberedFieldLocationy-1].getColor() == null 
						&& patterncard[numberedFieldLocationx-1][numberedFieldLocationy-1].getValue() == 0) { 	//check to see if field already has a color or value
							patterncard[numberedFieldLocationx-1][numberedFieldLocationy-1].setValue(randomValue);
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
				patterncard[p.getPositionX()-1][p.getPositionY()-1] = p;
			}
		}
		
	
//		patterncard[2][0].setColor(ModelColor.GREEN);
//		patterncard[0][1].setValue(2);
//		patterncard[1][1].setColor(ModelColor.YELLOW);
//		patterncard[2][1].setValue(5);
//		patterncard[3][1].setColor(ModelColor.BLUE);
//		patterncard[4][1].setValue(1);
//		patterncard[1][2].setColor(ModelColor.RED);
//		patterncard[2][2].setValue(3);
//		patterncard[3][2].setColor(ModelColor.PURPLE);
//		patterncard[0][3].setValue(1);
//		patterncard[2][3].setValue(6);
//		patterncard[4][3].setValue(4);
//		this.setName("Chromatic Splendor");
//		this.setDifficulty(4);
	}
	public ModelColor getFieldColor(int xPos, int yPos) {
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
	public ArrayList<PatternCard> getPatternCardsToChoose(boolean random, Player player) {
		ArrayList<PatternCard> patternCardsToChoose = new ArrayList<>();
		if(!random) {
			for(int i = 0; i < 4;i++) {
				patternCardsToChoose.add(patterncardDB.getPatterncard());	
			}
		}
		else {
			for (int i = 0; i < 4; i++) {
				PatternCard  patternCard = new PatternCard(conn);
				patternCard.setpattern(random);
				patternCardsToChoose.add(patternCard);
			}
		}
		return patternCardsToChoose;
	}
	
}
