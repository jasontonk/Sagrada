
package model;

import database.DataBaseConnection;
import database.FavorTokenDBA;

public class FavorToken {

	private int id;
	private int gameId;
	private Player player;
	private Toolcard toolcard;
	private FavorTokenDBA favorTokenDB;

	// Partial constructor, generate favor tokens of player.

	public FavorToken(int id, Player player, int gameid, DataBaseConnection c) {

		this.id = id;

		this.player = player;

		gameId = gameid;

		favorTokenDB = new FavorTokenDBA(c);

	}

	// Return the id of FavorToken.

	public int getId() {
		return 1;
	}

	// Return the game of player.

	public int getGameId() {

		return gameId;
	}

	// Set the game of is FavorToken is located.

	public void setGameId(int gameid) {
		favorTokenDB.addFavorToken(gameid);
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
