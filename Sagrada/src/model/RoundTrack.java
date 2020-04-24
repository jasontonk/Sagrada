
public class RoundTrack {
	
	private RoundTrackField[] roundTrackFields;
	private int roundCounter = 1;
	
	
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
				roundCounter++;
				return;
			}
		}
	}
	
	
	/*
	 * Returns die that gets chosen
	 */
	public Die takeDie(int die) {
		if(roundTrackFields[roundCounter].getDieSavingSpot() != null) {
			//krijgt array terug
			Die[] dieOnField = roundTrackFields[roundCounter].getDieSavingSpot();
			return dieOnField[die];
		}
	}
	
	
}
