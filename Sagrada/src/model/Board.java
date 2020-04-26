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
        int xPos = boardField.getxPos();
        int yPos = boardField.getyPos();
        placeDie(gameDie, xPos, yPos);
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

	/**
     * validates if there is a die with the color of the parameter north west of the boardfield of the parameter
     */
	public boolean validateNorthWestDieColor(BoardField boardField, Color color, boolean isDie) {
		if(boardField.getyPos() == 1 || boardField.getxPos() == 1) {
			return true;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos() - 1, boardField.getyPos() - 1);
		return validateFieldColorAndDieColor(boardFieldNext, color, isDie);
	}
	
	/**
     * validates if there is a die with the value of the parameter north west of the boardfield of the parameter
     */
	public boolean validateNorthWestDieValue(BoardField boardField, int value, boolean isDie) {
		if(boardField.getyPos() == 1 || boardField.getxPos() == 1) {
			return true;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos() - 1, boardField.getyPos() - 1);
		return validateFieldValueAndDieValue(boardFieldNext, value, isDie);
	}
	
	/**
     * validates if there is a die with the color of the parameter north east of the boardfield of the parameter
     */
	public boolean validateNorthEastDieColor(BoardField boardField, Color color, boolean isDie) {
		if(boardField.getyPos() == 1 || boardField.getxPos() == BOARD_SQUARES_HORIZONTAL) {
			return true;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos() + 1, boardField.getyPos() - 1);
		return validateFieldColorAndDieColor(boardFieldNext, color, isDie);
	}
	
	/**
     * validates if there is a die with the value of the parameter north east of the boardfield of the parameter
     */
	public boolean validateNorthEastDieValue(BoardField boardField, int value, boolean isDie) {
		if(boardField.getyPos() == 1 || boardField.getxPos() == BOARD_SQUARES_HORIZONTAL) {
			return true;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos() + 1, boardField.getyPos() - 1);
		return validateFieldValueAndDieValue(boardFieldNext, value, isDie);
	}
	
	/**
     * validates if there is a die with the color of the parameter south east of the boardfield of the parameter
     */
	public boolean validateSouthEastDieColor(BoardField boardField, Color color, boolean isDie) {
		if(boardField.getyPos() == BOARD_SQUARES_VERTICAL || boardField.getxPos() == BOARD_SQUARES_HORIZONTAL) {
			return true;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos() + 1, boardField.getyPos() + 1);
		return validateFieldColorAndDieColor(boardFieldNext, color, isDie);
	}
	
	/**
     * validates if there is a die with the value of the parameter south east of the boardfield of the parameter
     */
	public boolean validateSouthEastDieValue(BoardField boardField, int value, boolean isDie) {
		if(boardField.getyPos() == BOARD_SQUARES_VERTICAL || boardField.getxPos() == BOARD_SQUARES_HORIZONTAL) {
			return true;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos() + 1, boardField.getyPos() + 1);
		return validateFieldValueAndDieValue(boardFieldNext, value, isDie);
	}
	
	/**
     * validates if there is a die with the value of the parameter south west of the boardfield of the parameter
     */
	public boolean validateSouthWestDieValue(BoardField boardField, int value, boolean isDie) {
		if(boardField.getyPos() == BOARD_SQUARES_VERTICAL || boardField.getxPos() == 1) {
			return true;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos() - 1, boardField.getyPos() + 1);
		return validateFieldValueAndDieValue(boardFieldNext, value, isDie);
	}
	
	/**
     * validates if there is a die with the color of the parameter south west of the boardfield of the parameter
     */
	public boolean validateSouthWestDieColor(BoardField boardField, Color color, boolean isDie) {
		if(boardField.getyPos() == BOARD_SQUARES_VERTICAL || boardField.getxPos() == 1) {
			return true;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos() - 1, boardField.getyPos() + 1);
		return validateFieldColorAndDieColor(boardFieldNext, color, isDie);
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
	
	
	
	/**
     * checks if there is a die on the north of the boardfield
     */
	public boolean validateIsNextToDieNorth(BoardField boardField) {
		if (boardField.getyPos() == 1) {
            return false;
        }
        return getBoardField(boardField.getxPos(), boardField.getyPos() - 1).hasDie();
    }
	
	/**
     * checks if there is a die on the south of the boardfield
     */
	public boolean validateIsNextToDieSouth(BoardField boardField) {
		if (boardField.getyPos() == BOARD_SQUARES_VERTICAL) {
            return false;
        }
        return getBoardField(boardField.getxPos(), boardField.getyPos() + 1).hasDie();
	}
	
	/**
     * checks if there is a die on the east of the boardfield
     */
	public boolean validateIsNextToDieEast(BoardField boardField) {
		if (boardField.getxPos() == BOARD_SQUARES_HORIZONTAL) {
            return false;
        }
        return getBoardField(boardField.getxPos() + 1, boardField.getyPos()).hasDie();
	}
	
	/**
     * checks if there is a die on the west of the boardfield
     */
	public boolean validateIsNextToDieWest(BoardField boardField) {
		if (boardField.getxPos() == 1) {
            return false;
        }
        return getBoardField(boardField.getxPos() - 1, boardField.getyPos()).hasDie();
	}
	
	public boolean validateIsNextToDieNorthEast(BoardField boardField) {
		if (boardField.getyPos() == 1 || boardField.getxPos() == BOARD_SQUARES_HORIZONTAL) {
            return false;
        }
        return getBoardField(boardField.getxPos() + 1, boardField.getyPos() - 1).hasDie();
    }

	public boolean validateIsNextToDieNorthWest(BoardField boardField) {
		if (boardField.getxPos()== 1 || boardField.getyPos() == 1) {
            return false;
        }
        return getBoardField(boardField.getxPos() - 1, boardField.getyPos() - 1).hasDie();
    }
	
	public boolean validateIsNextToDieSouthEast(BoardField boardField) {
		if (boardField.getxPos() == 5 || boardField.getyPos() == 4) {
            return false;
        }
        return getBoardField(boardField.getxPos() + 1, boardField.getyPos() + 1).hasDie();
    }
	
	public boolean validateIsNextToDieSouthWest(BoardField boardField) {
		if (boardField.getyPos() == 4 || boardField.getxPos() == 1) {
            return false;
        }
        return getBoardField(boardField.getxPos() - 1, boardField.getyPos() + 1).hasDie();
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
