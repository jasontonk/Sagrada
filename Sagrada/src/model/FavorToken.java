
package model;

import database.DataBaseConnection;
import database.FavorTokenDBA;

public class FavorToken {

	private int gameId;
	private Player player;
	private Toolcard toolcard;
	private FavorTokenDBA favorTokenDB;

	// Partial constructor, generate favor tokens of player.
	public FavorToken(Player player, int gameid, DataBaseConnection c) {
		this.player = player;
		gameId = gameid;
		favorTokenDB = new FavorTokenDBA(c);
	}
	
	public FavorToken(int id, int gameId, DataBaseConnection c) {//added constructor to fix error in FaverTokenDBA
		this.gameId = gameId;
		favorTokenDB = new FavorTokenDBA(c);
	}
	
	public void addFavorTokenToDB() {
		favorTokenDB.addFavorToken(gameId, player.getId());
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

	// Return the Toolcard of which this FavorToken is used for.
	public Toolcard getToolcard() {
		return toolcard;
	}

	// Set the Toolcard of which this FavorToken is used for.
	public void setToolcard(int playerid, int toolcard, int round, int favortokenid) {
		favorTokenDB.setFavortokensForToolCard(playerid, toolcard, round, favortokenid);
	}
}
