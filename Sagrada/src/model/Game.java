package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import controller.GameController;
import database.DataBaseConnection;
import database.GameDBA;
import database.GameDieDBA;

public class Game {
	private ArrayList<Player> players;
	private ArrayList<Account> accounts;
	private Board board;
	private ArrayList<Toolcard> toolcards;
	private ArrayList<PublicObjectiveCard> publicObjectiveCards;
	private GameDie[] diceInBag; // 18 per kleur 5 kleuren
	private ArrayList<GameDie> usedDice ;
	private GameDie[] offer;
	private RoundTrack roundTrack;
	private Chat chat;
	private Player currentPlayer;
	private Player personalPlayer;
	private int round;
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
	private GameDieDBA gamedieDBA = new GameDieDBA(conn);


	public Game(DataBaseConnection conn, boolean randomgeneratedpatterncards) {
		
		this.conn = conn;
		this.randomPatterncards = randomgeneratedpatterncards;
		round = 1;
		roundTrack = new RoundTrack(this);
		gameDBA = new GameDBA(conn);
		gamedieDBA = new GameDieDBA(conn);
		gameDBA.addNewGameDB(LocalDateTime.now(), this);
		offer = new GameDie[9];
		players = new ArrayList<Player>();
		currentPlayer = new Player(conn, new Account("ditis2", "eentest", conn), this, PlayerStatus.CHALLENGER);
		Player player = new Player(conn, new Account("ditiseentest2", "testtest", conn), this, PlayerStatus.CHALLENGEE);
		players.add(currentPlayer);
		players.add(player);
		currentPlayer = players.get(0);
		players.get(0).setSequenceNumber(1);
		players.get(1).setSequenceNumber(2);
		personalPlayer = players.get(0);
		System.out.println(players.get(0).getId());
		System.out.println(players.get(1).getId());
		diceInBag = new GameDie[90];
		usedDice = new ArrayList<GameDie>();
		makedie();
		finishedGame = false;
		placedDie = true;
	}

//	public void checkInvites() {
//		for (Account account : accounts) {
//
//			if (account.getInvitations() && account.accepInvitations) {
//				Player player = new Player();
//				account.setPlayers(player);
//				account.setAccountStatus(accountStatus);
//				players.add(account.getPlayers());
//			}
//		}
//	}

	public void play() {
		gamesetup();
		playfirstround();
		while(!finishedGame) {
			playround();
		}

	}

	public void gamesetup() {
		makedie();
		for (Player player : players) {
			setPlayerPatternCards(player);
			player.assignFavorTokens();
			player.setColor(ModelColor.GREEN);//todo color mee geven voor speler
		}
		for (int i = 0; i < 3; i++) {
//			bepaal public objective
//		bepaal toolcards
		}
	
	}

	public void playfirstround() { // met boolean first round treu

//		methode die dobbelstenen selecteert // hoe gaan we iedere keer amount of dice pakken en dan iedere keer andere dobbelstenen  ? en waar maken we ze aan en hoeveel 
		for (int i = 0; i < players.size(); i++) {

		}
		for (int j = players.size(); j < 0; j--) {
//			dobbelsteen kiezen,passen of toolcard gebruiken
//		 gekozen actie uitvoeren
//		 dobbelsteen kiezen,einde beurt of toolcard - de eerder gekozen optie 
//		 actie uitvoeren 
//		 volgende speler
		}

//		 voeg overige dobbelstenen aan rondespoor toe
//		for each over de overige dobbelstenen 
//		RoundTrack.placedie(die-van-foreach,round);
		round++;

	}

	public void grabDiceFromBag() {
		int amountofdice = players.size() * 2 + 1;
		Random r = new Random();
		
		while (offer[amountofdice-1] == null) {
			for (int i = 0; i < amountofdice; i++) {
				GameDie selectedDice = diceInBag[r.nextInt(89)];
				if (!checkDieUsed(selectedDice)) {
					offer[i] = selectedDice;
					usedDice.add(selectedDice);
					for (int j = 0; j < usedDice.size(); j++) {
						if (usedDice.get(j).getRoundID(this) == 0) {
							selectedDice.setRoundID(this);
						}
					}
				}
				else {
					i--;
				}
			}
		}
	}
	
	public void getDicePoolFromDB() {
		offer = diceInBag[0].getAllRoundDice(this).toArray(offer);
		usedDice.addAll(diceInBag[0].getAllRoundDice(this));//TODO find other solution
		
	}
	
	public GameDie[] getDicePool() {
		grabDiceFromBag();
		return offer;
	}
	
	public boolean checkDieUsed(GameDie selectedDice) {
		if(selectedDice.getRoundID(this) != 0){
			return true;
		}
		else {
			return false;
		}
	}

	public void playround() {// boolean first round false
		System.out.println("round: " +round+ " Databaseround: "+gameDBA.getCurrentRound(this.getGameID()));
		if(round == gameDBA.getCurrentRound(this.getGameID())) {
			if(currentPlayer == personalPlayer) {
				playTurn();
			}
			else {
				System.out.println("wacht tot een andere speler de beurt beindigt");
			}
		}
		round = gameDBA.getCurrentRound(this.getGameID());
	}
	
