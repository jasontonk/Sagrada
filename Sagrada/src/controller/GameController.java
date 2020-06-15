package controller;

import java.util.ArrayList;
import java.util.Timer;

import database.DataBaseConnection;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Game;
import model.GameDie;
import model.ModelColor;
import model.PatternCard;
import model.Player;
import view.FinishedGameView;
import view.GameView;
import view.LobbyView;
import view.MyScene;
import view.PatterncardSelectionView;
import view.PatterncardView;

public class GameController {

	private Game game;
	private GameRoundPlayer gameRoundPlayer;
	private GameUpdater gameUpdater;
	private GameViewUpdater gameViewUpdater;
	private GameView gameView;
	private DieController dieController;
	private PatterncardController patterncardController;
	private RoundtrackController roundtrackController;
	private AccountController accountController;
	private DataBaseConnection conn;
	private MyScene myScene;
	private volatile boolean isPlayingTurn;
	private volatile boolean shownTurnMessage;


	private PatterncardView patterncardView;
	private PatterncardSelectionView patterncardSelectionView;
	
	private ArrayList<GameDie> changedDiceOnRoundTrack;
	private ArrayList<GameDie> diceOnRoundTrack;
	
	private Thread updateGame;
	private Thread updateViews;
	private Thread playround;
	

//	public GameController(DataBaseConnection conn, MyScene ms, Game game) {
//		this.conn= conn;
//		
//		myScene = ms;
//		this.game = game;
////		game =  new Game(conn, true);
//		System.out.println("loading...20%");
//		dieController = new DieController(conn, this);
//		System.out.println("loading...40%");
//		patterncardController= new PatterncardController(conn, this);
//		System.out.println("loading...60%");
//		roundtrackController= new RoundtrackController(game, this);
//		System.out.println("loading...80%");
//		gameView = new GameView(this);
//		System.out.println("loading...100%");
//		Timer timer = new Timer();
////		Task task = new Task;
//		game.playround();
//		patterncardView = new PatterncardView(patterncardController);
//		patterncardSelectionView = new PatterncardSelectionView(this);
//		gameRoundPlayer =  new GameRoundPlayer(this, 3);
//		gameUpdater = new GameUpdater(this);
//		gameViewUpdater = new GameViewUpdater(this, gameUpdater);
//		
//		changedDiceOnRoundTrack = new ArrayList<GameDie>();
//		diceOnRoundTrack = new ArrayList<GameDie>();
//		
//		
//		updateGame = new Thread(gameUpdater);
//		updateGame.setDaemon(true);
//		updateGame.start();
//		updateViews = new Thread(gameViewUpdater);
//		updateViews.setDaemon(true);
//		updateViews.start();
////		myScene.setContentPane(patterncardSelectionView);
////		myScene.setContentPane(gameView.getPatterncardSelectionView());
//		
//
//	}
	
