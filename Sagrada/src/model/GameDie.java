package model;

import java.util.ArrayList;

import database.GameDieDBA;

public class GameDie extends Die {

	private int eyes;
	private boolean isAvailable;
	private boolean isFirstTurn;
	private boolean isOnRoundTrack;
	private BoardField boardField;
	private GameDieDBA gameDieDBA;
	
	public GameDie(ModelColor modelColor, int number, int eyes) {
		super(modelColor, number);
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

	public boolean isAvailable(Game game) {
		if(gameDieDBA.getRoundID(this, game) != 0) {
			isAvailable = true;
		}
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
	public void addDieToDB(Game game) {
		gameDieDBA.addGameDie(this, game);
	}
	public void setRoundID(Game game) {
		gameDieDBA.addRoundID(this,game, game.getRound());
		
	}
	public ArrayList<GameDie> getAllRoundDice(Game game){
		return gameDieDBA.getAllRoundDice(game);
	}
	public int getRoundID(Game game) {
		return gameDieDBA.getRoundID(this, game);
	}
}
