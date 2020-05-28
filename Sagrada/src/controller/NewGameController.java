package controller;

import javafx.application.Platform;
import model.Player;

public class NewGameController implements Runnable {

	private int seconds;
	private AccountController accountController;
	
	public NewGameController(int i, AccountController accountController) {
		this.seconds = i;
		this.accountController = accountController;
	}

	@Override
	public void run() {
		
		while(true) {
			System.out.println("NewGameController Running...");

			for(Player player : accountController.getAccount().getStartPlayers()) {
				
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							accountController.joinGame(player, player.getGame());;
						}				
					});
			}
			
			System.out.println("NewGameController Stop...");
			try {
				Thread.sleep(seconds * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	}
}

