package model;

public class Board {
	private PatternCard patternCard;
	private BoardField[][] boardFields;
	private int boardId;
	private boolean hasPatternCard;
	public static final int BOARD_SQUARES_HORIZONTAL = 5;
	public static final int BOARD_SQUARES_VERTICAL = 4;
	private Player player;
	
	public Board(int boardId, Player player) {
		this.setBoardId(boardId);
		this.player = player;
		hasPatternCard = false;
	}
	
	public PatternCard getPatternCard() {
		return patternCard;
	}

	public void setPatternCard(PatternCard patternCard) {
		this.patternCard = patternCard;
	}


	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean isHasPatternCard() {
		return hasPatternCard;
	}

	public void setHasPatternCard(boolean hasPatternCard) {
		this.hasPatternCard = hasPatternCard;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	/**
     * places a die on the coordinates of the parameters
     */
	public void placeDie(GameDie gameDie, int xPos, int Ypos) {
		BoardField boardField = boardFields[xPos][Ypos];
		boardField.setDie(gameDie);
	}
	
	/**
     * places a die on the coordinates of a boardfield
     */
	public void placeDie(BoardField boardField, GameDie gameDie) {
        int xPos = boardField.getxPos();
        int yPos = boardField.getyPos();
        placeDie(gameDie, xPos, yPos);
    }
	
	/**
     * removes a die from the coordinates of a boardfield
     */
	public void removeDie(BoardField boardField) {
		boardField.remove();;
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

	/**
     * returns true if there is a die with the color of the parameter north west of the boardfield of the parameter
     */
	private boolean checkNorthWestDieColor(BoardField boardField, Color color) {
		if(boardField.getyPos() == 1 || boardField.getxPos() == 1) {
			return false;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos() - 1, boardField.getyPos() - 1);
		if(boardFieldNext.hasDie()) {
			return checkFieldColorAndDieColor(boardFieldNext, color);
		}
		return false;
	}
	
	//============================================
	
//	/**
//     * returns true if there is a die with the value of the parameter north west of the boardfield of the parameter
//     */
//	private boolean checkNorthWestDieValue(BoardField boardField, int value) {
//		if(boardField.getyPos() == 1 || boardField.getxPos() == 1) {
//			return false;
//		}
//		BoardField boardFieldNext = getBoardField(boardField.getxPos() - 1, boardField.getyPos() - 1);
//		if(boardFieldNext.hasDie()) {
//			return checkFieldValueAndDieValue(boardFieldNext, value);
//		}
//		return false;
//	}
//	
//	/**
//     * returns true if there is a die with the color of the parameter north east of the boardfield of the parameter
//     */
//	private boolean checkNorthEastDieColor(BoardField boardField, Color color) {
//		if(boardField.getyPos() == 1 || boardField.getxPos() == BOARD_SQUARES_HORIZONTAL) {
//			return false;
//		}
//		BoardField boardFieldNext = getBoardField(boardField.getxPos() + 1, boardField.getyPos() - 1);
//		if(boardFieldNext.hasDie()) {
//			return checkFieldColorAndDieColor(boardFieldNext, color);
//		}
//		return false;
//	}
//	
//	/**
//     * checks if there is a die with the value of the parameter north east of the boardfield of the parameter
//     */
//	private boolean checkNorthEastDieValue(BoardField boardField, int value) {
//		if(boardField.getyPos() == 1 || boardField.getxPos() == BOARD_SQUARES_HORIZONTAL) {
//			return false;
//		}
//		BoardField boardFieldNext = getBoardField(boardField.getxPos() + 1, boardField.getyPos() - 1);
//		if(boardFieldNext.hasDie()) {
//			return checkFieldValueAndDieValue(boardFieldNext, value);
//		}
//		return false;
//	}
//	
//	/**
//     * returns true if there is a die with the color of the parameter south east of the boardfield of the parameter
//     */
//	private boolean checkSouthEastDieColor(BoardField boardField, Color color) {
//		if(boardField.getyPos() == BOARD_SQUARES_VERTICAL || boardField.getxPos() == BOARD_SQUARES_HORIZONTAL) {
//			return false;
//		}
//		BoardField boardFieldNext = getBoardField(boardField.getxPos() + 1, boardField.getyPos() + 1);
//		if(boardFieldNext.hasDie()) {
//			return checkFieldColorAndDieColor(boardFieldNext, color);
//		}
//		return false;
//	}
//	
//	/**
//     * returns true if there is a die with the value of the parameter south east of the boardfield of the parameter
//     */
//	private boolean checkSouthEastDieValue(BoardField boardField, int value) {
//		if(boardField.getyPos() == BOARD_SQUARES_VERTICAL || boardField.getxPos() == BOARD_SQUARES_HORIZONTAL) {
//			return false;
//		}
//		BoardField boardFieldNext = getBoardField(boardField.getxPos() + 1, boardField.getyPos() + 1);
//		if(boardFieldNext.hasDie()) {
//			return checkFieldValueAndDieValue(boardFieldNext, value);
//		}
//		return false;
//	}
//	
//	/**
//     * returns true if there is a die with the value of the parameter south west of the boardfield of the parameter
//     */
//	private boolean checkSouthWestDieValue(BoardField boardField, int value) {
//		if(boardField.getyPos() == BOARD_SQUARES_VERTICAL || boardField.getxPos() == 1) {
//			return false;
//		}
//		BoardField boardFieldNext = getBoardField(boardField.getxPos() - 1, boardField.getyPos() + 1);
//		if(boardFieldNext.hasDie()) {
//			return checkFieldValueAndDieValue(boardFieldNext, value);
//		}
//		return false;
//	}
//	
//	/**
//     * returns true if there is a die with the color of the parameter south west of the boardfield of the parameter
//     */
//	private boolean checkSouthWestDieColor(BoardField boardField, Color color) {
//		if(boardField.getyPos() == BOARD_SQUARES_VERTICAL || boardField.getxPos() == 1) {
//			return false;
//		}
//		BoardField boardFieldNext = getBoardField(boardField.getxPos() - 1, boardField.getyPos() + 1);
//		if(boardFieldNext.hasDie()) {
//			return checkFieldColorAndDieColor(boardFieldNext, color);
//		}
//		return false;
//	}
	
//===============================================	
	
	/**
     * returns true if the dice north of the boardfield has the color of the parameter
     */
	private boolean checkNorthDieColor(BoardField boardField, Color color) {
		if(boardField.getyPos() == 1) {
			return false;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos(), boardField.getyPos() - 1);
		if(boardFieldNext.hasDie()) {
			return checkFieldColorAndDieColor(boardFieldNext, color);
		}
		return false;
	}

	/**
     * returns true if the dice west of the boardfield has the color of the parameter
     */
	private boolean checkWestDieColor(BoardField boardField, Color color) {
		if(boardField.getxPos() == 1) {
			return false;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos() - 1, boardField.getyPos());
		if(boardFieldNext.hasDie()) {
			return checkFieldColorAndDieColor(boardFieldNext, color);
		}
		return false;
	}
	
	/**
     * returns true if the dice east of the boardfield has the color of the parameter
     */
	private boolean checkEastDieColor(BoardField boardField, Color color) {
		if(boardField.getxPos() == BOARD_SQUARES_HORIZONTAL) {
			return false;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos() + 1, boardField.getyPos());
		if(boardFieldNext.hasDie()) {
			return checkFieldColorAndDieColor(boardFieldNext, color);
		}
		return false;
	}
	
	/**
     * returns true if the dice south of the boardfield has the color of the parameter
     */
	private boolean checkSouthDieColor(BoardField boardField, Color color) {
		if(boardField.getyPos() == BOARD_SQUARES_VERTICAL) {
			return false;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos(), boardField.getyPos() + 1);
		if (boardFieldNext.hasDie()) {
			return checkFieldColorAndDieColor(boardFieldNext, color);
		}
		return false;
	}
	
	
	
	/**
     * returns true if the dice north of the boardfield has the value of the parameter
     */
	private boolean checkNorthDieValue(BoardField boardField, int value) {
		if(boardField.getyPos() == 1) {
			return false;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos(), boardField.getyPos() - 1);
		if(boardFieldNext.hasDie()) {
			return checkFieldValueAndDieValue(boardFieldNext, value);
		}
		return false;
	}
	
	/**
     * returns true if the dice west of the boardfield has the value of the parameter
     */
	private boolean checkWestDieValue(BoardField boardField, int value) {
		if(boardField.getxPos() == 1) {
			return false;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos() - 1, boardField.getyPos());
		if(boardFieldNext.hasDie()) {
			return checkFieldValueAndDieValue(boardFieldNext, value);
		}
		return false;
	}
	
	/**
     * returns true if the dice east of the boardfield has the value of the parameter
     */
	private boolean checkEastDieValue(BoardField boardField, int value) {
		if(boardField.getxPos() == BOARD_SQUARES_HORIZONTAL) {
			return false;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos() + 1, boardField.getyPos());
		if(boardFieldNext.hasDie()) {
			return checkFieldValueAndDieValue(boardFieldNext, value);
		}
		return false;
	}
	
	/**
     * returns true if the dice south of the boardfield has the value of the parameter
     */
	private boolean checkSouthDieValue(BoardField boardField, int value) {
		if(boardField.getyPos() == BOARD_SQUARES_VERTICAL) {
			return false;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos(), boardField.getyPos() + 1);
		if(boardFieldNext.hasDie()) {
			return checkFieldValueAndDieValue(boardFieldNext, value);
		}
		return false;
	}
	
	
	
	
	/**
     * returns true if the dice on the side have the same color as the parameter
     * 
     */
	public boolean checkSidesColor(BoardField boardField, Color color) {
		return checkNorthDieColor(boardField, color)
				&& checkEastDieColor(boardField, color)
				&& checkSouthDieColor(boardField, color)
				&& checkWestDieColor(boardField, color);
	}
	
	/**
     * Returns true if the dice on the side have the same value as the parameter
     */
	public boolean checkSidesValue(BoardField boardField, int value) {
		return checkNorthDieValue(boardField, value)
				&& checkEastDieValue(boardField, value)
				&& checkSouthDieValue(boardField, value)
				&& checkWestDieValue(boardField, value);
	}
	
	
	
	 /**
     * Returns true when the boardfield has the same color as the parameter
     */
	public boolean checkFieldColorAndDieColor(BoardField boardField, Color color) {
		int xPos = boardField.getxPos();
		int yPos = boardField.getyPos();
		Color fieldColor = patternCard.getFieldColor(xPos, yPos);
		if(fieldColor == color) {
			return true;
		}
		else { 
			return false;
		}
	}
	
	 /**
     * Returns true when the die and the boardfield have the same value
     */
	public boolean checkFieldValueAndDieValue(BoardField boardField, int value) {
		int xPos = boardField.getxPos();
		int yPos = boardField.getyPos();
		int fieldValue = patternCard.getFieldValue(xPos, yPos);
		if(fieldValue == value) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	/**
     * returns true if there is a die on the north of the boardfield
     */
	private boolean checkIsNextToDieNorth(BoardField boardField) {
		if (boardField.getyPos() == 1) {
            return false;
        }
        return getBoardField(boardField.getxPos(), boardField.getyPos() - 1).hasDie();
    }
	
	/**
     * returns true if there is a die on the south of the boardfield
     */
	private boolean checkIsNextToDieSouth(BoardField boardField) {
		if (boardField.getyPos() == BOARD_SQUARES_VERTICAL) {
            return false;
        }
        return getBoardField(boardField.getxPos(), boardField.getyPos() + 1).hasDie();
	}
	
	/**
     * returns true if there is a die on the east of the boardfield
     */
	private boolean checkIsNextToDieEast(BoardField boardField) {
		if (boardField.getxPos() == BOARD_SQUARES_HORIZONTAL) {
            return false;
        }
        return getBoardField(boardField.getxPos() + 1, boardField.getyPos()).hasDie();
	}
	
	/**
     * returns true if there is a die on the west of the boardfield
     */
	private boolean checkIsNextToDieWest(BoardField boardField) {
		if (boardField.getxPos() == 1) {
            return false;
        }
        return getBoardField(boardField.getxPos() - 1, boardField.getyPos()).hasDie();
	}
	
	
	/**
     * returns true if there is a die on the north east of the boardfield
     */
	private boolean checkIsNextToDieNorthEast(BoardField boardField) {
		if (boardField.getyPos() == 1 || boardField.getxPos() == BOARD_SQUARES_HORIZONTAL) {
            return false;
        }
        return getBoardField(boardField.getxPos() + 1, boardField.getyPos() - 1).hasDie();
    }

	
	/**
     * returns true if there is a die on the north west of the boardfield
     */
	private boolean checkIsNextToDieNorthWest(BoardField boardField) {
		if (boardField.getxPos()== 1 || boardField.getyPos() == 1) {
            return false;
        }
        return getBoardField(boardField.getxPos() - 1, boardField.getyPos() - 1).hasDie();
    }
	
	
	/**
     * returns true if there is a die on the south east of the boardfield
     */
	private boolean checkIsNextToDieSouthEast(BoardField boardField) {
		if (boardField.getxPos() == 5 || boardField.getyPos() == 4) {
            return false;
        }
        return getBoardField(boardField.getxPos() + 1, boardField.getyPos() + 1).hasDie();
    }
	
	
	/**
     * returns true if there is a die on the south west of the boardfield
     */
	private boolean checkIsNextToDieSouthWest(BoardField boardField) {
		if (boardField.getyPos() == 4 || boardField.getxPos() == 1) {
            return false;
        }
        return getBoardField(boardField.getxPos() - 1, boardField.getyPos() + 1).hasDie();
    }
	
	/**
     * returns true if there is any Die next to or diagonal to the placed Die
     */
	public boolean checkIsNextToDie(BoardField boardField) {
		if(checkIsNextToDieNorth(boardField) || checkIsNextToDieSouth(boardField) || checkIsNextToDieEast(boardField) 
			|| checkIsNextToDieWest(boardField) || checkIsNextToDieNorthEast(boardField) || checkIsNextToDieNorthWest(boardField) 
			|| checkIsNextToDieSouthEast(boardField) || checkIsNextToDieSouthWest(boardField)) {
			return true;
		}
		return false;
	}
	
}
