package model;

import java.util.ArrayList;

import database.DataBaseConnection;
import database.FavorTokenDBA;
import database.PatternCardDBA;
import database.PlayerDBA;
import database.PlayerFrameFieldDBA;
import javafx.beans.property.SimpleIntegerProperty;

public class Player {
	
	private int id;
	private int sequenceNumber;
	private SimpleIntegerProperty score;
	private String name;
	private PlayerStatus playerStatus;
	private boolean isCurrentPlayer;
	private boolean placedDie;
	
	private Account account;
	private FavorToken favorToken;
	private ModelColor personalObjectiveCardColor;
	private Board board;
	private ArrayList<FavorToken> favorTokens;
	private PatternCard patternCard;
	private ArrayList<PatternCard> patternCardsToChoose;
	private Game game;
	private DataBaseConnection connection;
	private boolean drawnPatternCard;
	
	private PlayerDBA playerDBA ;
	private FavorTokenDBA favorTokenDBA;
	private PlayerFrameFieldDBA playerFrameFieldDBA;
	
	public Player(DataBaseConnection c, Game game) {
		connection = c;
		playerDBA = new PlayerDBA(c);
		drawnPatternCard = false;
		setPlayerFrameFieldDBA(new PlayerFrameFieldDBA(c));
		favorTokenDBA = new FavorTokenDBA(c);
		patternCard = new PatternCard(c);
		favorTokens = new ArrayList<FavorToken>();
		this.setPlayerStatus(PlayerStatus.CHALLENGEE);
		this.game = game;
		this.setPersonalObjectiveCardColor();	
		score = new SimpleIntegerProperty();
		setScore(-20);
		}	
	
	public void createBoard() {
		board = new Board(this, connection);	
	}

