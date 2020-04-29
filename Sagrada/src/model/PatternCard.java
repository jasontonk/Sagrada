import java.util.List;

public class PatternCard {

	private String name;
	private int difficulty; 
	private PatternCardField[][] patterncard;
	private PatternCardDBA patterncardDB;
	private Color color
	
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
					patterncard[x][y].setColor(color);//TODO add color
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
}
