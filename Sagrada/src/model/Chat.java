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
