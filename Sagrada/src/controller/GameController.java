package controller;

import java.util.ArrayList;
import java.util.Optional;

import database.ChatDBA;
import database.DataBaseConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import model.Chat;
import model.FavorToken;
import model.Game;
import model.GameDie;
import model.ModelColor;
import model.PatternCard;
import model.Player;
import model.Toolcard;
import view.ChatView;
import view.FinishedGameView;
import view.GameView;
import view.MyScene;
import view.ToolcardView;

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
	private ToolcardView toolcardView;
	private ArrayList<GameDie> changedDiceOnRoundTrack;
	private ArrayList<GameDie> diceOnRoundTrack;
	private volatile boolean newCurrentPlayer; 
	private ChatDBA chatDBA;
	private Thread updateGame;
	private Thread updateViews;
	private Thread playround;

	public GameController(DataBaseConnection conn, MyScene ms, Game game, AccountController accountController) {
		this.conn = conn;
		myScene = ms;
		this.game = game;

		this.accountController = accountController;
		chatDBA = new ChatDBA(conn);
		System.out.println("loading...20%");
		dieController = new DieController(conn, this);
		System.out.println("loading...40%");
		patterncardController = new PatterncardController(conn, this);
		System.out.println("loading...60%");
		roundtrackController = new RoundtrackController(game, this);
		System.out.println("loading...80%");
		gameView = new GameView(this);
		System.out.println("loading...100%");

		gameRoundPlayer = new GameRoundPlayer(this, 3);
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
		isPlayingTurn = false;
		shownTurnMessage = false;
	}

	public ArrayList<PatterncardController> getPatternCardsToChoose() {
		ArrayList<PatterncardController> patterncardControllers = new ArrayList<PatterncardController>();
		ArrayList<PatternCard> patternCard = new ArrayList<PatternCard>();
		patternCard = getCurrentPlayer().getPatternCardsToChoose(this.isRandom());

		for (int i = 0; i < 4; i++) {
			patterncardControllers.add(new PatterncardController(patternCard.get(i)));
		}
		return patterncardControllers;
	}

	public DieController getDieController() {
		return dieController;
	}

	public ArrayList<GameDie> getDicePool() {
		return game.getOffer();
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

	public void setToolCardUnused() {
		game.setSelectedToolcard(0);
		toolcardView.removeBorder();
	}

	public void setSelectedDie(GameDie die) {
		game.setSelectedDie(die);
	
		if(getGame().getSelectedToolcard()!=null) {
			if(game.getSelectedDie() != game.getSelectedDieFromDicePool()) {
				int toolcardID = getGame().getSelectedToolcard().getId();
				if(toolcardID == 1 || toolcardID == 10) {
					showWarning("Gereedschapskaart", "De geselecteerde gereedschapskaart kan niet gebruikt worden!");
				}
			}
		}
	}

	public GameView getGameView() {
		return gameView;
	}

	public void setSelectedToolcard(int id,ToolcardView toolcardView) {
		game.setSelectedToolcard(id);
		selectedToolCard(id,toolcardView);
	}
	
	public void selectedToolCard(int id, ToolcardView toolcardView) {
		if(!game.hasUsedToolcard()) {
			
			Toolcard selectedToolcard = game.getSelectedToolcard();
			
			int price = 1; 
			if(selectedToolcard.returnAmountOfTokens(game) > 0) {
				price = 2;
			}
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Let op!");
			alert.setHeaderText("U heeft de gereedschapskaart "+ selectedToolcard.getName()+ " geselecteerd");
			alert.setContentText("Deze gereedschapskaart kost "+price+" betaalstenen, weet je zeker dat je deze gereedschapskaart wilt kopen");
			
			ButtonType buttonTypeOk = new ButtonType("Ja");
			ButtonType buttonTypeCancel = new ButtonType("Nee", ButtonData.CANCEL_CLOSE);
			
			alert.getButtonTypes().setAll(buttonTypeOk,buttonTypeCancel);
			
			Optional<ButtonType> result = alert.showAndWait();
			
			if (result.get() == buttonTypeOk){
				this.toolcardView = toolcardView;
				payForToolcard(selectedToolcard, price);
			}else if(result.get() == buttonTypeCancel) {
				game.setSelectedToolcard(0);
				game.setUsedToolcard(false);
			}
		}
		else {
			gameView.getToolcardPoolView().removeAllBorders();
			game.setSelectedToolcard(0);
			showWarning("Gereedschapskaart", "U heeft deze beurt al een gereedschapskaart gebruikt.");
		}
	}
	
	public void payForToolcard(Toolcard selectedToolcard,int price) {
		
		ArrayList<FavorToken> favorTokens = game.getPersonalPlayer().getFavorTokens();

		if(favorTokens.size() >= price) {
			for(int i = 0; i < price; i++) {
				favorTokens.get(i).setFavortokensForToolCard(selectedToolcard.getId(), game);
				favorTokens.remove(0);
			}
			gameView.updateFavorTokenView(favorTokens.size());
			game.setUsedToolcard(true);
			
		}else {
			showWarning("Je mag toolcard niet kopen!", "Je hebt niet genoeg betaalstenen");
			game.setSelectedToolcard(0);
		}
	}

	public boolean checkPlacementAgainstRules(int x, int y, ModelColor modelColor, int value) {
		if (game.getSelectedToolcard() == null) {
			boolean checkplacement = game.checkPlacementAgainstRules(x, y, modelColor, value);
			if (!checkplacement) {
				showWarning("Dobbelsteen zetten", "De geselecteerde dobbelsteen kan niet op deze plek worden geplaatst.");
			}
			return checkplacement;
		} else {
			boolean testje = game.checkSelectedToolcard(x, y);
			if (!testje) {
				showWarning("Gereedschapskaart", "De geselecteerde Gereedschapskaart kan niet gebruikt worden!");
			}
			return testje;
		}
	}

	public Player getCurrentPlayer() {
		Player currentplayer = game.getCurrentPlayer();
		if (!shownTurnMessage && currentplayer.getId() == game.getPersonalPlayer().getId()) {
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
		if (game.getSelectedDieColor() == null) {
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
	
	public void removeAllBordersFromToolcard() {
		gameView.getToolcardPoolView().removeAllBorders();
	}

	public void showWarning(String header, String text) {
	
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Let op!");
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();	
	}
	
	public void lensCutter(GameDie rountrackDie) {
		
		System.out.print("color roundtrack "+ rountrackDie.getColorString()+ " value "+rountrackDie.getEyes());
		
		GameDie dieOnDiePool = game.getSelectedDieFromDicePool();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Let op!");
		alert.setHeaderText("De waarde van de geselecteerde dobbelsteen is " + dieOnDiePool.getEyes() + " en de kleur is "+ dieOnDiePool.getColorString());
		alert.setContentText("Weet je zeker dat je deze dobbelsteen wilt wisselen met een dobbelsteen uit het rondespoor?");
		
		ButtonType buttonTypeOk = new ButtonType("Ja");
		ButtonType buttonTypeCancel = new ButtonType("Nee", ButtonData.CANCEL_CLOSE);
		
		alert.getButtonTypes().setAll(buttonTypeOk,buttonTypeCancel);
		
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == buttonTypeOk){
			
			game.removeDieFromRoundTrack(rountrackDie);
			game.setDieOnRoundTrack(dieOnDiePool,rountrackDie);
		
			gameUpdater.updateRountrack();
			gameViewUpdater.updateRoundtrackView();
		}
		
		rountrackDie.setOnRoundTrack(0);
		getGame().updateOffer(dieOnDiePool, rountrackDie);
		getGame().setSelectedDieFromDicePool(rountrackDie);
		getGame().setSelectedDie(rountrackDie);	
		
		System.out.print("color roundtrack "+ rountrackDie.getColorString()+ " value "+rountrackDie.getEyes());
		dieController.getDieViewForToolcard5().toolcardUpdate();
	}
	
	public void fluxRemover(GameDie gamedie) {
		GameDie unusedDie;
//		game.setGameDieUnused(gamedie);
		gamedie.setRoundID(0, game);
		unusedDie = game.getUnusedDiceForGame();
		
		while(unusedDie.getNumber() == gamedie.getNumber()) {
			unusedDie = game.getUnusedDiceForGame();
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Let op!");
		alert.setHeaderText("De kleur van de nieuwe dobbelsteen is " + unusedDie.getColorString());
		alert.setContentText("Welke waarde wilt u deze dobbelsteen geven?");
		
		ButtonType buttonType1 = new ButtonType("1");
		ButtonType buttonType2 = new ButtonType("2");
		ButtonType buttonType3 = new ButtonType("3");
		ButtonType buttonType4 = new ButtonType("4");
		ButtonType buttonType5 = new ButtonType("5");
		ButtonType buttonType6 = new ButtonType("6");
		ButtonType buttonTypeCancel = new ButtonType("Annuleren", ButtonData.CANCEL_CLOSE);
		
		alert.getButtonTypes().setAll(buttonType1,buttonType2,buttonType3,buttonType4,buttonType5,buttonType6,buttonTypeCancel);
		
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == buttonType1){
			unusedDie.changeEyes(1, game);
		}else if (result.get() == buttonType2) {
			unusedDie.changeEyes(2, game);
		}else if (result.get() == buttonType3) {
			unusedDie.changeEyes(3, game);
		}else if (result.get() == buttonType4) {
			unusedDie.changeEyes(4, game);
		}else if (result.get() == buttonType5) {
			unusedDie.changeEyes(5, game);
		}else if (result.get() == buttonType6) {
			unusedDie.changeEyes(6, game);
		}
		
		game.updateOffer(gamedie, unusedDie);
		game.setSelectedDieFromDicePool(unusedDie);
		game.setSelectedDie(unusedDie);
	}
	
	public void fluxBrush(GameDie gamedie) {
		int dieValue = gamedie.getEyes();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Let op!");
		alert.setHeaderText("De waarde van de geselecteerde dobbelsteen is " + dieValue);
		alert.setContentText("Weet je zeker dat je de dobbelsteen opnieuw wilt werpen?");
		
		ButtonType buttonTypeOk = new ButtonType("Ja");
		ButtonType buttonTypeCancel = new ButtonType("Nee", ButtonData.CANCEL_CLOSE);
		
		alert.getButtonTypes().setAll(buttonTypeOk,buttonTypeCancel);
		
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == buttonTypeOk){
			int randomDieValue = (int) (Math.random() * 6) + 1; 
			gamedie.changeEyes(randomDieValue, game);
		}
		
		getGame().setSelectedDieFromDicePool(gamedie);
	}
	
	public void flipDice(GameDie gamedie) {
		int dieValue = gamedie.getEyes();
	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Let op!");
		alert.setHeaderText("De waarde van de geselecteerde dobbelsteen is " + dieValue);
		alert.setContentText("Weet je zeker dat je de dobbelsteen wilt omdraaien?");
		
		ButtonType buttonTypeOk = new ButtonType("Ja");
		ButtonType buttonTypeCancel = new ButtonType("Nee", ButtonData.CANCEL_CLOSE);
		
		alert.getButtonTypes().setAll(buttonTypeOk,buttonTypeCancel);
		
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == buttonTypeOk){
			switch (dieValue) {
			case 1:
				gamedie.changeEyes(6, getGame());
				break;
			case 2:
				gamedie.changeEyes(5, getGame());
				break;
			case 3:
				gamedie.changeEyes(4, getGame());
				break;
			case 4:
				gamedie.changeEyes(3, getGame());
				break;
			case 5:
				gamedie.changeEyes(2, getGame());
				break;
			case 6:
				gamedie.changeEyes(1, getGame());
				break;
			} 
		}
		getGame().setSelectedDieFromDicePool(gamedie);
	}
	
	public void showConfirmation(GameDie gamedie) {
		
		int dieValue = gamedie.getEyes();
	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Let op!");
		alert.setHeaderText("De waarde van de geselecteerde dobbelsteen is " + dieValue);
		alert.setContentText("Wilt u de waarde ervan met 1 verhogen of verlagen?");
		
		ButtonType buttonTypeOne = new ButtonType("-1");
		ButtonType buttonTypeTwo = new ButtonType("+1");
		ButtonType buttonTypeCancel = new ButtonType("Annuleren", ButtonData.CANCEL_CLOSE);

		if(dieValue == 1){
			alert.getButtonTypes().setAll(buttonTypeTwo,buttonTypeCancel);
		}
		else if(dieValue == 6) {
			alert.getButtonTypes().setAll(buttonTypeOne,buttonTypeCancel);
		}
		else {
			alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo,buttonTypeCancel);
		}
		
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == buttonTypeOne){
			
		    gamedie.changeEyes((dieValue-1), getGame());
		    
		} else if (result.get() == buttonTypeTwo) {
			 gamedie.changeEyes((dieValue+1), getGame());
		}
		getGame().setSelectedDieFromDicePool(gamedie);
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

		if (game.getCurrentPlayer().getId() == game.getPersonalPlayer().getId()) {
			if (!isPlayingTurn) {
				game.setCurrentPlayer(game.getPersonalPlayer());
				gameViewUpdater.setPaused(true);
				gameUpdater.setPaused(true);
				gameRoundPlayer.setIsPaused(false);
				isPlayingTurn = true;
			} else
				showWarning("Beurt", "Je bent jouw beurt al begonnen");
		} else
			showWarning("Beurt", "Het is niet jouw beurt");
	}

	public void stopround() {
		if (game.getCurrentPlayer().getId() == game.getPersonalPlayer().getId()) {
			isPlayingTurn = false;
			getGamePoller().setFinishedTurn(true);
			gameUpdater.setPaused(false);
			gameViewUpdater.setPaused(false);
			gameRoundPlayer.setIsPaused(true);
		} else
			showWarning("Beurt", "Het is niet jouw beurt");
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
				if (gameDieFromDB.getColor() == diceOnRoundTrack.get(i).getColor()
						&& gameDieFromDB.getNumber() == diceOnRoundTrack.get(i).getNumber()) {
					ArrayList<GameDie> temporaryList = (ArrayList<GameDie>) changedDiceOnRoundTrack.clone();// TODO miss geen clone
					for (int j = 0; j < changedDiceOnRoundTrack.size(); j++) {
						if (gameDieFromDB.getColor() == changedDiceOnRoundTrack.get(j).getColor()
								&& gameDieFromDB.getNumber() == changedDiceOnRoundTrack.get(j).getNumber()) {
							temporaryList.remove(j);
						}
					}
					changedDiceOnRoundTrack = temporaryList;
				}
			}
		}
		diceOnRoundTrack = (ArrayList<GameDie>) diceOnRoundTrackFromDB.clone();// TODO miss geen clone
	}

	public ArrayList<GameDie> getChangedDiceOnRoundTrack() {
		return changedDiceOnRoundTrack;
	}

	public void clearChangedDiceOnRoundTrack() {
		changedDiceOnRoundTrack.clear();
	}

	public ArrayList<GameDie> getDiceOnRoundTrack() {
		return diceOnRoundTrack;
	}

	public void setFinishedGameView() {
		myScene.setContentPane(new FinishedGameView(this));
		gameViewUpdater.setRunning(false);
		gameUpdater.setRunning(false);
		gameRoundPlayer.setRunning(false);
	}

	public void setLobbyView() {
		accountController.viewLobby();
	}

	/**
	 * @return the newCurrentPlayer
	 */
	public boolean isNewCurrentPlayer() {
		return newCurrentPlayer;
	}

	/**
	 * @param newCurrentPlayer the newCurrentPlayer to set
	 */
	public void setNewCurrentPlayer(boolean newCurrentPlayer) {
		this.newCurrentPlayer = newCurrentPlayer;
	}
	
	public Player getLocalCurrentPlayer() {
		return game.getLocalCurrentPlayer();
	}

	public void actionSendMessage(String text, ChatView chatView) {
		Chat c = new Chat(game.
				getPersonalPlayer().
				getId(), text, conn);
		chatDBA.getTime(c);
		
		chatDBA.addChatDB(game.
				getPersonalPlayer().
				getId(), text, c);
		
		ArrayList<Chat> chats = chatDBA.getChatlinesOfGame(this.getGame().getGameID());
		chatView.deleteAllChats();
		for (Chat chat : chats) {
			chatDBA.getTime(chat);
			chatView.addMessage(chat);
		}
		chatView.makeChat();
	}
}
