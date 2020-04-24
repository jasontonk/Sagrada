package model;

public class RoundTrackField {

	private Die[] dieSavingSpot;
	
	public RoundTrackField() {
		// TODO Auto-generated constructor stub
		dieSavingSpot = new Die[9];
	}

	public void addDice(Die die) {
		for (int i = 0; i < dieSavingSpot.length; i++) {
			if (dieSavingSpot[i] == null) {
				dieSavingSpot[i] = i;
				return;
			}	
		}
	}
	
	public die takeDie(int die) {
		Die returndie;
		if (die != null) {
			returndie = dieSavingSpot[die];
			dieSavingSpot = null;
			return returndie;
			
		}
		return returndie;
	}
	public Die[] getDieSavingSpot() {
		return dieSavingSpot;
	}
	public void setDieSavingSpot(Die[] dieSavingSpot) {
		this.dieSavingSpot = dieSavingSpot;
	}
	
	
}
