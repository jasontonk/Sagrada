package model;

public class GameDie extends Die {

	private int dieNumber;
	private int gameRound;
	private boolean isAvailable;
	private boolean isFirstTurn;
	private boolean isOnRoundTrack;
	private BoardField boardField;
	
	public GameDie(Color color, int eyes, int dieNumber, int gameRound) {
		super(color, eyes);
		this.dieNumber = dieNumber;
		isAvailable = false;
		isFirstTurn = false;
		isOnRoundTrack = false;
		this.gameRound = gameRound;
	}
	
	public void setBoardField(BoardField boardField) {
		this.boardField = boardField;
	}
	
	public BoardField getBoardField() {
        return boardField;
    }

	public int getDieNumber() {
		return dieNumber;
	}

	public void setDieNumber(int dieNumber) {
		this.dieNumber = dieNumber;
	}

	public void setGameRound(int gameRound) {
		this.gameRound = gameRound;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public boolean isFirstTurn() {
		return isFirstTurn;
	}

	public void setFirstTurn(boolean isFirstTurn) {
		this.isFirstTurn = isFirstTurn;
	}

	public boolean isOnRoundTrack() {
		return isOnRoundTrack;
	}

	public void setOnRoundTrack(boolean isOnRoundTrack) {
		this.isOnRoundTrack = isOnRoundTrack;
	}


}
