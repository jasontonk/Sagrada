package model;

public class GameDie extends Die {

	private int eyes;
	private boolean isAvailable;
	private boolean isFirstTurn;
	private boolean isOnRoundTrack;
	private BoardField boardField;
	
	public GameDie(Color color, int number, int eyes) {
		super(color, number);
		this.setEyes(eyes);
		isAvailable = false;
		isFirstTurn = false;
		isOnRoundTrack = false;
	}
	
	public void setBoardField(BoardField boardField) {
		this.boardField = boardField;
	}
	
	public BoardField getBoardField() {
        return boardField;
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

	public int getEyes() {
		return eyes;
	}

	public void setEyes(int eyes) {
		this.eyes = eyes;
	}
}
