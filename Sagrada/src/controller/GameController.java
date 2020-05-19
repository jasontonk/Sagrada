package controller;

import java.util.ArrayList;

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
	private DataBaseConnection conn;
	
	public GameController(DataBaseConnection conn) {
		this.conn= conn;
		game =  new Game(conn, true);
		System.out.println("loading...20%");
		dieController = new DieController(conn, this);
		System.out.println("loading...40%");
		patterncardController= new PatterncardController(conn, this);
		System.out.println("loading...60%");
		roundtrackController= new RoundtrackController(game, this);
		System.out.println("loading...80%");
		gameView = new GameView(this);
		System.out.println("loading...100%");
		
	}
	
public ArrayList<PatterncardController> getPatternCardsToChoose(){
		ArrayList<PatterncardController> patterncardControllers = new ArrayList<PatterncardController>();
		ArrayList<PatternCard> patternCard = new ArrayList<PatternCard>();
		patternCard = getCurrentPlayer().getPatternCardsToChoose(this.isRandom());
		
		for(int i = 0;i < 4;i++) {
			
			patterncardControllers.add(new PatterncardController(patternCard.get(i)));	
			
		}
		return patterncardControllers;
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
			showWarning("Dobbelsteen zetten", "De geselecteerde dobbelsteen kan niet op deze plek worden geplaatst.");
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

	public int getCurrentRound() {
		return game.getRound();
	}

	public boolean isRandom() {
		return game.isRandom();
	}
}
