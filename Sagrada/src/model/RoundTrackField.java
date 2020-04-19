package model;

public class RoundTrackField {

	Dice[][] diceSavingSpots = new Dice[10][9]

	public void placeDice(Dice dice, round int) { // methode je geeft een dobbelsteen mee en de ronde en plaatst de dobbelsteen op het eerste lege plekje in het ronde spoor
		for (int i = 0; i < diceSavingSpots.length; i++) {
			if (diceSavingSpots[round][i] == null) {
				diceSavingSpots[round][i] = dice;	
				return;
			}
		}				
	}

	public Dice takeDice(round int, dice int) {
		if (diceSavingSpots[round][dice] != null) {
			Dice returndice = diceSavingSpots[round][dice];
			diceSavingSpots[round][dice] = null;
			return returndice;
		}
		 return dice;
	}
	

}
