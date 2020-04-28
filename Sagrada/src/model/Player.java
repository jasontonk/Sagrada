package model;

import java.util.ArrayList;

public class Player {
	
	private int id;
	private int sequenceNumber;
	private int score;
	private String name;
	private String playerStatus;
	
	private boolean isCurrentPlayer;
	private boolean placedDie;
	
	private Account account;
	private FavorToken favorToken;
	private Color color;
	private Board board;
	
	private ArrayList<FavorToken> favorTokens;
	private PatternCard patternCard;
	private ArrayList<PatternCard> patternCardsToChoose;
	
	private Game game;
	
	public Player() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlayerStatus() {
		return playerStatus;
	}

	public void setPlayerStatus(String playerStatus) {
		this.playerStatus = playerStatus;
	}

	public boolean isCurrentPlayer() {
		return isCurrentPlayer;
	}

	public void setCurrentPlayer(boolean isCurrentPlayer) {
		this.isCurrentPlayer = isCurrentPlayer;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Board getBoard() {
		return board;//TODO
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public FavorToken getFavorToken() {
		return favorToken;
	}

	public void setFavorToken(FavorToken favorToken) {
		this.favorToken = favorToken;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public ArrayList<FavorToken> getFavorTokens() {
		return favorTokens;//TODO
	}

	public void setFavorTokens(ArrayList<FavorToken> favorTokens) {
		this.favorTokens = favorTokens;
	}

	public ArrayList<PatternCard> getPatternCardsToChoose() {
		return patternCardsToChoose;//TODO
	}

	public void setPatternCardsToChoose(ArrayList<PatternCard> patternCardsToChoose) {
		this.patternCardsToChoose = patternCardsToChoose;//TODO
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public boolean isPlacedDie() {
		return placedDie;
	}

	public void setPlacedDie(boolean placedDie) {
		this.placedDie = placedDie;
	}
	
	public int calculateScore(BoardField[][] boardFields) {
		int score = 0;
		for(int x = 0; x < Board.BOARD_SQUARES_HORIZONTAL; x++) {
			for(int y = 0; y < Board.BOARD_SQUARES_VERTICAL; y++) {
				if(!boardFields[x][y].hasDie()) {
					score = score - 1;
				}
				else {
					if(boardFields[x][y].getDie().getColor().equals(color)) {
						score = score + 1;
					}
				}
			}
		}
		
		for(FavorToken favorToken : this.getFavorTokens()) {
			score = score + 1;
		}
		
		//TODO publicobjective
		
		this.score = score;
		
		//TODO update in database
		
		return score;
	}
	
	
}
