//package controller;
//
//import java.util.ArrayList;
//
//import javafx.application.Platform;
//import javafx.concurrent.Task;
//import model.Player;
//import model.PlayerStatus;
//
//public class InvitationController extends Task<Void> {
//
//	private int seconds;
//	private AccountController accountController;
//	private volatile boolean isRunning;
//	private ArrayList<Player>players;
//	
//	public InvitationController(int i, AccountController accountController) {
//		this.seconds = i;
//		this.accountController = accountController;
//		isRunning = true;
//		players = new ArrayList<Player>();
//	}
//	
//	@Override
//	protected Void call() throws NullPointerException {
//		while(isRunning) {
//			try {
//				Thread.sleep(seconds * 1000);
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//			System.out.println("Invitation Controller Running...");
//
//					Platform.runLater(new Runnable() {
//						
//					@Override
//					public void run() {
//						addPlayers();
//						accountController.
//						getLobbyView().
//						update();
//					}
//					
//				});
//					
//					System.out.println("Invitation Controller Stop...");
//					
//			
//			
//		}
//		return null;
//	}
//
//	public void setRunning(boolean isRunning) {
//		this.isRunning = isRunning;
//	}
//	
//	private void addPlayers() {
//		players.clear();
//		
//		for(Player player : accountController.getAccount().getChallengeePlayers()) {
//			Player challengerPlayer = player.getGame().getPlayerChallengerOfGameWithID(player.getGame().getGameID());
//			players.add(challengerPlayer);
//		}
//		
//		
//		
//		
//		System.out.println("ADDplayerSize"+ players.size());
//		if(players.size()!=0) {
//			accountController.setReceivedInvitations(players);
//		}
//		
//	}
//
//	
//}




package controller;

import javafx.application.Platform;
import model.Player;
import model.PlayerStatus;

public class InvitationController implements Runnable {

	private int seconds;
	private AccountController accountController;
	private volatile boolean isRunning;
	
	public InvitationController(int i, AccountController accountController) {
		this.seconds = i;
		this.accountController = accountController;
		isRunning = true;
	}

	@Override
	public void run() {
		while(isRunning) {
			System.out.println("Invitation Controller Running...");

					Platform.runLater(new Runnable() {
						
					@Override
					public void run() {
						for(Player player : accountController.getAccount().getChallengeePlayers()) {
							accountController.showInvite(player);
						}			
					}
				});
					
			
			
			System.out.println("Invitation Controller Stop...");
			try {
				Thread.sleep(seconds * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}