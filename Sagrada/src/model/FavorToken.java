<<<<<<< Upstream, based on branch 'master' of https://github.com/jasontonk/Sagrada
package model;

public class FavorToken {

	private int id;
	private Game game;
	private Player player;
	private Toolcard toolcard;

	// Partial constructor, generate favor tokens of player.

	public FavorToken(int id, Player player) {

		this.id = id;

		this.player = player;

		this.game = player.getGame();
		

	}
	
	public FavorToken(int id) {
		
	}

	// Return the id of FavorToken.

	public int getId() {

		return id;
	}

	// Set the id of FavorToken.

	public void setId(int id) {

		this.id = id;
	}

	// Return the game of player.

	public Game getGame() {

		return game;
	}

	// Set the game of is FavorToken is located.

	public void setGame(Game game) {

		this.game = game;
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

	public void setToolcard(Toolcard toolcard) {

		this.toolcard = toolcard;
	}

}
=======
package model;

import database.DataBaseConnection;
import database.FavorTokenDBA;

public class FavorToken {

	private int id;
	private int gameId;
	private Player player;
	private Toolcard toolcard;
	private FavorTokenDBA db;

	// Partial constructor, generate favor tokens of player.

	public FavorToken(int id, Player player, int gameid, DataBaseConnection c) {

		this.id = id;

		this.player = player;

		gameId = gameid;

		db = new FavorTokenDBA(c);

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
		db.addFavorToken(gameid);
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
		db.setFavortokensForToolCard(playerid, toolcard, round, favortokenid);

	}

}
>>>>>>> 9bc128a chat en favortoken klassen gekoppeld aan database.
