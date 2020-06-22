package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import controller.AccountController;
import database.DataBaseConnection;
import database.GameDBA;
import database.GameDieDBA;
import database.PlayerDBA;
import database.PublicObjectiveCardDBA;
import database.ToolCardDBA;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Game {
	private ArrayList<Player> players;
	private ArrayList<Account> accounts;
	private ArrayList<Toolcard> toolcards;
	private ArrayList<PublicObjectiveCard> publicObjectiveCards;
	private GameDie[] diceInBag; // 18 per kleur 5 kleuren
	private ArrayList<GameDie> offer;
	private RoundTrack roundTrack;
	private Player currentPlayer;
	private Player personalPlayer;
	private SimpleIntegerProperty round;
	private int gameID;
	private boolean finishedGame;
	private boolean randomPatterncards;
	private ArrayList<PatternCard> gamePatterncards;
	private ArrayList<PatternCard> playerPatterncards;
	private DataBaseConnection conn;
	private GameDie selectedDie;
	private GameDBA gameDBA;
	private boolean finishedTurn;
	private boolean placedDie;
	private boolean usedToolcard;
	private GameDieDBA gamedieDBA;
	private PublicObjectiveCardDBA publicObjectiveCardDBA;
	private PlayerDBA playerDBA;
	private ToolCardDBA toolcardDBA;
	private SimpleStringProperty currentPlayerName;
	private Toolcard selectedToolcard; 
	private GameDie selectedDieFromDicePool;
	private GameDie selectedDieRoundTrack;

	public Game(DataBaseConnection conn, boolean randomgeneratedpatterncards) {

		this.conn = conn;
		this.randomPatterncards = randomgeneratedpatterncards;
		round = new SimpleIntegerProperty();
		round.set(1);
		roundTrack = new RoundTrack(this);
		gameDBA = new GameDBA(conn);
		gamedieDBA = new GameDieDBA(conn);
		publicObjectiveCardDBA = new PublicObjectiveCardDBA(conn);
		toolcardDBA = new ToolCardDBA(conn);
		playerDBA = new PlayerDBA(conn);
		offer = new ArrayList<GameDie>();
		diceInBag = new GameDie[90];
		publicObjectiveCards = new ArrayList<PublicObjectiveCard>();
		toolcards = new ArrayList<Toolcard>();
		currentPlayerName = new SimpleStringProperty();
		players = new ArrayList<Player>();
	}
	
	public Toolcard getSelectedToolcard() {
		return selectedToolcard;
	}

	public void setSelectedToolcard(int id) {
		if (id != 0) {
			for (Toolcard toolcard : toolcards) {
				if (toolcard.getId() == id) {
					this.selectedToolcard = toolcard; 
				}
			}
		} 
		else {
			this.selectedToolcard = null;
		}
	}

	public void addpublicobjectivecards() {
		getPublicObjectiveCardsOfGame();
	}

	public GameDie getSelectedDieRoundTrack() {
		return selectedDieRoundTrack;
	}

	public void setSelectedDieRoundTrack(GameDie selectedDieRoundTrack) {
		this.selectedDieRoundTrack = selectedDieRoundTrack;
	}

	public void addToolcards() {
		getToolcardsOfGame();
	}

	public void addGametoDB() {
		gameDBA.addNewGameDB(LocalDateTime.now(), this);
	}
	
	public GameDie getSelectedDieFromDicePool() {
		return selectedDieFromDicePool;
	}

	public void setSelectedDieFromDicePool(GameDie selectedDieFromDicePool) {
		this.selectedDieFromDicePool = selectedDieFromDicePool;
	}

	public void finishGameSetup(AccountController accountController) {

		this.players = gameDBA.getPlayersOfGame(this);
		currentPlayer = gameDBA.getCurrentPlayer(this);
		if (currentPlayer == null) {
			currentPlayer = players.get(0);
			gameDBA.changeCurrentPlayer(currentPlayer.getId(), this);
			currentPlayerName.set(players.get(0).getName());
		}
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setSequenceNumber(i + 1);
		}
		finishedGame = false;
		placedDie = true;
	}

	public void setPersonalPlayer(Account account) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getName().equals(account.getUsername())) {
				personalPlayer = players.get(i);
				break;
			}
		}
	}

	private void getPublicObjectiveCardsOfGame() {
		ArrayList<PublicObjectiveCard> publicObjectiveCardsOfGameFromDB = publicObjectiveCardDBA
				.getPublicObjectiveCardsOfGame(this);
		if (publicObjectiveCardsOfGameFromDB.size() == 0) {
			ArrayList<PublicObjectiveCard> publicObjectiveCardsFromDB = publicObjectiveCardDBA
					.getAllAvailablePublicObjectiveCards(this);
			int i = 0;
			Random r = new Random();
			while (i < 3) {
				PublicObjectiveCard publicObjectiveCard = publicObjectiveCardsFromDB
						.get(r.nextInt(publicObjectiveCardsFromDB.size()));
				if (publicObjectiveCard != null && !publicObjectiveCards.contains(publicObjectiveCard)) {
					publicObjectiveCards.add(publicObjectiveCard);
					i++;
				}
			}
			for (PublicObjectiveCard publicObjectiveCard2 : publicObjectiveCards) {
				publicObjectiveCardDBA.addPublicObjectiveCardToGame(publicObjectiveCard2, this);
			}
		} else {
			publicObjectiveCards = publicObjectiveCardsOfGameFromDB;
		}
	}

	private void getToolcardsOfGame() {
		ArrayList<Toolcard> toolcardsOfGameFromDB = toolcardDBA.getToolcardsFromGame(this);
		if (toolcardsOfGameFromDB.size() == 0) {
			toolcardsOfGameFromDB = toolcardDBA.get3ToolcardsForAGame();

			for (Toolcard toolcard2 : toolcardsOfGameFromDB) {
				toolcards.add(toolcard2);
				toolcardDBA.addToolCardToGame(this, toolcard2);
			}
		} else if (toolcardsOfGameFromDB.size() == 3) {
			ArrayList<Toolcard> toolcardsFromDB = toolcardDBA.getToolcardsFromGame(this);
			int i = 0;
			Random r = new Random();
			while (i < 3) {
				Toolcard toolcard = toolcardsFromDB.get(r.nextInt(toolcardsFromDB.size()));
				if (toolcard != null && !toolcards.contains(toolcard)) {
					toolcards.add(toolcard);
					i++;
				}
			}

		} else {
			
			toolcards = toolcardsOfGameFromDB;
		}
	}

	public void play() {
		gamesetup();
		while (!finishedGame) {
			playround();
		}
	}

	public void gamesetup() {
		makedie();
		for (Player player : players) {
			setPlayerPatternCards(player);
			player.assignFavorTokens();
			player.setPersonalObjectiveCardColor();// todo color mee geven voor speler
		}
	}

	public void deleteDieFromPatternCard(int x_pos, int y_pos) {
		personalPlayer.getBoard().removedie(selectedDie, x_pos, y_pos);
	}

	public void grabDiceFromBag() {
		int amountofdice = players.size() * 2 + 1;
		Random r = new Random();
		offer = gamedieDBA.getAllRoundDice(this);
		while (offer.size() < amountofdice) {
			for (int i = 0; i < amountofdice; i++) {
				GameDie selectedDice = diceInBag[r.nextInt(89)];
				if (!checkDieUsed(selectedDice)) {
					offer.add(selectedDice);
					selectedDice.setRoundID(this);
				} else {
					i--;
				}
			}
		}
	}

	public void getDicePoolFromDB() {
		ArrayList<GameDie> offerfromDB = gamedieDBA.getAllavailableDiceOfRound(this);
		if (offer != offerfromDB) {
			offer = offerfromDB;
		}
	}

	public boolean checkDieUsed(GameDie selectedDice) {
		if (selectedDice != null) {
			if (selectedDice.getRoundID(this) != 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void playround() {// boolean first round false
		round.set(gameDBA.getCurrentRound(this.getGameID()));
	}

	public void setNextPlayer() {
		if (!(getRoundFromDB() >= 20 && getCurrentPlayer().getSequenceNumber() == 1)) {
			boolean isClockwise = gameDBA.isRoundClockwise(this);

			if (currentPlayer.getSequenceNumber() == players.size() && isClockwise) {
				gameDBA.changeRoundDirection(this);
				round.add(1);
			} else if (currentPlayer.getSequenceNumber() == 1 && !isClockwise) {
				changeSequenceNumber();
				if (!round.equals(20)) {
					gameDBA.changeRoundDirection(this);
					round.add(1);
				}

			} else if (isClockwise) {
				for (int i = players.size() - 1; i >= 0; i--) {

					if (currentPlayer.getSequenceNumber() == players.get(i).getSequenceNumber()) {
						if (i == players.size() - 1) {
							currentPlayer = players.get(0);
							currentPlayerName.set(players.get(0).getName());
						} else {
							currentPlayer = players.get(i + 1);
							currentPlayerName.set(players.get(i + 1).getName());
						}
						gameDBA.changeCurrentPlayer(currentPlayer.getId(), this);
						break;
					}
				}
			} else if (!isClockwise) {
				for (int i = 0; i < players.size(); i++) {
					if (currentPlayer.getSequenceNumber() == players.get(i).getSequenceNumber()) {
						if (i == 0) {
							currentPlayer = players.get(players.size() - 1);
							currentPlayerName.set(players.get(players.size() - 1).getName());
						} else {
							currentPlayer = players.get(i - 1);
							currentPlayerName.set(players.get(i - 1).getName());
						}
						gameDBA.changeCurrentPlayer(currentPlayer.getId(), this);
						break;
					}
				}
			}
		} else {
			finishGame();
		}
	}

	public void addLeftOverDiceToRoundTrack() {
		for (int i = 0; i < offer.size(); i++) {
			gamedieDBA.addDieToRoundTrack(offer.get(i), this, round.get() / 2);// TODO check if right
		}
		offer.clear();
	}

	public void changeSequenceNumber() {
		if (currentPlayer.getSequenceNumber() == 1 && !gameDBA.isRoundClockwise(this)) {

			if (getRoundFromDB() >= 20 && this.getCurrentPlayer().getSequenceNumber() == 1) {
				finishGame();
			} else {
				for (int i = 0; i < players.size(); i++) {
					if (players.get(i).getSequenceNumber() == players.size()) {
						players.get(i).setSequenceNumber(1);
					} else {
						players.get(i).setSequenceNumber(players.get(i).getSequenceNumber() + 1);
					}
				}

				for (int i = 0; i < players.size(); i++) {
					if (players.get(i).getSequenceNumber() == 1) {
						currentPlayer = players.get(i);
						currentPlayerName.set(players.get(i).getName());
					}
				}
				gameDBA.changeCurrentPlayer(currentPlayer.getId(), this);
			}
		}
	}

	public void finishGame() {
		finishedGame = true;
		getPersonalPlayer().setPlayerStatus(PlayerStatus.FINISHED);
	}

	public void makedie() {
		ArrayList<GameDie> diceFromGameFromDB = gamedieDBA.getAllDiceFromGame(this);
		if (diceFromGameFromDB.size() == 0) {
			Random r = new Random();
			for (int i = 0; i < 18; i++) {
				diceInBag[i] = new GameDie(ModelColor.GREEN, i + 1, r.nextInt(6) + 1, this, conn, gamedieDBA);
				diceInBag[i].addDieToDB(this);
				diceInBag[i].setEyes(this);
			}
			for (int i = 0; i < 18; i++) {
				diceInBag[i + 17] = new GameDie(ModelColor.BLUE, i + 1, r.nextInt(6) + 1, this, conn, gamedieDBA);
				diceInBag[i + 17].addDieToDB(this);
				diceInBag[i + 17].setEyes(this);
			}
			for (int i = 0; i < 18; i++) {
				diceInBag[i + 35] = new GameDie(ModelColor.YELLOW, i + 1, r.nextInt(6) + 1, this, conn, gamedieDBA);
				diceInBag[i + 35].addDieToDB(this);
				diceInBag[i + 35].setEyes(this);
			}
			for (int i = 0; i < 18; i++) {
				diceInBag[i + 53] = new GameDie(ModelColor.PURPLE, i + 1, r.nextInt(6) + 1, this, conn, gamedieDBA);
				diceInBag[i + 53].addDieToDB(this);
				diceInBag[i + 53].setEyes(this);
			}
			for (int i = 0; i < 18; i++) {
				diceInBag[i + 71] = new GameDie(ModelColor.RED, i + 1, r.nextInt(6) + 1, this, conn, gamedieDBA);
				diceInBag[i + 71].addDieToDB(this);
				diceInBag[i + 71].setEyes(this);
			}
		} else {
			diceInBag = diceFromGameFromDB.toArray(diceInBag);
		}
	}

	public ArrayList<PatternCard> generategamePatterncards(boolean randomgenerated) {
		ArrayList<PatternCard> patterncards = new ArrayList<PatternCard>();
		for (int i = 0; i < (accounts.size() * 4); i++) {
			PatternCard patterncard = new PatternCard(conn);
			patterncards.add(patterncard);
		}
		return patterncards;
	}

	public void setPlayerPatternCards(Player player) {
		for (int i = 0; i < 4; i++) {
			int randomPatterncardNumber = (int) (Math.random() * gamePatterncards.size());
			playerPatterncards.add(gamePatterncards.get(randomPatterncardNumber));
			gamePatterncards.remove(i);
		}
		player.setPatternCardsToChoose(playerPatterncards);
		playerPatterncards.clear();
	}

	public ArrayList<Player> getPlayers() {
		if (players.size() == 0) {
			players = gameDBA.getPlayersOfGame(this);
		}
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public SimpleIntegerProperty getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round.set(round);
	}

	public void setSelectedDie(GameDie die) {
		this.selectedDie = die;
	}

	public GameDie getSelectedDie() {
		return selectedDie;
	}

	public Player getCurrentPlayer() {
		currentPlayer = gameDBA.getCurrentPlayer(this);
		currentPlayerName.set(currentPlayer.getName());
		if (currentPlayer == null) {
			currentPlayer = gameDBA.getChallengerOfGameWithID(this.getGameID(), this);
			currentPlayerName.set(gameDBA.getChallengerOfGameWithID(this.getGameID(), this).getName());
		}
		return currentPlayer;
	}

	public void setCurrentPlayer(Player player) {
		currentPlayer = player;
		currentPlayerName.set(player.getName());
	}

	public boolean checkPlacementAgainstRules(int x, int y, ModelColor modelColor, int value) {
	
		if (!placedDie) {
			if (personalPlayer == currentPlayer) {
				if (currentPlayer.getBoard() == null) {
					currentPlayer.createBoard();
				}
				if (currentPlayer.checkPlacementAgainstRules(x, y, modelColor, value)) {
					placedDie = true;
					return true;
				}
			}
		}
		return false;
	}

	public PatternCard getPlayerPatterncard(Player player) {
		return player.getPatternCard();
	}

	public ModelColor getSelectedDieColor() {
		if (selectedDie != null) {
			return selectedDie.getColor();
		}
		return null;
	}
	
	public String getSelectedDieColorString() {
		if (selectedDie != null) {
			return selectedDie.getColorString();
		}
		return null;
	}

	public int getSelectedDieValue() {
		if (selectedDie != null) {
			return selectedDie.getEyes();
		}
		return 0;
	}

	public boolean isRandom() {
		return randomPatterncards;
	}

	public void setFinishedTurnTrue() {
		finishedTurn = true;	
	}

	public void setPlacedDie(boolean b) {
		placedDie = b;
	}

	public ArrayList<GameDie> getDiceOnRoundtrack() {
		return gamedieDBA.getDiceOnRoundTrack(this);
	}

	public String getChallengerOfGameWithID(int gameID) {
		return gameDBA.getNameOfChallengerOfGameWithID(gameID);
	}

	public RoundTrack getRoundTrack() {
		return roundTrack;
	}
	
	public void updateOffer(GameDie oldDie, GameDie newDie){
		for(int i = 0; i < offer.size(); i++) {
			if(offer.get(i).getNumber() == oldDie.getNumber() && (offer.get(i).getColorString().equals(oldDie.getColorString()))) {
				System.out.println("offer 1 = "+ offer.size());
				offer.remove(i);
				System.out.println("offer 2 = "+ offer.size());
				offer.add(newDie);
				System.out.println("offer 3 = "+ offer.size());
				break;
			}
		}
	}

	public ArrayList<GameDie> getOffer() {

		int i = 0;
		for (GameDie gameDie : gamedieDBA.getAllDiceFromGame(this)) {
			diceInBag[i] = gameDie;
			i++;
		}
		offer = gamedieDBA.getAllavailableDiceOfRound(this);
		currentPlayer = gameDBA.getCurrentPlayer(this);
		if (currentPlayer.getSequenceNumber() == 1 && currentPlayer.getId() == personalPlayer.getId()
				&& gameDBA.getCurrentRound(this.getGameID()) % 2 == 1 && offer.size() == 0) {
			grabDiceFromBag();
		}
		return offer;
	}
	
	public ArrayList<GameDie> getLocalOffer(){
		return offer;
	}

	public boolean everyoneSelectedPatternCard() {
		for (Player player : players) {
			if (player.getPatternCard() == null) {
				return false;
			}
		}
		return true;
	}

	public ArrayList<PublicObjectiveCard> getPublicObjectiveCards() {
		return publicObjectiveCards;
	}

	public boolean checkSelectedToolcard(int x, int y) {
		if (personalPlayer == currentPlayer) {
			switch (selectedToolcard.getName()) {
			case "Grozing Pliers":
				break;
			case "Eglomise Brush":
				return selectedToolcard.eglomiseBorstel(personalPlayer, personalPlayer.getPatternCard(),
						personalPlayer.getBoard().getBoardField(x, y), personalPlayer.getBoard(),
						selectedDie.getColor(), selectedDie.getEyes());
			case "Copper Foil Burnisher":

				break;
			}
		}
		return false;
	}

	public int[] getPublicObjectiveCardIDs() {
		int[] publicObjectiveCardIDs = new int[3];
		for (int i = 0; i < publicObjectiveCards.size(); i++) {
			publicObjectiveCardIDs[i] = publicObjectiveCards.get(i).getId();
		}
		return publicObjectiveCardIDs;
	}

	public int[] getToolcardIDs() {
		int[] toolcardIDs = new int[3];
		for (int i = 0; i < 3; i++) {
			toolcardIDs[i] = toolcards.get(i).getId();
		}
		return toolcardIDs;
	}

	public boolean isFinishedGame() {
		return finishedGame;
	}

	public ArrayList<String> getWinnerOfGameWithID(int gameID) {
		return gameDBA.getWinnerOfGameUsingGameID(this);
	}

	public Player getPersonalPlayer() {
		return personalPlayer;
	}

	public GameDieDBA getGameDieDBA() {
		return gamedieDBA;
	}

	public SimpleStringProperty getCurrentPlayerName() {
		return currentPlayerName;
	}

	public void setCurrentPlayerName(SimpleStringProperty currentPlayerName) {
		this.currentPlayerName = currentPlayerName;
	}

	public int getRoundFromDB() {
		return gameDBA.getCurrentRound(this.getGameID());
	}

	public Player getPlayerChallengerOfGameWithID(int gameid) {
		return gameDBA.getChallengerOfGameWithID(gameid, this);
	}

	public ArrayList<Player> getChallengeePlayers(Account account) {
		return playerDBA.getChallengeePlayers(account);
	}
	
	public void setGameDieUnused(GameDie gameDie) {
		gamedieDBA.setGameDieUnused(gameDie, this);
	}
	
	public GameDie getUnusedDiceForGame(){
		return gamedieDBA.getUnusedDiceOfGame(this);
	}

	public void addDieToRoundTrack(GameDie dieondicepool,GameDie dieonRoundTrack) {
		gamedieDBA.addDieToRoundTrack(dieondicepool,this,dieonRoundTrack);
	}
	
	public void addDieTodiecePool(int dieID, Game game, GameDie dieonRoundTrack) {
		gamedieDBA.addDieTodiecePool(dieID, this,dieonRoundTrack);
	}

	public Player getLocalCurrentPlayer() {
		return currentPlayer;
	}

	public boolean hasUsedToolcard() {
		return usedToolcard;
	}

	public void setUsedToolcard(boolean usedToolcard) {
		this.usedToolcard = usedToolcard;
	}
}
