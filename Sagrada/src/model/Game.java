package model;

import java.util.ArrayList;

public class Game {
// add players
// add roundtrack
	//add toolcard
	//add public objective
//add gamedie	//add chat ?
	private ArrayList<Player> players;
	private Board board;
	private ArrayList<Toolcard> toolcards;
	private ArrayList<PublicObjectiveCard> publicObjectiveCards;
	private GameDie[] diesInBag;
	private RoundTrack roundTrack;
	private Chat chat;
	private int round;
	
	public void play() {
		
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

}
