import java.util.List;

public class PatternCard {

	private String name;
	private int difficulty; 
	private List<PatternCardField> patterncard;
	private PatternCardDBA patterncardDB;
	
	//TODO remove
	private PatternCardField temp = new PatternCardField();
	
	public PatternCard(String name, int difficulty) {
		this.name = name;
		this.difficulty = difficulty;
		patterncardDB = new PatternCardDBA();
	}
	//TODO figure out how te get patterncard from database with matheus
	public void setpattern(boolean random) {
		if(random) {
			int amountOfColoredFields = (int) (Math.random() * 3) + 4; //generates a random number between 4 and 6 for the amount of colored fields
			for(int i = 0; i < amountOfColoredFields; i++) {
				int coloredFieldLocation = (int)(Math.random() * 20) + 1; //generates a rondom number between 1 and 20 for the location of a colored field
				if(patterncard.get(coloredFieldLocation) != null) {
					patterncard.add(coloredFieldLocation, temp);//TODO add color
				}
				else {
					i--;
				}
			}
			int amountOfNumberedFields = (int) (Math.random() * 5) + 4; //generates a random number between 4 and 8 for the amount of numbered fields
			for(int i = 0; i < amountOfNumberedFields; i++) {
				int numberedFieldLocation = (int)(Math.random() * 20) + 1; //generates a random number between 1 and 20 for the location of a colored field
				if(patterncard.get(numberedFieldLocation) != null) {
					patterncard.add(numberedFieldLocation, temp);//TODO add color
				}
				else {
					i--;
				}
			}
		}
		else {
			private List<PatternCardField> = patterncardDB.getPatternCard();
		}
	}
}
