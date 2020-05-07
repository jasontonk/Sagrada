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
	private GameDie[] diesInBag; // 18 per kleur 5 kleuren
	private GameDie[] usedDice ;
	private RoundTrack roundTrack;
	private Chat chat;
	private int round;
	private int gameID;
	private ArrayList<PatternCard> gamePatterncards;
	private ArrayList<PatternCard> playerPatterncards;
	private DataBaseConnection conn;

	public Game(Account account1, Account account2, boolean randomgeneratedpatterncards, DataBaseConnection conn) {// boolean generated toevoegen ja of nee generated patterncards
		this.conn = conn;
		players = new ArrayList<Player>();
		toolcards = new ArrayList<Toolcard>();
		gamePatterncards = new ArrayList<PatternCard>();
		playerPatterncards = new ArrayList<PatternCard>();
		publicObjectiveCards = new ArrayList<PublicObjectiveCard>();
		diesInBag = new GameDie[90];
		usedDice = new GameDie[90];
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
		diesInBag = new GameDie[90];
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
		diesInBag = new GameDie[90];
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

	public Game(int id) {
		gameID = id;
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

	public void selectDice() {
		int amountofdice = players.size() * 2 + 1;
		Random r = new Random();
		GameDie[] offer = new GameDie[9];
		
		while (offer[8] == null) {
			for (int i = 0; i < amountofdice; i++) {
				GameDie selectedDice = diesInBag[r.nextInt(89)];
				if (!checkDieUsed(selectedDice)) {
					offer[i] = selectedDice;
					for (int j = 0; j < usedDice.length; j++) {
						if (usedDice[j] == null) {
							usedDice[j] = selectedDice;
						}
					}
				}
			}
		}
	}
	public boolean checkDieUsed(GameDie selectedDice) {
		for (int i = 0; i < usedDice.length; i++) {
			if (usedDice[i] == selectedDice) {
				return true;
			}
		}
		return false;
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
			diesInBag[i] = new GameDie(ModelColor.GREEN, i, r.nextInt(6)+1);
		}
		for (int i = 18; i < 36; i++) {
			diesInBag[i] = (new GameDie(ModelColor.BLUE, i, r.nextInt(6)+1));
		}
		for (int i = 36; i < 54; i++) {
			diesInBag[i] = (new GameDie(ModelColor.YELLOW, i, r.nextInt(6)+1));
		}
		for (int i = 54; i < 72; i++) {
			diesInBag[i] = (new GameDie(ModelColor.PURPLE, i, r.nextInt(6)+1));
		}
		for (int i = 72; i < 90; i++) {
			diesInBag[i] = (new GameDie(ModelColor.RED, i, r.nextInt(6)+1)); 
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

}
