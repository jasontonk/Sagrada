package model;

public class RoundTrack {
	
	private RoundTrackField[] roundTrackFields;
	private int roundCounter = 1;
	
	//constructor
	public RoundTrack() {
		roundTrackFields = new RoundTrackField[10];
	}
	
	/*
	 * Places Die on the roundtrack, calls roundtrackfield method
	 */
	public void placeDie(Die die) {
		for (int i = 0; i < roundTrackFields.length; i++) {
			if(roundTrackFields[i] == null) {
				roundTrackFields[i].addDice(die);
 				return;
			}
		}
	}
	
	
	/*
	 * Returns die that gets chosen
	 * TODO change to the right method for getRound
	 */
	public Die takeDie(int die) {
		if(roundTrackFields[game.getRound].getDieSavingSpot() != null) {
			//krijgt array terug
			Die[] dieOnField = roundTrackFields[game.getRound].getDieSavingSpot();
			return dieOnField[die];
		}
	}
	
	
}
