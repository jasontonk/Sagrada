package controller;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import model.Account;
import model.Player;
import model.PlayerStatus;

public class InvitationController implements Runnable {

	private Account account;
	private int seconds;
	private AccountController accountController;
	
	public InvitationController(Account account, int i, AccountController accountController) {
		this.account = account;
		this.seconds = i;
		this.accountController = accountController;
	}

	@Override
	public void run() {
		
		while(true) {
			try {
				Thread.sleep(seconds * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Running...");
			
			for(Player player : account.getPlayers()) {
				if (player.getPlayerStatus().equals(PlayerStatus.CHALLENGEE)){
	
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Invite");
						alert.setHeaderText("Deze speler wil je inviten voor een game");
						alert.setContentText("Wil je dit accepteren?");
	
						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK){
						   player.setPlayerStatus(PlayerStatus.ACCEPTED); 
						} else {
							player.setPlayerStatus(PlayerStatus.REFUSED); 
						}
				}
			}
			System.out.println("Stop...");
		}
	}
}
