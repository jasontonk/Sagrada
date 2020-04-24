package model;

import java.sql.Timestamp;

public class Chat {
	private Timestamp time;
	private int playerId;
	private String message;

	public Chat(int playerid, String message) {
		playerId = playerid;
		this.message = message;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
