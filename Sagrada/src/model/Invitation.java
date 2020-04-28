package model;

public class Invitation {
	
	private String message;
	private Player sender;
	private Player receiver;
	
	
	//Constructor that adds sender, receiver and message to the invitation 
	public Invitation(Player sendingPlayer, Player receivingPlayer, String message) {
		addSender(sendingPlayer);
		addReceiver(receivingPlayer);
		addText(message);
	}
	
	//adds the player who sent the invitation
	private void addSender(Player sendingPlayer) {
		sender = sendingPlayer;
	}
	
	//adds the player who should receiver the invitation
	private void addReceiver(Player receivingPlayer) {
		receiver = receivingPlayer;
	}
	
	//adds the message that should be sent with the invitation
	private void addText(String text) {
		message = text;
	}
	
}
