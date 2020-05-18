package model;

public class RoundTrack {
	
	private RoundTrackField[] roundTrackFields;
	private Game game; //deleted roundcounter and added game to fix error
	
	//constructor
	public RoundTrack(Game game) {
		roundTrackFields = new RoundTrackField[10];
		this.game = game;
	}
	
	/*
	 * Places Die on the roundtrack, calls roundtrackfield method 
	 */
	public void placeDie(Die die,int round) {
		for (int i = 0; i < roundTrackFields.length; i++) {
			if(roundTrackFields[round] == null) {
				roundTrackFields[round].addDice(die);
 				return;
			}
		}
	}
	
	
	/*
	 * Returns die that gets chosen
	 * TODO change to the right method for getRound
	 */
	public Die takeDie(int die) {
		if(roundTrackFields[game.getRound()].getDieSavingSpot() != null) {//changed to getRounc() to fix error
			//krijgt array terug
			Die[] dieOnField = roundTrackFields[game.getRound()].getDieSavingSpot();
			return dieOnField[die];
		}
		return null;//returns null if nothing can be returned
	}
	
	
}
