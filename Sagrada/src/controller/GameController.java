package controller;

import database.DataBaseConnection;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Game;
import model.GameDie;
import model.ModelColor;
import model.PatternCard;
import model.Player;
import view.GameView;

public class GameController {

	private Game game;
	private GameView gameView;
	private DieController dieController;
	private PatterncardController patterncardController;
	private RoundtrackController roundtrackController;
	
	public GameController(DataBaseConnection conn) {
		game =  new Game(conn);
		dieController = new DieController(conn, this);
		patterncardController= new PatterncardController(conn, this);
		roundtrackController= new RoundtrackController(null);
		gameView = new GameView(this);
		
	}

	public DieController getDieController() {
		return dieController;
	}

	public GameDie[] getDicePool() {
		return game.getDicePool();
	}

	public void getSelectedDie() {
		
	}

	public PatterncardController getPatterncardController() {
		return patterncardController;
	}

	public RoundtrackController getRoundtrackController() {
		return roundtrackController;
	}

	public void deleteSelectedDie() {
		dieController.deleteSelectedDie(game.getSelectedDie());
		game.setSelectedDie(null);
		
	}
	public void setSelectedDie(GameDie die) {
		game.setSelectedDie(die);
	}

	public GameView getGameView() {
		return gameView;
	}

	public boolean checkPlacementAgainstRules(int x, int y, ModelColor modelColor, int value) {
		if(!game.checkPlacementAgainstRules(x, y, modelColor, value)) {
			showWarning("Dobbelsteen zetten", "De geselecteerde dobbelsteen kan niet op deze plek worden geplaats.");
		}
		return game.checkPlacementAgainstRules(x, y, modelColor, value);
	}

	public Player getCurrentPlayer() {
		return game.getCurrentPlayer();
	}

	public PatternCard getPlayerPatterncard(Player Player) {
		return game.getPlayerPatterncard(Player);
	}

	public ModelColor getSelectedDieColor() {
		if(game.getSelectedDieColor() == null) {
			showWarning("Geselecteerde dobbelsteen", "U heeft geen dobbelsteen geselecteerd");
		}
		return game.getSelectedDieColor();
	}

	public int getSelectedDieValue() {
		return game.getSelectedDieValue();
	}

	public void removeAllBorders() {
		gameView.getDicePoolView().removeAllBorders();
		
	}
	
	public void showWarning(String header, String text) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Let op!");
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();
	}
}
