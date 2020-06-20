package controller;

import database.DataBaseConnection;
import model.GameDie;
import model.ModelColor;
import model.PatternCard;

public class PatterncardController {

	private PatternCard patterncard;
	private GameController gameController;

	public PatterncardController(DataBaseConnection conn, GameController gameController) {
		this.gameController = gameController;
		patterncard = gameController.getGame().getPersonalPlayer().getBoard().getPatternCard();
		if (patterncard.getPatterncardID() == 0) {
			patterncard = new PatternCard(conn);
		}
		patterncard.setpattern(false);
	}

	public PatterncardController(PatternCard patterncard) {
		this.patterncard = patterncard;
		this.patterncard.setpattern(false);
	}

	public ModelColor getFieldColor(int x, int y) {
		return patterncard.getFieldColor(x, y);
	}

	public int getFieldValue(int x, int y) {
		return patterncard.getFieldValue(x, y);
	}

	public String getName() {
		return patterncard.getName();

	}

	public int getDifficulty() {
		return patterncard.getDifficulty();
	}

	public String getSelectedDieUrl() {
		if (gameController.getGame().getSelectedToolcard() == null) {
			return gameController.getDieController().getSelectedDieURL();
		} else {
			return "/images/" + gameController.getSelectedDieColor() + gameController.getSelectedDieValue()
					+ "_Die.png";
		}
	}

	public void deleteDieFromPool() {
		gameController.deleteSelectedDie();
	}

	public boolean checkPlacementAgainstRules(int x, int y, ModelColor modelColor, int value) {
		return gameController.checkPlacementAgainstRules(x, y, modelColor, value);
	}

	public ModelColor getSelectedDieColor() {
		return gameController.getSelectedDieColor();
	}

	public int getSelectedDieValue() {
		return gameController.getSelectedDieValue();
	}

	public GameController getGameController() {
		return gameController;
	}

	public PatternCard getPatterncard() {
		return patterncard;
	}

	public void setSelectedDie(int x, int y) {
		GameDie gameDie = gameController.getGame().getPersonalPlayer().getBoard().getBoardField(x, y).getDie();
		if (gameDie != null) {
			gameController.setSelectedDie(gameDie);
		}
	}

	public void deleteDieFromPatternCard() {
		gameController.deleteSelectedDie();
	}
}
