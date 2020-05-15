package model;

import java.util.ArrayList;
import java.util.Random;

import database.DataBaseConnection;

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
	private int round;
	private int gameID;
	private ArrayList<PatternCard> gamePatterncards;
	private ArrayList<PatternCard> playerPatterncards;
	private DataBaseConnection conn;
	private GameDie selectedDie;

	public Game(Account account1, Account account2, boolean randomgeneratedpatterncards, DataBaseConnection conn) {// boolean generated toevoegen ja of nee generated patterncards
		this.conn = conn;
		players = new ArrayList<Player>();
		toolcards = new ArrayList<Toolcard>();
		gamePatterncards = new ArrayList<PatternCard>();
		playerPatterncards = new ArrayList<PatternCard>();
		publicObjectiveCards = new ArrayList<PublicObjectiveCard>();
		diceInBag = new GameDie[90];
		usedDice = new ArrayList<>();
		offer = new GameDie[9];
		round = 1;

		accounts.add(account1);
		accounts.add(account2);
		players.add(new Player(conn, account1));
		players.add(new Player(conn, account2));
		
		gamePatterncards = generategamePatterncards(randomgeneratedpatterncards);

//		checkInvites(); 

	}

	public Game(Account account1, Account account2, Account account3, boolean randomgeneratedpatterncards, DataBaseConnection conn) {
		this.conn = conn;
		players = new ArrayList<Player>();
		toolcards = new ArrayList<Toolcard>();
		gamePatterncards = new ArrayList<PatternCard>();
		playerPatterncards = new ArrayList<PatternCard>();
		publicObjectiveCards = new ArrayList<PublicObjectiveCard>();
		diceInBag = new GameDie[90];
		usedDice = new ArrayList<>();
		offer = new GameDie[9];
		round = 1;

		accounts.add(account1);
		accounts.add(account2);
		accounts.add(account3);
		players.add(new Player(conn, account1));
		players.add(new Player(conn, account2));
		players.add(new Player(conn, account3));
		

		gamePatterncards = generategamePatterncards(randomgeneratedpatterncards);
		
//		checkInvites();

	}

	public Game(Account account1, Account account2, Account account3, Account account4, boolean randomgeneratedpatterncards, DataBaseConnection conn) {
		this.conn = conn;
		players = new ArrayList<Player>();
		toolcards = new ArrayList<Toolcard>();
		gamePatterncards = new ArrayList<PatternCard>();
		playerPatterncards = new ArrayList<PatternCard>();
		publicObjectiveCards = new ArrayList<PublicObjectiveCard>();
		diceInBag = new GameDie[90];
		usedDice = new ArrayList<>();
		offer = new GameDie[9];
		round = 1;

		accounts.add(account1);
		accounts.add(account2);
		accounts.add(account3);
		accounts.add(account4);
		players.add(new Player(conn, account1));
		players.add(new Player(conn, account2));
		players.add(new Player(conn, account3));
		players.add(new Player(conn, account4));
		

		
		gamePatterncards = generategamePatterncards(randomgeneratedpatterncards);
		
//		checkInvites();

	}

	public Game(int id, DataBaseConnection conn) {
		gameID = id;
		offer = new GameDie[9];
		players = new ArrayList<Player>();
		currentPlayer = new Player(conn, new Account(conn));
		players.add(currentPlayer);
		
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
		for (int i = 0; i < 9; i++) {
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
		
		
		while (offer[8] == null) {
			for (int i = 0; i < amountofdice; i++) {
				GameDie selectedDice = diceInBag[r.nextInt(89)];
				if (!checkDieUsed(selectedDice)) {
					offer[i] = selectedDice;
					for (int j = 0; j < usedDice.size(); j++) {
						if (usedDice.get(j) == null) {
							selectedDice = usedDice.get(j);
							selectedDice.setRoundID(this);
						}
					}
				}
			}
		}
	}
	
	public void getDicePoolFromDB() {
		offer = diceInBag[0].getAllRoundDice(this).toArray(offer);
		usedDice.addAll(diceInBag[0].getAllRoundDice(this));//TODO find other solution
		
	}
	
	public GameDie[] getDicePool() {
		offer[0] = new GameDie(ModelColor.PURPLE, 1, 5);
		offer[1] = new GameDie(ModelColor.BLUE, 2, 3); 
		offer[2] = new GameDie(ModelColor.RED, 3, 1); 
		offer[3] = new GameDie(ModelColor.GREEN, 4, 2); 
		offer[4] = new GameDie(ModelColor.YELLOW, 5, 4); 
		offer[5] = new GameDie(ModelColor.RED, 6, 6); 
		offer[6] = new GameDie(ModelColor.BLUE, 7, 6); 
		offer[7] = new GameDie(ModelColor.GREEN, 8, 3); 
		offer[8] = new GameDie(ModelColor.PURPLE, 9, 5); 
				
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
		int amountofdice = players.size() * 2 + 1;
//		methode die dobbelstenen selecteert 
//		 dobbelsteen kiezen,passen of toolcard gebruiken // if met speler in put en dan de methode die er aan vast zit passen is door naar volgende speler 
//		 gekozen actie uitvoeren
//		 
//		 dobbelsteen kiezen,einde beurt of toolcard - de eerder gekozen optie 
//		 actie uitvoeren 
//		 volgende speler
//		
//		 voeg overige dobbelstenen aan rondespoor toe
		round++;
	}

	public void makedie() {
		Random r = new Random();
		for (int i = 0; i < 18; i++) {
			diceInBag[i] = new GameDie(ModelColor.GREEN, i, r.nextInt(6)+1);
			diceInBag[i].addDieToDB(this);
		}
		for (int i = 18; i < 36; i++) {
			diceInBag[i] = (new GameDie(ModelColor.BLUE, i, r.nextInt(6)+1));
			diceInBag[i].addDieToDB(this);
		}
		for (int i = 36; i < 54; i++) {
			diceInBag[i] = (new GameDie(ModelColor.YELLOW, i, r.nextInt(6)+1));
			diceInBag[i].addDieToDB(this);
		}
		for (int i = 54; i < 72; i++) {
			diceInBag[i] = (new GameDie(ModelColor.PURPLE, i, r.nextInt(6)+1));
			diceInBag[i].addDieToDB(this);
		}
		for (int i = 72; i < 90; i++) {
			diceInBag[i] = (new GameDie(ModelColor.RED, i, r.nextInt(6)+1));
			diceInBag[i].addDieToDB(this);
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
		for (int i = 0; i < players.size(); i++) {
			if(players.get(i) == currentPlayer) {
				return players.get(i).checkPlacementAgainstRules(x, y, modelColor, value);
			}
		}
		return false;
	}

	public PatternCard getPlayerPatterncard(Player player) {
		return player.getPatternCard();
	}

	public ModelColor getSelectedDieColor() {
		return selectedDie.getColor();
	}

	public int getSelectedDieValue() {
		return selectedDie.getEyes();
	}

}
