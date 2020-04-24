package model;

public class Board {
	private PatternCard patternCard;
	private BoardField[][] boardFields;
	private int boardId;
	private boolean hasPatternCard;
	private static final int BOARD_SQUARES_HORIZONTAL = 5;
	private static final int BOARD_SQUARES_VERTICAL = 4;
	private Player player;
	
	public Board() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
     * places a die on the coordinates of the parameters
     */
	public void placeDie(GameDie gameDie, int xPos, int Ypos) {
		BoardField boardField = boardFields[xPos][Ypos];
		boardField.addDie(gameDie);
	}
	
	/**
     * places a die on the coordinates of a boardfield
     */
	public void placeDie(BoardField boardField, GameDie gameDie) {
        int xPos = BoardField.getxPos();
        int yPos = BoardField.getyPos();
        placeDie(xPos, yPos, gameDie);
    }
	
	/**
     * removes a die from the coordinates of a boardfield
     */
	public void removeDie(BoardField boardField) {
		boardField.setDie(null);
	}
	
	
	
	/**
     * Checks if it is the first turn of the player
     */
	public boolean isFirstTurn() {
		for(int y = 1; y < BOARD_SQUARES_VERTICAL; y++) {
			for(int x = 1; x < BOARD_SQUARES_HORIZONTAL; x++) {
				BoardField boardField = getBoardField(x, y);
				GameDie gameDie = boardField.getDie();
				if (gameDie != null) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
     * Returns the board field of the parameters
     */
	private BoardField getBoardField(int x, int y) {
		return boardFields[x][y];
	}

	public boolean validateNorthWestDieColor() {
		//TODO
		return false;
	}
	
	public boolean validateNorthWestDieValue() {
		//TODO
		return false;
	}
	
	public boolean validateNorthEastDieColor() {
		//TODO
		return false;
	}
	
	public boolean validateNorthEastDieValue() {
		//TODO
		return false;
	}
	
	public boolean validateSouthEastDieColor() {
		//TODO
		return false;
	}
	
	public boolean validateSouthEastDieValue() {
		//TODO
		return false;
	}
	
	public boolean validateSouthWestDieValue() {
		//TODO
		return false;
	}
	
	public boolean validateSouthWestDieColor() {
		//TODO
		return false;
	}
	
	
	
	/**
     * checks if the dice north of the boardfield has the color of the parameter
     */
	public boolean validateNorthDieColor(BoardField boardField, Color color, boolean isDie) {
		if(boardField.getyPos() == 1) {
			return true;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos(), boardField.getyPos() - 1);
		return validateFieldColorAndDieColor(boardFieldNext, color, isDie);
	}

	/**
     * checks if the dice west of the boardfield has the color of the parameter
     */
	public boolean validateWestDieColor(BoardField boardField, Color color, boolean isDie) {
		if(boardField.getxPos() == 1) {
			return true;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos() - 1, boardField.getyPos());
		return validateFieldColorAndDieColor(boardFieldNext, color, isDie);
	}
	
	/**
     * checks if the dice east of the boardfield has the color of the parameter
     */
	public boolean validateEastDieColor(BoardField boardField, Color color, boolean isDie) {
		if(boardField.getxPos() == BOARD_SQUARES_HORIZONTAL) {
			return true;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos() + 1, boardField.getyPos());
		return validateFieldColorAndDieColor(boardFieldNext, color, isDie);
	}
	
	/**
     * checks if the dice south of the boardfield has the color of the parameter
     */
	public boolean validateSouthDieColor(BoardField boardField, Color color, boolean isDie) {
		if(boardField.getyPos() == BOARD_SQUARES_VERTICAL) {
			return true;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos(), boardField.getyPos() + 1);
		return validateFieldColorAndDieColor(boardFieldNext, color, isDie);
	}
	
	
	
	/**
     * checks if the dice north of the boardfield has the value of the parameter
     */
	public boolean validateNorthDieValue(BoardField boardField, int value, boolean isDie) {
		if(boardField.getyPos() == 1) {
			return true;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos(), boardField.getyPos() - 1);
		return validateFieldValueAndDieValue(boardFieldNext, value, isDie);
	}
	
	/**
     * checks if the dice west of the boardfield has the value of the parameter
     */
	public boolean validateWestDieValue(BoardField boardField, int value, boolean isDie) {
		if(boardField.getxPos() == 1) {
			return true;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos() - 1, boardField.getyPos());
		return validateFieldValueAndDieValue(boardFieldNext, value, isDie);
	}
	
	/**
     * checks if the dice east of the boardfield has the value of the parameter
     */
	public boolean validateEastDieValue(BoardField boardField, int value, boolean isDie) {
		if(boardField.getxPos() == BOARD_SQUARES_HORIZONTAL) {
			return true;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos() + 1, boardField.getyPos());
		return validateFieldValueAndDieValue(boardFieldNext, value, isDie);
	}
	
	/**
     * checks if the dice south of the boardfield has the value of the parameter
     */
	public boolean validateSouthDieValue(BoardField boardField, int value, boolean isDie) {
		if(boardField.getyPos() == BOARD_SQUARES_VERTICAL) {
			return true;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos(), boardField.getyPos() + 1);
		return validateFieldValueAndDieValue(boardFieldNext, value, isDie);
	}
	
	
	
	
	/**
     * 
     */
	public boolean validateSidesColor() {
		//TODO
		return false;
	}
	
	/**
     * 
     */
	public boolean validateSidesValue() {
		//TODO
		return false;
	}
	
	
	
	
	public boolean validateFieldColorAndDieColor(BoardField boardField, Color color, boolean isDie) {
		//TODO
		return false;
	}
	
	public boolean validateFieldValueAndDieValue(BoardField boardField, int value, boolean isDie) {
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
