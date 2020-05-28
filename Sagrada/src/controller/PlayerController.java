package controller;

import java.util.ArrayList;

import model.Player;

public class PlayerController {
	
	private AccountController accountController;
	private Player player;
	
	public PlayerController(AccountController accountController, Player player) {
		this.accountController = accountController;
		this.player = player;
	}

//	public void viewOptionalPatternCards() {
//		ArrayList<PatternCard> patternCards =
//                new PatternCardDao().getOptionalPatternCardsOfPlayer(player);
//        if (patternCards.size() == 4) {
//            PatternCardSelectionView patternCardSelectionView = new PatternCardSelectionView(this);
//            patternCardSelectionView.setOptionalPatternCards(patternCards);
//            patternCardSelectionView.render();
//            pane.getChildren().add(patternCardSelectionView);
//            myScene.setContentPane(pane);
//        }
//	}

}
