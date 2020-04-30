<<<<<<< Upstream, based on branch 'master' of https://github.com/jasontonk/Sagrada
package model;

import java.sql.Timestamp;

public class Chat {
	private Timestamp time;
	private int playerId;
	private String message;

	// Partial constructor, generate the player id and message of the player.

	public Chat(int playerid, String message) {

		playerId = playerid;

		this.message = message;
	}

	// Return the time stamp of this chat.

	public Timestamp getTime() {

		return time;
	}

	// Set the time stamp of this chat.
	public void setTime(Timestamp time) {

		this.time = time;
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

		return message;
	}

	// Set the message of this chat.

	public void setMessage(String message) {

		this.message = message;
	}
}
=======
package model;

import java.sql.Timestamp;

import database.ChatDBA;
import database.DataBaseConnection;

public class Chat {
	private Timestamp time;
	private int playerId;
	private String message;
	private ChatDBA chatDB;

	// Partial constructor, generate the player id and message of the player.

	public Chat(int playerid, String message, DataBaseConnection c) {

		playerId = playerid;

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

	// Return the player id of this chat.

	public int getPlayerId() {

		return playerId;
	}

	// Set the player id of this chat.

	public void setPlayerId(int playerId) {

		this.playerId = playerId;
	}

	// Return the message of this chat.

	public String getMessage(int playerid, Timestamp timestamp) {

		return chatDB.getChatTextDB(playerid, timestamp);
	}

	// Set the message of this chat.

	public void setMessage(int playerid, String message, Chat chatline) {
		chatDB.addChatDB(playerid, message, this);
	}

}
>>>>>>> 9bc128a chat en favortoken klassen gekoppeld aan database.
