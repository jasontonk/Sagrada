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