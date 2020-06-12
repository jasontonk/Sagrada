package model;

public class RoundTrackField {

	private GameDie[] dieSavingSpot;// array to save dies max 9 dies per round

	public RoundTrackField() {
		// TODO Auto-generated constructor stub
		dieSavingSpot = new GameDie[9];
	}

	public void addDice(GameDie die) { // adds die to the dieSavingSpot array on the first possible place
		for (int i = 0; i < dieSavingSpot.length; i++) {
			if (dieSavingSpot[i] == null) {
				dieSavingSpot[i] = die;//changed i to die
				break;
			}
		}
	}

	public GameDie takeDie(int die) { // select number of die in array returns die that was selected if die not null and removes it from the array
		if (die != 0) {
			return dieSavingSpot[die];//changed returntype to die
		}
		return null;//changed to return null if needed
	}
	public GameDie[] getDieSavingSpot() {
		return dieSavingSpot;
	}

	public void setDieSavingSpot(GameDie[] dieSavingSpot) {
		this.dieSavingSpot = dieSavingSpot;
	}
	
	public GameDie getDie(int index) {
		return dieSavingSpot[index];
	}
}
