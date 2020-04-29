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
