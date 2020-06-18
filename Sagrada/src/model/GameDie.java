package model;

import java.util.ArrayList;

import database.DataBaseConnection;
import database.GameDieDBA;

public class GameDie extends Die {

	private int eyes;
	private int round;
	private boolean isAvailable;
	private boolean isFirstTurn;
	private int isOnRoundtrackfield;
	private BoardField boardField;
	private GameDieDBA gameDieDBA;
	
	public GameDie(ModelColor modelColor, int number, int eyes, Game game, DataBaseConnection conn, GameDieDBA gameDieDBA) {
		super(modelColor, number);
		this.gameDieDBA = gameDieDBA;
//		addDieToDB(game);
		this.eyes = eyes;
		isAvailable = false;
		isFirstTurn = false;
		isOnRoundtrackfield = 0;
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

	public int isOnRoundTrack() {
		return isOnRoundtrackfield;
	}

	public void setOnRoundTrack(int isOnRoundTrack) {
		this.isOnRoundtrackfield = isOnRoundTrack;
	}

	public int getEyes() {
		return eyes;
	}
	
	public void changeEyes(int eyes, Game game) {
		this.eyes = eyes;
		gameDieDBA.addEyes(this, game, eyes);
	}

	public void setEyes(Game game) {
		gameDieDBA.addEyes(this, game, eyes);
	}
	
	public void addDieToDB(Game game) {
		gameDieDBA.addGameDie(this, game);
	}
	public void setRoundID(Game game) {
		round = game.getRoundFromDB();
		gameDieDBA.addRoundID(this,game);
	}
	public void setRoundIDFromDB(int roundID) {
		this.round = roundID;
	}
	public ArrayList<GameDie> getAllRoundDice(Game game){
		return gameDieDBA.getAllRoundDice(game);
	}
	public int getRoundID(Game game) {
		this.round = gameDieDBA.getRoundID(this, game);
		return round;
	}
}
