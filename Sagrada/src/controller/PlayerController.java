package controller;

import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import model.Player;
import model.PlayerStatus;

public class PlayerController {
	
	private AccountController accountController;
	
	public PlayerController(AccountController accountController) {
		this.accountController = accountController;
		test();
	}
	
//	public void test() {
//		for (Player player : accountController.getInvitePlayerList()) {
//			if(player.getPlayerStatus().equals(PlayerStatus.CHALLENGEE)) {
//				viewInvitation();
//			}
//		}
//	}
	
	public void viewInvitation() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Invite");
		alert.setHeaderText("Deze speler wil je inviten voor een game");
		alert.setContentText("Wil je dit accepteren?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    // ... user chose OK 
		} else {
		    // ... user chose CANCEL or closed the dialog
		}
	}
}
