package controller;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import database.AccountDBA;
import database.DataBaseConnection;
import database.GameDBA;
import database.PlayerDBA;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import model.Account;
import model.Game;
import model.Invitation;
import model.ModelColor;
import model.PatternCard;
import model.Player;
import model.PlayerStatus;
import view.ChooseView;
import view.LobbyView;
import view.LoginView;
import view.MyScene;
import view.RegisterView;

public class AccountController {

	private Account account;
	private LoginView loginView;
	private RegisterView registerView;
	private ChooseView chooseView;
	private DataBaseConnection connection;
	private LobbyView lobbyView;
	private MyScene myScene;
	private AccountDBA accountDBA;
	private ArrayList<Player> invitePlayerList;
	public AccountController(DataBaseConnection c, MyScene myScene) {
		this.connection = c;
		this.myScene = myScene;
		
		setAccount(new Account(connection));
		
		
		chooseView = new ChooseView(this);
		registerView = new RegisterView(this);
		loginView = new LoginView(this);
		invitePlayerList = new ArrayList<Player>();
		
		accountDBA = new AccountDBA(c);
		makeInviteThread();
	}
	

	public void makeInviteThread() {
		Thread invitationChecker = new Thread(new InvitationController(10, this));
		invitationChecker.start();
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public void actionLogin(String username, String password) {
		Account account = accountDBA.GetAccountDB(username);
		
		if (account != null) {
			if(account.accountExists(username)) {
				if(password.equals(account.getPassword(username))) {
					this.account = account;
					viewLobby();
				}
				else {
					showWarning("wachtwoord", "Dit is niet het juiste wachtwoord");
				}
			}
		}
		else {
			showWarning("gebruikersnaam", "Dit is niet de juiste gebruikersnaam");
		}
		
	}  
 
	public void actionRegister(String username, String password) {
		Account account = new Account(connection);
		
		if(account.accountExists(username)) {
			showWarning("gebruikersnaam", "gebruikersnaam is al bezet");		
		} else if(username.length() < 3) {
			showWarning("gebruikersnaam", "Gebruikersnaam moet minimaal 3 tekens zijn");
		} else if(password.length() < 3) {
			showWarning("wachtwoord", "Wachtwoord moet minimaal 3 tekens zijn");
		} else if(password.length() > 25) {
			showWarning("wachtwoord", "Wachtwoord moet maximaal 25 tekens zijn");
		} else if(username.length() > 25) {
			showWarning("gebruikersnaam", "Gebruikersnaam moet maximaal 25 tekens zijn");
		}
		
		Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
        Matcher usernameMatcher = pt.matcher(username);
        Matcher passwordMatcher = pt.matcher(password);
		
        if (usernameMatcher.find()) {
        	showWarning("gebruikersnaam", "Gebruikersnaam moet alleen letters en/of cijfers bevatten.");
        } else if (passwordMatcher.find()) {
        	showWarning("wachtwoord", "Wachtwoord moet alleen letters en/of cijfers bevatten.");
        }
        
		else {
			account.setAccount(username, password);
			viewLogin();
		}
	}
	
	public ArrayList<Account> getAllAccounts() {
		return account.getAllAccounts();
	}
	
	public ArrayList<Player> getAllPlayersOfThisAccount() {
		return account.getPlayers(); 
	}
	
	public void viewLogin() {
		myScene.setContentPane(loginView.makeLoginPane());
	}
	
	public void viewRegister() {
		myScene.setContentPane(registerView.makeRegisterPane());
	}
	
	public void viewChoose() {
		myScene.setContentPane(chooseView.makeChoosePane());
	}
	
	public void viewLobby() {
		if(lobbyView == null) {
			lobbyView = new LobbyView(this);
		}
		myScene.setContentPane(lobbyView.makeAccountPane());
	}
	
	public void showWarning(String header, String text) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Error");
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();
	}
	
	public void showInvite(Player player) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Invite");
			
					String inviter = player.getGame().getChallengerOfGameWithID(player.getGame().getGameID());
					alert.setHeaderText(inviter + " wil je inviten voor een game");
	
			alert.setContentText("Wil je dit accepteren?");
	
			
			
			Optional<ButtonType> result = alert.showAndWait();
			
			
			if (result.get() == ButtonType.OK){
				
				player.setPlayerStatus(PlayerStatus.ACCEPTED);
			} else {
				player.setPlayerStatus(PlayerStatus.REFUSED);
			}
	}

	public void inviteAccounts(ArrayList<Account> inviteList) {
		System.out.println(inviteList);
		
		 if (inviteList.size() == 0) {
			 	showWarning("invites niet verstuurd", "Te weinig accounts geselecteerd");
	            return;
	        }
	     if (inviteList.size() > 3) {
	       	showWarning("invites niet verstuurd", "Te veel accounts geselecteerd");
	        return;
	     	}
	     if(inviteList.contains(this.getAccount())) {
	    	 showWarning("invites niet verstuurd", "Je kan niet jezelf uitnodigen");
		     return; 
	     }
			Game game = new Game(connection, false);	
			game.addGametoDB();
			game.addpublicobjectivecards();
			game.makedie();
			Player player = new Player(connection);
			player.setAccount(account);
			player.setName(account.getUsername());
			player.setGame(game);
			player.setPlayerStatus(PlayerStatus.CHALLENGER);
			player.setPersonalObjectiveCardColor();
			player.addPlayer(player);
			player.createBoard();
			player.setScore(-20);
			game.setCurrentPlayer(player);
			invitePlayerList.add(player);
			
			for (Account account : inviteList) {
				Player p = new Player(connection);
				p.setAccount(account);
				p.setName(account.getUsername());
				p.setGame(game);
				p.setPlayerStatus(PlayerStatus.CHALLENGEE);
				p.setPersonalObjectiveCardColor();
				p.addPlayer(p);
				p.setScore(-20);
				p.createBoard();
				invitePlayerList.add(p);
			}
			game.getDicePoolFromDB();
			game.getOffer(); //check to see if working
			game.finishGameSetup(this);
	}

	public ArrayList<Player> getInvitePlayerList() {
		return invitePlayerList;
	}

	public void render() {
		myScene.setContentPane(lobbyView.makeAccountPane());
		System.out.println("========== LOBBYVIEW GERENDERD");
	}

	public void startGame(ArrayList<Player> gameLobby) {
		for (Player player : gameLobby) {
			
			if(player.getPlayerStatus().equals(PlayerStatus.CHALLENGER)) {
			}
			else if(player.getPlayerStatus().equals(PlayerStatus.CHALLENGEE)) {
				showWarning("game", "Niet elke speler heeft gereageerd op je invite");
			}
			else {
				joinGame(player, player.getGame());
			}
		}	
		
//		for(int i = 0; i < gameLobby.size(); i++) {
//			if(gameLobby.get(1).equals(obj))
//		}
	}
	
	public void joinGame(Player player, Game game) {
		
		if (player.getPatternCard() == null) { 
			player.setPatternCard(new PatternCard(connection));
        }
		else player.setPatternCard(player.getPatternCard());
		
		System.out.println("GAME.getid: " + game.getGameID());
		System.out.println("GAME.getplayers: " + game.getPlayers());

		System.out.println("GAME.getplayers.0: " + game.getPlayers().get(0));
		
		game.setCurrentPlayer(game.getPlayers().get(0));
		
		game.addpublicobjectivecards();
		
		GameController gameController = new GameController(connection, myScene, game, 0);
		myScene.setContentPane(gameController.getGameView());
	}
}
