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
	public void placeDie(GameDie die,int round) {
		roundTrackFields[round].addDice(die);
	}
	
	
	/*
	 * Returns die that gets chosen
	 * TODO change to the right method for getRound
	 */
	public GameDie takeDie(int die) {
		if(roundTrackFields[game.getRound().get()].getDieSavingSpot() != null) {//changed to getRounc() to fix error
			//krijgt array terug
			GameDie[] dieOnField = roundTrackFields[game.getRound().get()].getDieSavingSpot();
			return dieOnField[die];
		}
		return null;//returns null if nothing can be returned
	}
	public RoundTrackField[] getRoundTrackFields(){
		return roundTrackFields;
	}
	
	
}
