package model;

import java.util.ArrayList;

import controller.CalculateScoreTask;
import database.DataBaseConnection;
import database.FavorTokenDBA;
import database.PatternCardDBA;
import database.PlayerDBA;

public class Player {
	
	private int id;
	private int sequenceNumber;
	private int score;
	private String name;
	private PlayerStatus playerStatus;
	
	
	private boolean isCurrentPlayer;
	private boolean placedDie;
	
	private Account account;
	private FavorToken favorToken;
	private ModelColor modelColor;
	private Board board;
	
	private ArrayList<FavorToken> favorTokens;
	private PatternCard patternCard;
	private ArrayList<PatternCard> patternCardsToChoose;
	
	private Game game;
	private DataBaseConnection connection;
	
	private PlayerDBA playerDBA ;
	
	public Player(DataBaseConnection c, Account account, Game game, PlayerStatus playerStatus) {
		connection = c;
		playerDBA = new PlayerDBA(c);
		this.account = account;
		this.setName(account.getUsername());
		this.setGame(game);
		this.setPlayerStatus(PlayerStatus.CHALLENGER);

		modelColor = modelColor.RED;
		this.setColor(modelColor);


		playerDBA.addPlayer(this, playerStatus);
		System.out.println("test2");
		patternCard = new PatternCard(c);
		board = new Board(1, this, c);		
		
	}


	public Player(DataBaseConnection c) {
		connection = c;
		playerDBA = new PlayerDBA(c);
		this.setPlayerStatus(PlayerStatus.CHALLENGEE);

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

	public PlayerStatus getPlayerStatus() {
		System.out.println("Getting playerstatus of: " + this.getName());
		System.out.println("getplayerusingid" + (playerDBA.getPlayerUsingID(this.getId())).getAccount());
		playerStatus = playerDBA.getPlayerStatusFromDB(playerDBA.getPlayerUsingID(this.getId())); 
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

	public ModelColor getColor() {
		return modelColor;
	}

	public void setColor(ModelColor modelColor) {
		this.modelColor = modelColor;
	}

	public ArrayList<FavorToken> getFavorTokens() {
		FavorTokenDBA favorTokenDBA = new FavorTokenDBA(connection);
		this.favorTokens = favorTokenDBA.getFavortokensOfPlayer(getId(), this);
		return favorTokens;
	}

	public void setFavorTokens(ArrayList<FavorToken> favorTokens) {
		this.favorTokens = favorTokens;
	}

	public PatternCard getPatternCard() {
		patternCard.setpattern(false);
		return patternCard;
	}

	public void setPatternCard(PatternCard patternCard) {
		this.patternCard = patternCard;

	}

	public ArrayList<PatternCard> getPatternCardsToChoose(boolean random) {
//		PatternCardDBA patternCardDBA = new PatternCardDBA(connection);
//		patternCardsToChoose = patternCardDBA.getOptionalPatternCardsOfPlayer(getId(), this);
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
		FavorTokenDBA favorTokenDBA = new FavorTokenDBA(connection);
		ArrayList<FavorToken> allUnusedGameFavorTokens = favorTokenDBA.getUnusedFavorTokensOfGame(game.getGameID());
		ArrayList<FavorToken> favorTokens = new ArrayList<>();
		for(int i = 0; i < patternCard.getDifficulty(); i++) {
			FavorToken favorToken = allUnusedGameFavorTokens.get(0);
			allUnusedGameFavorTokens.remove(0);
			favorTokenDBA.setFavortokenForPlayer(favorToken.getId(), this.getId());
			favorTokens.add(favorToken);
		}
		this.favorTokens = favorTokens;
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
	
	public CalculateScoreTask calculateScoreTask() {
		CalculateScoreTask cst = new CalculateScoreTask(this);
        return cst;
	}
	
	public int calculateScore() {
		if (patternCard == null) {
            patternCard = getPatternCard();
        }
		
		int score = 0;
		for(int x = 0; x < Board.BOARD_SQUARES_HORIZONTAL; x++) {
			for(int y = 0; y < Board.BOARD_SQUARES_VERTICAL; y++) {
				if(!board.getBoardField(x, y).hasDie()) {
					score = score - 1;
					System.out.println("SPELER: " + this.getName() + " SCORE -1 =================== " + "BOARDFIELD (" + x + ", " + y + ") SCORE: " + score);
				}
				else if(board.getBoardField(x, y).getDie().getColor().equals(modelColor)) {
					score = score + 1;
					System.out.println("SPELER: " + this.getName() + " SCORE -1 =================== " + "BOARDFIELD (" + x + ", " + y + ") SCORE: " + score);
				}
			}
		}
		
		for(FavorToken favorToken : this.getFavorTokens()) {
			score = score + 1;
		}
		
		//TODO publicobjective
		
		this.score = score;
		
	
		playerDBA.setScore(this);
		 
		return score;
	}
	public boolean checkPlacementAgainstRules(int x, int y, ModelColor modelColor, int value) {
		return board.checkAll(board.getBoardField(x, y), modelColor, value);
	}

	public GameDie getSelectedDie() {
		return game.getSelectedDie();
		
	}
	
}
