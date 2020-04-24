package model;

public class RoundTrackField {

	private Die[][] dieSavingSpots;
	
	public RoundTrackField() {
		// TODO Auto-generated constructor stub
		dieSavingSpots = = new Die[10][9];
	}

	public void placeDie(Die die, round int) { // methode je geeft een dobbelsteen mee en de ronde en plaatst de dobbelsteen op het eerste lege plekje in het ronde spoor
		for (int i = 0; i < dieSavingSpots.length; i++) {
			if (dieSavingSpots[round][i] == null) {
				dieSavingSpots[round][i] = die;	
				return;
			}
		}				
	}

	public Die takeDie(round int, die int) {// hoe gaan we de rondes bij houden ?
		if (dieSavingSpots[round][die] != null) {
			Die returndie = dieSavingSpots[round][die];
			dieSavingSpots[round][die] = null;
			return returndie;
		}
		 return die;
	}
	

}
