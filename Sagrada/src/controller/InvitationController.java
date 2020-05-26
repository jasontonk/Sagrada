package controller;

import javafx.application.Platform;
import model.Player;
import model.PlayerStatus;

public class InvitationController implements Runnable {

	private int seconds;
	private AccountController accountController;
	
	public InvitationController(int i, AccountController accountController) {
		this.seconds = i;
		this.accountController = accountController;
	}

	@Override
	public void run() {
		
		while(true) {
			System.out.println("Invitation Controller Running...");

			for(Player player : accountController.getAccount().getChallengeePlayers()) {
				if (player.getPlayerStatus() == PlayerStatus.CHALLENGEE){
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							accountController.showInvite(player);
						}				
					});
				}
			}
			
			System.out.println("Invitation Controller Stop...");
			try {
				Thread.sleep(seconds * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}