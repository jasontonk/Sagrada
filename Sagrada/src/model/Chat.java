
package model;

import java.sql.Timestamp;

import database.ChatDBA;
import database.DataBaseConnection;
import database.PlayerDBA;

public class Chat {
	private Timestamp time;
	private int playerId;
	private String message;
	private ChatDBA chatDB;
	private PlayerDBA playerDBA;
	private Player player;

	// Partial constructor, generate the player id and message of the player.

	public Chat(int playerid, String message, DataBaseConnection c) {

		playerId = playerid;
		playerDBA = new PlayerDBA(c);
		player = playerDBA.getPlayerById(playerid, null);
		this.message = message;
		chatDB = new ChatDBA(c);

	}

	// Return the time stamp of this chat.

	public Timestamp getTime() {

		return time;
	}

	// Set the time stamp of this chat.
	public void setTime(Timestamp time) {

		this.time = time;
	}
	
	public Player getPlayer() {
		return player;
	}

	// Return the player id of this chat.

	public int getPlayerId() {

		return playerId;
	}

	// Set the player id of this chat.

	public void setPlayerId(int playerId) {

		this.playerId = playerId;
	}

	// Return the message of this chat.

	public String getMessage() {

//		message = chatDB.getChatTextDB(playerid, timestamp);
		return message;										//saved message form DB into message
	}

	// Set the message of this chat.

	public void setMessage(int playerid, String message) {
		chatDB.addChatDB(playerid, message, this);
	}

}
