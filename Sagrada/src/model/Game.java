package model;

import java.util.ArrayList;

public class Game {
// add players
// add roundtrack
	// add toolcard
	// add public objective
//add gamedie	//add chat ?
	private ArrayList<Player> players;
	private ArrayList<Account> accounts;
	private Board board;
	private ArrayList<Toolcard> toolcards;
	private ArrayList<PublicObjectiveCard> publicObjectiveCards;
	private GameDie[] diesInBag; //18 per kleur 5 kleuren
	private RoundTrack roundTrack;
	private Chat chat;
	private int round;
	private int gameID;
	
	public Game(Account account1, Account account2) {// boolean generated toevoegen ja of nee generated patterncards
		players = new ArrayList<Player>();
		toolcards = new ArrayList<Toolcard>();
		publicObjectiveCards = new ArrayList<PublicObjectiveCard>();
		diesInBag = new GameDie[90];
		round = 1;

		accounts.add(account1);
		accounts.add(account2);

		checkInvites();

	}

	public Game(Account account1, Account account2, Account account3) {
		players = new ArrayList<Player>();
		toolcards = new ArrayList<Toolcard>();
		publicObjectiveCards = new ArrayList<PublicObjectiveCard>();
		diesInBag = new GameDie[90];
		round = 1;

		accounts.add(account1);
		accounts.add(account2);
		accounts.add(account3);

		checkInvites();

	}

	public Game(Account account1, Account account2, Account account3, Account account4) {
		players = new ArrayList<Player>();
		toolcards = new ArrayList<Toolcard>();
		publicObjectiveCards = new ArrayList<PublicObjectiveCard>();
		diesInBag = new GameDie[90];
		round = 1;

		accounts.add(account1);
		accounts.add(account2);
		accounts.add(account3);
		accounts.add(account4);

		checkInvites();

	}
	
	public Game(int id) {
		gameID = id;
	}

	public void checkInvites() {
		for (Account account : accounts) {

			if (account.getInvitations() && account.accepInvitations) {
				Player player = new Player();
				account.setPlayers(player);
				account.setAccountStatus(accountStatus);
				players.add(account.getPlayer());
			}
		}
	}

	public void play() {
		gamesetup();
		playfirstround();
		for (int i = 0; i < 9; i++) {
			playround();
		}

	}
	
	public void gamesetup() {
		for (players player : players) {
//			selecteer 2 patterncards met 4 patronen 
//			selecteer 1 pattern
			player.setpatterncard();
			player.assignFavorTokens();
			player.setcolor();
		}
//		bepaal public objective
//			bepaal toolcards
	}
	
	public void playfirstround() { // met boolean first round treu 
		int amountofdice = players.size()*2+1;
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
		RoundTrack.placedie(die-van-foreach,round);
		 round++;
		
		
		
		
		
	}
	
	public void playround() {// boolean first round false
		int amountofdice = players.size()*2+1;
//		methode die dobbelstenen selecteert 
//		 dobbelsteen kiezen,passen of toolcard gebruiken // if met speler in put en dan de methode die er aan vast zit passen is door naar volgende speler 
//		 gekozen actie uitvoeren
//		 
//		 dobbelsteen kiezen,einde beurt of toolcard - de eerder gekozen optie 
//		 actie uitvoeren 
//		 volgende speler
		
		 voeg overige dobbelstenen aan rondespoor toe
		 round++;
	}

	public void makedice() {
		
	}
	
	public Player announcewinner() {
		
		
	}
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