	public void addPlayer(Player player) {
		playerDBA.addPlayer(player, playerStatus);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSequenceNumber() {
		return playerDBA.getPlayerSeqNumber(this);
	}

	public void setSequenceNumber(int seqnr) {
		this.sequenceNumber = seqnr;
		playerDBA.setPlayerSeqNumber(seqnr, this);;
	}

	public SimpleIntegerProperty getScore() {
		if(id == game.getPersonalPlayer().getId()) {
			score.set(playerDBA.getScoreFromDB(this));
		}
		else {
			score.set(playerDBA.getScoreFromDB(this) - playerDBA.getPrivateObjectiveCardScoreFromDB(this));
		}
		return score;
	}
	public SimpleIntegerProperty getPrivateScore() {
		score.set(playerDBA.getScoreFromDB(this));
		return score;
	}

	public void setScore(int score) {
		this.score.set(score);
		playerDBA.setScore(this, score);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PlayerStatus getPlayerStatus() {
		playerStatus = playerDBA.getPlayerStatusFromDB(playerDBA.getPlayerUsingID(this.getId(), game)); 
		return playerStatus;
	}

	public void setPlayerStatus(PlayerStatus playerStatus) {
		this.playerStatus = playerStatus;
		playerDBA.setPlayerStatus(this, playerStatus);
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
		return board;
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

	public ModelColor getPersonalObjectiveCardColor() {
		return personalObjectiveCardColor;
	}

	public void setPersonalObjectiveCardColor() {
		this.personalObjectiveCardColor = ModelColor.randomColor(ModelColor.class);
	}
	
	public void setPersonalObjectiveCardColorFromDB(ModelColor modelcolor) {
		this.personalObjectiveCardColor = modelcolor;
	}

	public ArrayList<FavorToken> getFavorTokens() {
		this.favorTokens = favorTokenDBA.getFavortokensOfPlayer(this);
		return favorTokens;
	}

	public void setFavorTokens(ArrayList<FavorToken> favorTokens) {
		this.favorTokens = favorTokens;
	}

	public PatternCard getPatternCard() {
			if(patternCard.getPatterncardDB().getSelectedPatterncardOfPlayer(this.getId(), this) == null) {
				return null;
			}
			else {
				patternCard = patternCard.getPatterncardDB().getSelectedPatterncardOfPlayer(this.getId(), this);	
			}
		patternCard.setpattern(false);
		return patternCard;
	}

	public void setPatternCard(PatternCard patternCard) {
			playerDBA.setPlayerPatternCard(patternCard, this);
			this.patternCard = patternCard;
			for(int i = 0; i < patternCard.getDifficulty(); i++) {
				favorTokens.add(new FavorToken(this, this.getGame().getGameID(), connection));
		}
	}

	public ArrayList<PatternCard> getPatternCardsToChoose(boolean random) {
		PatternCardDBA patternCardDBA = new PatternCardDBA(connection);
		patternCardsToChoose = patternCardDBA.getOptionalPatternCardsOfPlayer(this);
		patternCardsToChoose = patternCard.getPatternCardsToChoose(random, this);
		return patternCardsToChoose;
	}

	public void setPatternCardsToChoose(ArrayList<PatternCard> patternCardsToChoose) {
		this.patternCardsToChoose = patternCardsToChoose;
		PatternCardDBA patternCardDBA = new PatternCardDBA(connection);
		patternCardDBA.saveOptionalPatternCardsOfPlayer(patternCardsToChoose, getId());
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
	
	/**
	* Assign favor tokens of a game to the player
	*/
	public void assignFavorTokens() {
		favorTokens = new ArrayList<FavorToken>();
		favorTokens = favorTokenDBA.getFavortokensOfPlayer(this);
		
		if(favorTokens.size() == 0) {
			for (int i = 0; i < patternCard.getDifficulty(); i++) {
				favorToken = new FavorToken(this, game.getGameID(), connection);
				favorToken.addFavorTokenToDB();
				favorTokens.add(favorToken);
			}
		}
	}
	
	public void setNextSequenceNumber() {
		int players = this.getGame().getPlayers().size();
		int NewSequenceNumber = sequenceNumber;
		
        if (NewSequenceNumber == 1) { // NewSequenceNumber: 1
            if (players == 2) { // players: 2
            	NewSequenceNumber = 4;
            } else if (players == 3) { // players: 3
            	NewSequenceNumber = 6;
            } else if (players == 4) { // players: 4
            	NewSequenceNumber = 8;
            }
        } else if (NewSequenceNumber == 2) { // NewSequenceNumber: 2
            if (players == 2) { // players: 2
            	NewSequenceNumber = 3;
            } else if (players == 3) { // players: 3
            	NewSequenceNumber = 5;
            } else if (players == 4) { // players: 4
            	NewSequenceNumber = 7;
            }
        } else if (NewSequenceNumber == 3) { // NewSequenceNumber: 3
            if (players == 2) { //players: 2
            	NewSequenceNumber = 1;
            } else if (players == 3) { // players: 3
                NewSequenceNumber = 4;
            } else if (players == 4) { // players: 4
                NewSequenceNumber = 6;
            }
        } else if (NewSequenceNumber == 4) { // NewSequenceNumber: 4
            if (players == 2) { //players: 2
                NewSequenceNumber = 2;
            } else if (players == 3) { // players: 3
                NewSequenceNumber = 2;
            } else if (players == 4) { // players: 4
                NewSequenceNumber = 5;
            }
        } else if (NewSequenceNumber == 5) { // NewSequenceNumber: 5
            if (players == 3) { // players: 3
                NewSequenceNumber = 1;
            } else if (players == 4) { // players: 4
                NewSequenceNumber = 3;
            }
        } else if (NewSequenceNumber == 6) { // NewSequenceNumber: 6
            if (players == 3) { // players: 3
                NewSequenceNumber = 3;
            } else if (players == 4) { // players: 4
                NewSequenceNumber = 2;
            }
        } else if (NewSequenceNumber == 7) {
            NewSequenceNumber = 1;
        } else if (NewSequenceNumber == 8) {
            NewSequenceNumber = 4;
        }
		
		this.setSequenceNumber(NewSequenceNumber);
		playerDBA.setPlayerSeqNumber(NewSequenceNumber, this);
	}
	
	public int calculateScore() {
		int score = 0;
		for(int x = 0; x < Board.BOARD_SQUARES_HORIZONTAL; x++) {
			for(int y = 0; y < Board.BOARD_SQUARES_VERTICAL; y++) {
				if(!(board == null)) {
					if(!board.getBoardField(x, y).hasDie()) {
						score = score - 1;
					}
					else if(board.getBoardField(x, y).getDie().getColor().equals(personalObjectiveCardColor)) {
						score = score + 1;
					}
				}
				else {
					
				}
			}
		}
		for(FavorToken favorToken : this.getFavorTokens()) {
			score = score + 1;
		}

		for (PublicObjectiveCard publicObjectiveCard : game.getPublicObjectiveCards()) {
            score = score + publicObjectiveCard.calculateScore(board);
        }
		this.score.set(score);
		playerDBA.setScore(this, score);
		return score;
	}
	
	public boolean checkPlacementAgainstRules(int x, int y, ModelColor modelColor, int value) {		
		return board.checkAll(board.getBoardField(x, y), modelColor, value);
	}

	public GameDie getSelectedDie() {
		return game.getSelectedDie();
	}

	/**
	 * @return the playerFrameFieldDBA
	 */
	public PlayerFrameFieldDBA getPlayerFrameFieldDBA() {
		return playerFrameFieldDBA;
	}

	/**
	 * @param playerFrameFieldDBA the playerFrameFieldDBA to set
	 */
	public void setPlayerFrameFieldDBA(PlayerFrameFieldDBA playerFrameFieldDBA) {
		this.playerFrameFieldDBA = playerFrameFieldDBA;
	}

	/**
	 * @return the drawnPatternCard
	 */
	public boolean isDrawnPatternCard() {
		return drawnPatternCard;
	}

	/**
	 * @param drawnPatternCard the drawnPatternCard to set
	 */
	public void setDrawnPatternCard(boolean drawnPatternCard) {
		this.drawnPatternCard = drawnPatternCard;
	}	
}
