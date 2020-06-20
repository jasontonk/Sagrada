package controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import model.Player;

public class InvitationController extends Task<Void> {

	private int seconds;
	private AccountController accountController;
	private volatile boolean isRunning;
	
	public InvitationController(int i, AccountController accountController) {
		this.seconds = i;
		this.accountController = accountController;
		isRunning = true;
	}
	
	@Override
	protected Void call() throws NullPointerException {
		while(isRunning) {
			try {
				Thread.sleep(seconds * 1000);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("Invitation Controller Running...");

					Platform.runLater(new Runnable() {
						
					@Override
					public void run() {
						accountController.updateLobbyView();
						
						for(Player player : accountController.getAccount().getChallengeePlayers()) {
							accountController.showInvite(player);
						}
					}
				});
			System.out.println("Invitation Controller Stop...");	
		}
		return null;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}
