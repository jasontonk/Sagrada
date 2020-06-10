package model;

import java.util.ArrayList;

import database.DataBaseConnection;
import database.FavorTokenDBA;
import database.PatternCardDBA;
import database.PlayerDBA;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

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
	
	private PlayerDBA playerDBA ;
	private FavorTokenDBA favorTokenDBA;
	
//	public Player(DataBaseConnection c, Account account, Game game, PlayerStatus playerStatus) {
//		connection = c;
//		playerDBA = new PlayerDBA(c);
//
//		this.account = account;
//		this.setName(account.getUsername());
//		this.setGame(game);
//		this.setPlayerStatus(PlayerStatus.CHALLENGER);
//		this.setPersonalObjectiveCardColor();
//		System.out.println("De personal objectivecard color van " +name+ " is: "+personalObjectiveCardColor);
//
//		playerDBA.addPlayer(this, playerStatus);
//		System.out.println("test2");
//		patternCard = new PatternCard(c);
//		board = new Board(1, this, c);	
//		
//		score = new SimpleIntegerProperty();
//		setScore(-20);
//		
//	}


	public Player(DataBaseConnection c, Game game) {
		connection = c;
		playerDBA = new PlayerDBA(c);
		favorTokenDBA = new FavorTokenDBA(c);
		patternCard = new PatternCard(c);
		this.setPlayerStatus(PlayerStatus.CHALLENGEE);
		this.game = game;
		this.setPersonalObjectiveCardColor();
		System.out.println("De personal objectivecard color van " +name+ " is: "+personalObjectiveCardColor);	
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
		score.set(playerDBA.getScoreFromDB(this));
		System.out.println("DE SCORE VAN DE DATABASE IS: " + score);
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
//		System.out.println("Getting playerstatus of: " + this.getName());
		System.out.println("getplayerusingid" + (playerDBA.getPlayerUsingID(this.getId(), game)).getAccount());
		playerStatus = playerDBA.getPlayerStatusFromDB(playerDBA.getPlayerUsingID(this.getId(), game)); 
		System.out.println("Playerstatus is: " + playerStatus);
		return playerStatus;
	}

	public void setPlayerStatus(PlayerStatus playerStatus) {
		System.out.println("SET PLAYERSTATUS:   " + playerStatus);
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
//		this.personalObjectiveCardColor = ModelColor.BLUE;
		this.personalObjectiveCardColor = ModelColor.randomColor(ModelColor.class);
		
		
	}
	public void setPersonalObjectiveCardColorFromDB(ModelColor modelcolor) {
		
		this.personalObjectiveCardColor = modelcolor;
	}

	public ArrayList<FavorToken> getFavorTokens() {
		FavorTokenDBA favorTokenDBA = new FavorTokenDBA(connection);
		this.favorTokens = favorTokenDBA.getFavortokensOfPlayer(this);
		return favorTokens;
	}

	public void setFavorTokens(ArrayList<FavorToken> favorTokens) {
		this.favorTokens = favorTokens;
	}

	public PatternCard getPatternCard() {
		if(patternCard.getPatterncardID() == 0) {
			if(patternCard
					.getPatterncardDB()
					.getSelectedPatterncardOfPlayer(this.getId(), this) == null) {
				patternCard = patternCard.getPatterncardDB().getPatterncard();
				patternCard.setpattern(false);
			}
			else {
				patternCard = patternCard.getPatterncardDB().getSelectedPatterncardOfPlayer(this.getId(), this);	
			}
		}
		patternCard.setpattern(false);
		return patternCard;
	}

	public void setPatternCard(PatternCard patternCard) {
		playerDBA.setPlayerPatternCard(patternCard, this);
//		this.patternCard = patternCard.getPatterncardDB().getSelectedPatterncardOfPlayer(this.getId(), this);
		this.patternCard = patternCard;
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
//		FavorTokenDBA favorTokenDBA = new FavorTokenDBA(connection);
//		ArrayList<FavorToken> allUnusedGameFavorTokens = favorTokenDBA.getUnusedFavorTokensOfGame(game.getGameID());
//		System.out.println("favortoken 1");
//		ArrayList<FavorToken> favorTokens = new ArrayList<>();
//		System.out.println("favortoken 2");
//		System.out.println(patternCard.getDifficulty());
//		for(int i = 0; i < patternCard.getDifficulty(); i++) {
//			FavorToken favorToken = allUnusedGameFavorTokens.get(0);
//			allUnusedGameFavorTokens.remove(0);
//			System.out.println("favortoken 3");
//			favorTokenDBA.setFavortokenForPlayer(favorToken.getId(), this.getId());
//			favorTokens.add(favorToken);
//		}
//		this.favorTokens = favorTokens;
//		System.out.println("favortoken 4");
//		for (FavorToken favorToken : favorTokens) {
//			System.out.println("Ik hoor bij: " + favorToken.getPlayer());
//		}
		favorTokens = new ArrayList<FavorToken>();
		favorTokens = favorTokenDBA.getFavortokensOfPlayer(this);
		
		if(favorTokens.size() == 0) {
			for (int i = 0; i < patternCard.getDifficulty(); i++) {
				favorToken = new FavorToken(this, game.getGameID(), connection);
				favorToken.addFavorTokenToDB();
				favorTokens.add(favorToken);
				System.out.println("favortoken added");
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
		System.out.println("IK KOM IN CALCULATESCORE");
		int score = 0;
		for(int x = 0; x < Board.BOARD_SQUARES_HORIZONTAL; x++) {
			for(int y = 0; y < Board.BOARD_SQUARES_VERTICAL; y++) {
				System.out.println("TESTER1");
				if(!(board == null)) {
					System.out.println("TESTER2");
					if(!board.getBoardField(x, y).hasDie()) {
						System.out.println("TESTER3");
						score = score - 1;
						System.out.println("SPELER: " + this.getName() + " SCORE -1 =================== " + "BOARDFIELD (" + x + ", " + y + ") SCORE: " + score);
					}
					else if(board.getBoardField(x, y).getDie().getColor().equals(personalObjectiveCardColor)) {
						System.out.println("TESTER4");
						score = score + 1;
						System.out.println("SPELER: " + this.getName() + " SCORE -1 =================== " + "BOARDFIELD (" + x + ", " + y + ") SCORE: " + score);
					}
				}
				else {
					System.out.println("TESTER5");
//					board = new Board(this, connection);
				}
			}
		}
		System.out.println("TESTER6");
		for(FavorToken favorToken : this.getFavorTokens()) {
			score = score + 1;
		}
		
		System.out.println("TESTER7");
		for (PublicObjectiveCard publicObjectiveCard : game.getPublicObjectiveCards()) {
            score = score + publicObjectiveCard.calculateScore(board);
        }
		System.out.println("TESTER8");
		
//		Platform.r
			this.score.set(score);
		
		playerDBA.setScore(this, score);
		 
		return score;
	}
	
	public boolean checkPlacementAgainstRules(int x, int y, ModelColor modelColor, int value) {
//		System.out.println("TEST 1 ========= " + board);
//		System.out.println("TEST 1.x ========= " + x);
//		System.out.println("TEST 1.y ========= " + y);
//		System.out.println("TEST 2 ========= " + board.getBoardField(x, y));
//		System.out.println("TEST 3 ========= " + board.checkAll(board.getBoardField(x, y), modelColor, value));
//		System.out.println("TEST 4 ========= " + modelColor);
//		System.out.println("TEST 5 ========= " + value);
		
		return board.checkAll(board.getBoardField(x, y), modelColor, value);
	}

	public GameDie getSelectedDie() {
		return game.getSelectedDie();
		
	}
	
}
