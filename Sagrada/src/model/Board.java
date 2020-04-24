package model;

public class Board {
	private PatternCard patternCard;
	private BoardField[][] boardFields;
	private int boardId;
	private boolean hasPatternCard;
	private static final int BOARD_SQUARES_HORIZONTAL = 5;
	private static final int BOARD_SQUARES_VERTICAL = 4;
	
	public Board() {
		// TODO Auto-generated constructor stub
	}
	
	public void placeDie(GameDie gameDie, int xPos, int Ypos) {
		BoardField boardField = boardFields[xPos][Ypos];
		boardField.addDie(gameDie);
	}
	
	public boolean validateNorthWestColor() {
		//TODO
		return false;
	}
	
	public boolean validateNorthWestValue() {
		//TODO
		return false;
	}
	
	public boolean validateNorthEastColor() {
		//TODO
		return false;
	}
	
	public boolean validateNorthEastValue() {
		//TODO
		return false;
	}
	
	public boolean validateSouthEastColor() {
		//TODO
		return false;
	}
	
	public boolean validateSouthEastValue() {
		//TODO
		return false;
	}
	
	public boolean validateSouthstColor() {
		//TODO
		return false;
	}
	
	public boolean validateSouthWesstValue() {
		//TODO
		return false;
	}
	
	public boolean validateSouthWestColor() {
		//TODO
		return false;
	}
	
	
	public boolean validateNorthColor() {
		//TODO
		return false;
	}
	
	public boolean validateWestColor() {
		//TODO
		return false;
	}
	
	public boolean validateEastColor() {
		//TODO
		return false;
	}
	
	public boolean validateSouthColor() {
		//TODO
		return false;
	}
	
	public boolean validateNorthValue() {
		//TODO
		return false;
	}
	
	public boolean validateWestValue() {
		//TODO
		return false;
	}
	
	public boolean validateEastValue() {
		//TODO
		return false;
	}
	
	public boolean validateSouthValue() {
		//TODO
		return false;
	}
	
	
	/**
     * 
     */
	public boolean validateSidesColor() {
//		validateNorthColor();
//		validateWestColor();
//		validateEastColor();
//		validateSouthColor();
		//TODO
		return false;
	}
	
	/**
     * 
     */
	public boolean validateSidesValue() {
//		validateNorthValue();
//		validateWestValue();
//		validateEastValue();
//		validateSouthValue();
		//TODO
		return false;
	}
	
	public boolean validateFieldColorAndDieColor() {
		//TODO
		return false;
	}
	
	public boolean validateFieldValueAndDieValue() {
		//TODO
		return false;
	}
	
	public boolean validateIsNextToDieNorth() {
		//TODO
		return false;
	}
	public boolean validateIsNextToDieSouth() {
		//TODO
		return false;
	}
	public boolean validateIsNextToDieEast() {
		//TODO
		return false;
	}
	public boolean validateIsNextToDieWest() {
		//TODO
		return false;
	}
	
	public boolean validateIsNextToDieNorthEast() {
		//TODO
		return false;
	}

	public boolean validateIsNextToDieNorthWest() {
		//TODO
		return false;
	}
	
	public boolean validateIsNextToDieSouthEast() {
		//TODO
		return false;
	}
	
	public boolean validateIsNextToDieSouthWest() {
		//TODO
		return false;
	}
	
	/**
     * validates if there is any Die next to or diagonal to the placed Die
     */
	public boolean validateIsNextToDie() {
		if(validateIsNextToDieNorth() || validateIsNextToDieSouth() || validateIsNextToDieEast() 
			|| validateIsNextToDieWest() || validateIsNextToDieNorthEast() || validateIsNextToDieNorthWest() 
			|| validateIsNextToDieSouthEast() || validateIsNextToDieSouthWest()) {
			return true;
		}
		return false;
	}
	
}