	public GameController(DataBaseConnection conn, MyScene ms, Game game, AccountController accountController) {
		this.conn= conn;
		myScene = ms;
		this.game = game;
		
		this.accountController = accountController;
		
		System.out.println("loading...20%");
		dieController = new DieController(conn, this);
		System.out.println("loading...40%");
		patterncardController= new PatterncardController(conn, this);
		System.out.println("loading...60%");
		roundtrackController= new RoundtrackController(game, this);
		System.out.println("loading...80%");
		gameView = new GameView(this);
		System.out.println("loading...100%");
		
//		patterncardView = new PatterncardView(patterncardController);
//		patterncardSelectionView = new PatterncardSelectionView(this);
		gameRoundPlayer =  new GameRoundPlayer(this, 3);
		gameUpdater = new GameUpdater(this);
		gameViewUpdater = new GameViewUpdater(this, gameUpdater);
		
		changedDiceOnRoundTrack = new ArrayList<GameDie>();
		diceOnRoundTrack = new ArrayList<GameDie>();
		
		game.playround();
		
		updateGame = new Thread(gameUpdater);
		updateGame.setDaemon(true);
		updateGame.start();
		updateViews = new Thread(gameViewUpdater);
		updateViews.setDaemon(true);
		updateViews.start();
		
		playround = new Thread(gameRoundPlayer);
		playround.setDaemon(true);
		playround.start();
		
		gameRoundPlayer.setIsPaused(true);
		myScene.setContentPane(new GameView(this));
		isPlayingTurn = false;
		shownTurnMessage = false;

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

	public ArrayList<GameDie> getDicePool() {
		return game.getOffer();
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
		
	}
	public void setSelectedDie(GameDie die) {
		game.setSelectedDie(die);
	}

	public GameView getGameView() {
		return gameView;
	}

	
	public void setSelectedToolcard(int id) {
		game.setSelectedToolcard(id);
	}
	
	
	public boolean checkPlacementAgainstRules(int x, int y, ModelColor modelColor, int value) {
		if(game.getSelectedToolcard() == null) {
			boolean checkplacement = game.checkPlacementAgainstRules(x, y, modelColor, value);
			if(!checkplacement) {
				showWarning("Dobbelsteen zetten", "De geselecteerde dobbelsteen kan niet op deze plek worden geplaatst.");
			}
		return checkplacement;
		}
		else {
			return game.checkSelectedToolcard(x, y);
		}
	}

	public Player getCurrentPlayer() {
		Player currentplayer = game.getCurrentPlayer();
		if(!shownTurnMessage && currentplayer.getId() == game.getPersonalPlayer().getId()) {
			shownTurnMessage = true;
			playround();
			showWarning("Beurt", "jij bent aan de beurt");
		}
		return currentplayer;
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
		System.out.println("test 5.1");
		alert.setTitle("Let op!");
		System.out.println("test 5.2");
		alert.setHeaderText(header);
		System.out.println("test 5.3");
		alert.setContentText(text);
		System.out.println("test 5.4");
		alert.showAndWait();
		System.out.println("test 5.5");
	}

	public int getCurrentRound() {
		return game.getRound().get();
	}

	public boolean isRandom() {
		return game.isRandom();
	}
	public void setFinishedTurnTrue() {
		game.setFinishedTurnTrue();
	}

	public void playround() {
		
		if(game.getCurrentPlayer().getId() == game.getPersonalPlayer().getId()) {
			if(!isPlayingTurn) {
	//			gameViewUpdater.updateScoreBoard();
				game.setCurrentPlayer(game.getPersonalPlayer());
				gameViewUpdater.setPaused(true);
				gameUpdater.setPaused(true);
				gameRoundPlayer.setIsPaused(false);
				isPlayingTurn = true;
			}
			else showWarning("Beurt", "Je bent jouw beurt al begonnen");
		}
		else showWarning("Beurt", "Het is niet jouw beurt");
	}
	public void stopround() {
		if(game.getCurrentPlayer().getId() == game.getPersonalPlayer().getId()) {
			isPlayingTurn = false;
			getGamePoller().setFinishedTurn(true);
			gameUpdater.setPaused(false);
			gameViewUpdater.setPaused(false);
//			gameViewUpdater.updateScoreBoard();
			gameRoundPlayer.setIsPaused(true);

		}
		else showWarning("Beurt", "Het is niet jouw beurt");
	}
	public void setShowTurnMessage(Boolean value) {
		shownTurnMessage = value;
	}
	
	public Game getGame() {
		return game; 
	}

	public GameRoundPlayer getGamePoller() {
		return gameRoundPlayer;
	}
	
	public void updateRoundTrack(ArrayList<GameDie> diceOnRoundTrackFromDB) {
		for (GameDie gameDieFromDB : diceOnRoundTrackFromDB) {
			changedDiceOnRoundTrack.add(gameDieFromDB);
			for (int i = 0; i < diceOnRoundTrack.size(); i++) {
				if(gameDieFromDB.getColor() == diceOnRoundTrack.get(i).getColor() && gameDieFromDB.getNumber() == diceOnRoundTrack.get(i).getNumber()) {
					ArrayList<GameDie> temporaryList = (ArrayList<GameDie>) changedDiceOnRoundTrack.clone();// TODO miss geen clone
					for (int j = 0; j < changedDiceOnRoundTrack.size(); j++) {
						if(gameDieFromDB.getColor() == changedDiceOnRoundTrack.get(j).getColor() && gameDieFromDB.getNumber() == changedDiceOnRoundTrack.get(j).getNumber()) {
							temporaryList.remove(j);
						}
					}
					changedDiceOnRoundTrack = temporaryList;
				}
			}
		}
		diceOnRoundTrack = (ArrayList<GameDie>) diceOnRoundTrackFromDB.clone();
	}
	
	public ArrayList<GameDie> getChangedDiceOnRoundTrack() {
		return changedDiceOnRoundTrack;
	}
	
	public void clearChangedDiceOnRoundTrack() {
		changedDiceOnRoundTrack.clear();
	}

	public void viewPatternCardSelection() {
		patterncardSelectionView = new PatterncardSelectionView(this);
		
	}

	public void setFinishedGameView() {
		myScene.setContentPane(new FinishedGameView(this));
		gameViewUpdater.setRunning(false); 
		gameUpdater.setRunning(false);
		gameRoundPlayer.setRunning(false);
	}

	public void setLobbyView() {
		accountController.viewLobby();
//		myScene.setContentPane(accountController.getLobbyView());
	}

}
