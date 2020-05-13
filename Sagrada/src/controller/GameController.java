package controller;

import database.DataBaseConnection;
import javafx.scene.Parent;
import model.Game;
import model.GameDie;
import view.GameView;

public class GameController {

	private Game game;
	private GameView gameView;
	private DieController dieController;
	private PatterncardController patterncardController;
	private RoundtrackController roundtrackController;
	
	public GameController(DataBaseConnection conn) {
		game =  new Game(26);
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
}
