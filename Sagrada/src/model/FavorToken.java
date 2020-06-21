
package model;

import database.DataBaseConnection;
import database.FavorTokenDBA;

public class FavorToken {

	private int gameId;
	private Player player;
	private FavorTokenDBA favorTokenDB;
	private int favorTokenid;

	// Partial constructor, generate favor tokens of player.
	public FavorToken(Player player, int gameid, DataBaseConnection c) {
		this.player = player;
		gameId = gameid;
		favorTokenDB = new FavorTokenDBA(c);
	}
	
	public void addFavorTokenToDB() {
		favorTokenDB.addFavorToken(gameId, player.getId(),this);
	}

	// Return the game of player.
	public int getGameId() {
		return gameId;
	}

	// Return the player of this FavorToken.
	public Player getPlayer() {
		return player;
	}

	// Set the player of this FavorToken.
	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getFavorTokenid() {
		return favorTokenid;
	}

	public void setFavorTokenid(int favorTokenid) {
		this.favorTokenid = favorTokenid;
	}
	
	
}
