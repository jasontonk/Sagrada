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
			
			System.out.println("Running...");
//			System.out.println(accountController.getAccount().getPlayers());
			for(Player player : accountController.getAccount().getPlayers()) {
//				System.out.println("=====================================================");
//				System.out.println("Player name: " + player.getName());
//				System.out.println( "Player: "+ player );
//				System.out.println( "Playerstatus: "+  player.getPlayerStatus());
//				System.out.println("=====================================================");
				if (player.getPlayerStatus() == PlayerStatus.CHALLENGEE){
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							accountController.showInvite(player);
						}
						
					});
				}
			}
			System.out.println("Stop...");
			
			try {
				Thread.sleep(seconds * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}