	public void playTurn() {
		System.out.println(currentPlayer.getId());
			/* 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 */
			System.out.println("still your turn");
	}
	public void setNextPlayer() {
			boolean isClockwise = gameDBA.isRoundClockwise(this);
			
			if(currentPlayer.getSequenceNumber() == players.size() && isClockwise) {
				gameDBA.changeRoundDirection(this);
				round++;
			}
			else if(currentPlayer.getSequenceNumber() == 1 && !isClockwise) {
				changeSequenceNumber();
				gameDBA.changeRoundDirection(this);
				round++;
			}
			else if(isClockwise) {
				for (int i = players.size()-1; i >= 0; i--) {
					
					if(currentPlayer.getSequenceNumber() == players.get(i).getSequenceNumber()) {
						if(i == players.size()-1) {
							currentPlayer = players.get(0);
						}
						else {
							currentPlayer = players.get(i+1);
						}
						gameDBA.changeCurrentPlayer(currentPlayer.getId(), this);
						break;
					}
				}
			} 
			else if(!isClockwise){
				for (int i = 0; i < players.size(); i++) {
					if(currentPlayer.getSequenceNumber() == players.get(i).getSequenceNumber()) {
						if(i == 0) {
							currentPlayer = players.get(players.size()-1);
						}
						else {
							currentPlayer = players.get(i-1);
						}
						gameDBA.changeCurrentPlayer(currentPlayer.getId(), this);
						break;
					}
				}
			} 
			System.out.println(currentPlayer.getId());
	}
	public void changeSequenceNumber() {
		System.out.println("stap 1");
		if(currentPlayer.getSequenceNumber() == 1 && !gameDBA.isRoundClockwise(this)) {
			System.out.println("stap 2");
			for (int i = 0; i < players.size(); i++) {
				if(players.get(i).getSequenceNumber() == players.size()) {
					System.out.println("stap 3.1");
					players.get(i).setSequenceNumber(1);
				}
				else {
					System.out.println("stap 3.2");
					players.get(i).setSequenceNumber(players.get(i).getSequenceNumber()+1);
				}
			}
			if(round >= 20) {
				finishedGame = true;
			}
			for (int i = 0; i < players.size(); i++) {
				if(players.get(i).getSequenceNumber() == 1) {
					currentPlayer = players.get(i);
				}
			}
			gameDBA.changeCurrentPlayer(currentPlayer.getId(), this);
		}
	}

	private boolean getFinishedTurn() {
		return finishedTurn;
	}

	public void makedie() {
		Random r = new Random();
		for (int i = 0; i < 18; i++) {
			diceInBag[i] = new GameDie(ModelColor.GREEN, i+1, r.nextInt(6)+1, this, conn, gamedieDBA);
			diceInBag[i].addDieToDB(this);
			diceInBag[i].setEyes(this);
		}
		for (int i = 0; i < 18; i++) {
			diceInBag[i+17] = new GameDie(ModelColor.BLUE, i+1, r.nextInt(6)+1, this, conn, gamedieDBA);
			diceInBag[i+17].addDieToDB(this);
			diceInBag[i+17].setEyes(this);
		}
		for (int i = 0; i < 18; i++) {
			diceInBag[i+35] = new GameDie(ModelColor.YELLOW, i+1, r.nextInt(6)+1, this, conn, gamedieDBA);
			diceInBag[i+35].addDieToDB(this);
			diceInBag[i+35].setEyes(this);
		}
		for (int i = 0; i < 18; i++) {
			diceInBag[i+53] = new GameDie(ModelColor.PURPLE, i+1, r.nextInt(6)+1, this, conn, gamedieDBA);
			diceInBag[i+53].addDieToDB(this);
			diceInBag[i+53].setEyes(this);
		}
		for (int i = 0; i < 18; i++) {
			diceInBag[i+71] = new GameDie(ModelColor.RED, i+1, r.nextInt(6)+1, this, conn, gamedieDBA);
			diceInBag[i+71].addDieToDB(this);
			diceInBag[i+71].setEyes(this);
		}
	}
	
	public ArrayList<PatternCard> generategamePatterncards(boolean randomgenerated){
		ArrayList<PatternCard> patterncards = new ArrayList<PatternCard>();
		for(int i = 0;i<(accounts.size()*4);i++){
			PatternCard patterncard = new PatternCard(conn);
			patterncards.add(patterncard);
		}
		return patterncards;
	}
	public void setPlayerPatternCards(Player player) {
		for(int i = 0; i < 4; i++) {
			int randomPatterncardNumber = (int)(Math.random() * gamePatterncards.size());
			playerPatterncards.add(gamePatterncards.get(randomPatterncardNumber));
			gamePatterncards.remove(i);
		}
		player.setPatternCardsToChoose(playerPatterncards);
		playerPatterncards.clear();
	}

//	public Player announceWinner() {
//		
//		return Player;  
//	}

	public ArrayList<Player> getPlayers() {
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

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public void setSelectedDie(GameDie die) {
		this.selectedDie = die;
	}

	public GameDie getSelectedDie() {
		return selectedDie;
	}
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer() {
		for (int i = 0; i < players.size(); i++) {
			if(players.get(i).isCurrentPlayer()) {
				currentPlayer = players.get(i);
				break;
			}
		}
	}


	public boolean checkPlacementAgainstRules(int x, int y, ModelColor modelColor, int value) {
		if(!placedDie) {
			if(personalPlayer == currentPlayer) {
				if(currentPlayer.checkPlacementAgainstRules(x, y, modelColor, value)) {
					placedDie = true;
					//die uit dicepool halen
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
		if(selectedDie != null) {
			return selectedDie.getColor();
		}
		return null;
	}

	public int getSelectedDieValue() {
		if(selectedDie != null) {
			return selectedDie.getEyes();
		}
		return 0;
	}

	public boolean isRandom() {
		// TODO Auto-generated method stub
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
		return gameDBA.getChallengerOfGameWithID(gameID);
	}

	public RoundTrack getRoundTrack() {
		return roundTrack;
	}

}
