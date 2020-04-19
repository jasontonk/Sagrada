package model;

public class RoundTrackField {
	
	Dice[][] diceSavingSpots = new Dice[10][9]
			
			public RoundTrackField() {
				// TODO Auto-generated constructor stub
		
			}
			
	public void placeDice(Dice dice, round int) {
		for (int i = 0; i < diceSavingSpots.length; i++) {
			if (diceSavingSpots[round][i] == null) {
				diceSavingSpots[round][i] = dice;	
				return;
			
			}
		}
				
	}

}
