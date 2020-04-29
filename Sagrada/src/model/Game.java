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
	private GameDie[] diesInBag;
	private RoundTrack roundTrack;
	private Chat chat;
	private int round;

	public Game(Account account1, Account account2) {
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

	public void checkInvites() {
		for (Account account : accounts) {

			if (account.getInvitations() && account.accepInvitations) {
				Player player1 = new Player();
				account.setPlayers(player1);
				account.setAccountStatus(accountStatus);
				players.add(account.getPlayer());
			}
		}
	}

	public void play() {

	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

}
