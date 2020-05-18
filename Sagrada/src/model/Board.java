package model;

import database.DataBaseConnection;
import database.PlayerFrameFieldDBA;

public class Board {
	private PatternCard patternCard;
	private BoardField[][] boardFields;
	private int boardId;
	private boolean hasPatternCard;
	public static final int BOARD_SQUARES_HORIZONTAL = 5;
	public static final int BOARD_SQUARES_VERTICAL = 4;
	private Player player;
	private PlayerFrameFieldDBA playerFrameFieldDBA;
	
	public Board(int boardId, Player player, DataBaseConnection conn) {
		this.setBoardId(boardId);
		this.player = player;
		patternCard = this.player.getPatternCard();
		boardFields = new BoardField[5][4];
		playerFrameFieldDBA = new PlayerFrameFieldDBA(conn);
		for(int x = 0; x < 5; x++) {
			for (int y = 0; y < 4; y++) {
				boardFields[x][y] = new BoardField(x, y);
				playerFrameFieldDBA.addPlayerFrameField(player, x, y);
			}
		}
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
		for(int y = 0; y < BOARD_SQUARES_VERTICAL; y++) {
			for(int x = 0; x < BOARD_SQUARES_HORIZONTAL; x++) {
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
	public BoardField getBoardField(int x, int y) {
		System.out.println("XPos: "+x+" YPos: "+y);
		if(boardFields[x][y] != null){
			return boardFields[x][y];
		}
		else {
			return null;
		}
	}

	/**
     * returns true if there is a die with the color of the parameter north west of the boardfield of the parameter
     */
	private boolean checkNorthWestDieColor(BoardField boardField, ModelColor modelColor) {
		if(boardField.getyPos() == 0 || boardField.getxPos() == 0) {
			return false;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos() - 1, boardField.getyPos() - 1);
		if(boardFieldNext.hasDie()) {
			return checkFieldColorAndDieColor(boardFieldNext, modelColor);
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
	private boolean checkNorthDieColor(BoardField boardField, ModelColor modelColor) {
		if(boardField.getyPos() == 0) {
			return false;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos(), boardField.getyPos() - 1);
		if(boardFieldNext.hasDie()) {
			System.out.println(""+boardFieldNext.getDie().getColor()+" "+modelColor);
			if(boardFieldNext.getDie().getColor().equals(modelColor)) {
				System.out.println("test");
				return true;
			}
		}
		return false;
	}

	/**
     * returns true if the dice west of the boardfield has the color of the parameter
     */
	private boolean checkWestDieColor(BoardField boardField, ModelColor modelColor) {
		if(boardField.getxPos() == 0) {
			return false;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos() - 1, boardField.getyPos());
		if(boardFieldNext.hasDie()) {
			if(boardFieldNext.getDie().getColor() == modelColor) {
				return true;
			}
		}
		return false;
	}
	
	/**
     * returns true if the dice east of the boardfield has the color of the parameter
     */
	private boolean checkEastDieColor(BoardField boardField, ModelColor modelColor) {
		if(boardField.getxPos()+1 == BOARD_SQUARES_HORIZONTAL) {
			return false;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos() + 1, boardField.getyPos());
		if(boardFieldNext.hasDie()) {
			System.out.println( "test2");
			if(boardFieldNext.getDie().getColor() == modelColor) {
				return true;
			}
		}
		return false;
	}
	
	/**
     * returns true if the dice south of the boardfield has the color of the parameter
     */
	private boolean checkSouthDieColor(BoardField boardField, ModelColor modelColor) {
		if(boardField.getyPos()+1 == BOARD_SQUARES_VERTICAL) {
			return false;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos(), boardField.getyPos() + 1);
		if (boardFieldNext.hasDie()) {
			if(boardFieldNext.getDie().getColor() == modelColor) {
				return true;
			}
		}
		return false;
	}
	
	
	
	/**
     * returns true if the dice north of the boardfield has the value of the parameter
     */
	private boolean checkNorthDieValue(BoardField boardField, int value) {
		if(boardField.getyPos() == 0) {
			return false;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos(), boardField.getyPos() - 1);
		if(boardFieldNext.hasDie()) {
			if(boardFieldNext.getDie().getEyes() == value) {
				return true;
			}
		}
		return false;
	}
	
	/**
     * returns true if the dice west of the boardfield has the value of the parameter
     */
	private boolean checkWestDieValue(BoardField boardField, int value) {
		if(boardField.getxPos() == 0) {
			return false;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos() - 1, boardField.getyPos());
		if(boardFieldNext.hasDie()) {
			if(boardFieldNext.getDie().getEyes() == value) {
				return true;
			}
		}
		return false;
	}
	
	/**
     * returns true if the dice east of the boardfield has the value of the parameter
     */
	private boolean checkEastDieValue(BoardField boardField, int value) {
		if(boardField.getxPos()+1 == BOARD_SQUARES_HORIZONTAL) {
			return false;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos() + 1, boardField.getyPos());
		if(boardFieldNext.hasDie()) {
			if(boardFieldNext.getDie().getEyes() == value) {
				return true;
			}
		}
		return false;
	}
	
	/**
     * returns true if the dice south of the boardfield has the value of the parameter
     */
	private boolean checkSouthDieValue(BoardField boardField, int value) {
		if(boardField.getyPos()+1 == BOARD_SQUARES_VERTICAL) {
			return false;
		}
		BoardField boardFieldNext = getBoardField(boardField.getxPos(), boardField.getyPos() + 1);
		if(boardFieldNext.hasDie()) {
			if(boardFieldNext.getDie().getEyes() == value) {
				return true;
			}
		}
		return false;
	}
	
	
	
	
	/**
     * returns true if the dice on the side have the same color as the parameter
     * 
     */
	public boolean checkSidesColor(BoardField boardField, ModelColor modelColor) {
		if(checkNorthDieColor(boardField, modelColor) || checkEastDieColor(boardField, modelColor)|| 
				checkSouthDieColor(boardField, modelColor) || checkWestDieColor(boardField, modelColor)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
     * Returns true if the dice on the side have the same value as the parameter
     */
	public boolean checkSidesValue(BoardField boardField, int value) {
		if(checkNorthDieValue(boardField, value) || checkEastDieValue(boardField, value) || 
				checkSouthDieValue(boardField, value) || checkWestDieValue(boardField, value)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	 /**
     * Returns true when the boardfield has the same color as the parameter
     */
	public boolean checkFieldColorAndDieColor(BoardField boardField, ModelColor modelColor) {
		int xPos = boardField.getxPos();
		int yPos = boardField.getyPos();
		ModelColor fieldColor = patternCard.getFieldColor(xPos, yPos);
		if(fieldColor == modelColor) {
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
		if (boardField.getyPos() == 0) {
            return false;
        }
        return getBoardField(boardField.getxPos(), boardField.getyPos() - 1).hasDie();
    }
	
	/**
     * returns true if there is a die on the south of the boardfield
     */
	private boolean checkIsNextToDieSouth(BoardField boardField) {
		if (boardField.getyPos()+1 == BOARD_SQUARES_VERTICAL) {
            return false;
        }
        return getBoardField(boardField.getxPos(), boardField.getyPos() + 1).hasDie();
	}
	
	/**
     * returns true if there is a die on the east of the boardfield
     */
	private boolean checkIsNextToDieEast(BoardField boardField) {
		if (boardField.getxPos()+1 == BOARD_SQUARES_HORIZONTAL) {
            return false;
        }
        return getBoardField(boardField.getxPos() + 1, boardField.getyPos()).hasDie();
	}
	
	/**
     * returns true if there is a die on the west of the boardfield
     */
	private boolean checkIsNextToDieWest(BoardField boardField) {
		if (boardField.getxPos() == 0) {
            return false;
        }
        return getBoardField(boardField.getxPos() - 1, boardField.getyPos()).hasDie();
	}
	
	
	/**
     * returns true if there is a die on the north east of the boardfield
     */
	private boolean checkIsNextToDieNorthEast(BoardField boardField) {
		if (boardField.getyPos() == 0 || boardField.getxPos()+1 == BOARD_SQUARES_HORIZONTAL) {
            return false;
        }
        return getBoardField(boardField.getxPos() + 1, boardField.getyPos() - 1).hasDie();
    }

	
	/**
     * returns true if there is a die on the north west of the boardfield
     */
	private boolean checkIsNextToDieNorthWest(BoardField boardField) {
		if (boardField.getxPos()== 0 || boardField.getyPos() == 0) {
            return false;
        }
        return getBoardField(boardField.getxPos() - 1, boardField.getyPos() - 1).hasDie();
    }
	
	
	/**
     * returns true if there is a die on the south east of the boardfield
     */
	private boolean checkIsNextToDieSouthEast(BoardField boardField) {
		if (boardField.getxPos() == 4 || boardField.getyPos() == 3) {
            return false;
        }
        return getBoardField(boardField.getxPos() + 1, boardField.getyPos() + 1).hasDie();
    }
	
	
	/**
     * returns true if there is a die on the south west of the boardfield
     */
	private boolean checkIsNextToDieSouthWest(BoardField boardField) {
		if (boardField.getyPos() == 3 || boardField.getxPos() == 0) {
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
	public boolean checkAll(BoardField boardField, ModelColor modelColor, int value) {
		if(checkIsNextToDie(boardField)||true) {
			if(!checkSidesColor(boardField, modelColor)) { 
				if(!checkSidesValue(boardField, value)) {
					if(checkFieldColorAndDieColor(boardField, modelColor) || checkFieldValueAndDieValue(boardField, value) || 
							!checkFieldColorAndDieColor(boardField, modelColor) && !checkFieldValueAndDieValue(boardField, value) && 
								patternCard.getFieldColor(boardField.getxPos(), boardField.getyPos()) == null && 
									patternCard.getFieldValue(boardField.getxPos(), boardField.getyPos()) == 0){
						placeDie(boardField, player.getSelectedDie());
						playerFrameFieldDBA.setDie(player.getSelectedDie(), player, boardField.getxPos(), boardField.getyPos());
						return true;
					}
				}
			}
		}
		return false;
	}
}
