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
			System.out.println(accountController.getAccount());
			for(Player player : accountController.getAccount().getPlayers()) {
				System.out.println(player.getName());
				if (player.getPlayerStatus().equals(PlayerStatus.CHALLENGEE)){
					PlayerController playerController = new PlayerController(accountController);
					playerController.viewInvitation();
				}
			}
			System.out.println("Stop...");
		}
	}
}
