package controller;

import java.util.ArrayList;
import java.util.Timer;

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
import view.MyScene;
import view.PatterncardSelectionView;
import view.PatterncardView;

public class GameController {

	private Game game;
	private GameRoundPlayer gameRoundPlayer;
	private GameUpdater gameUpdater;
	private GameView gameView;
	private DieController dieController;
	private PatterncardController patterncardController;
	private RoundtrackController roundtrackController;
	private DataBaseConnection conn;
	private MyScene myScene;


	private PatterncardView patterncardView;
	private PatterncardSelectionView patterncardSelectionView;
	

	public GameController(DataBaseConnection conn, MyScene ms, Game game) {
		this.conn= conn;
		
		myScene = ms;
		this.game = game;
//		game =  new Game(conn, true);
		System.out.println("loading...20%");
		dieController = new DieController(conn, this);
		System.out.println("loading...40%");
		patterncardController= new PatterncardController(conn, this);
		System.out.println("loading...60%");
		roundtrackController= new RoundtrackController(game, this);
		System.out.println("loading...80%");
		gameView = new GameView(this);
		System.out.println("loading...100%");
//		Timer timer = new Timer();
//		Task task = new Task;
//		game.playround();
//		patterncardView = new PatterncardView(patterncardController);
//		patterncardSelectionView = new PatterncardSelectionView(this);
		gameRoundPlayer =  new GameRoundPlayer(this, 3);
		gameUpdater = new GameUpdater(this);
		Thread updateGame = new Thread(gameUpdater);
		updateGame.start();
//		myScene.setContentPane(patterncardSelectionView);
//		myScene.setContentPane(gameView.getPatterncardSelectionView());

	}
	
public ArrayList<PatterncardController> getPatternCardsToChoose(){
		ArrayList<PatterncardController> patterncardControllers = new ArrayList<PatterncardController>();
		ArrayList<PatternCard> patternCard = new ArrayList<PatternCard>();
		patternCard = getCurrentPlayer().getPatternCardsToChoose(this.isRandom());
		
		for(int i = 0;i < 4;i++) {
			
			patterncardControllers.add(new PatterncardController(patternCard.get(i)));	
			System.out.println(patternCard.get(i).getName());
			
		}
		return patterncardControllers;
	}

	public DieController getDieController() {
		return dieController;
	}

	public GameDie[] getDicePool() {
		System.out.println("test");
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
		boolean checkplacement = game.checkPlacementAgainstRules(x, y, modelColor, value);
		if(!checkplacement) {
			showWarning("Dobbelsteen zetten", "De geselecteerde dobbelsteen kan niet op deze plek worden geplaatst.");
		}
		return checkplacement;
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
	public void setFinishedTurnTrue() {
		game.setFinishedTurnTrue();
	}

	public void playround() {
		Thread playround = new Thread(gameRoundPlayer);
		playround.start();
	}
	public Game getGame() {
		return game;
	}

	public GameRoundPlayer getGamePoller() {
		return gameRoundPlayer;
	}
}
