package controller;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import database.AccountDBA;
import database.DataBaseConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Account;
import model.Game;
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
	private InvitationController invitationController;
	
	public AccountController(DataBaseConnection c, MyScene myScene) {
		this.connection = c;
		this.myScene = myScene;
		
		setAccount(new Account(connection));
		
		
		chooseView = new ChooseView(this);
		registerView = new RegisterView(this);
		loginView = new LoginView(this);
		invitePlayerList = new ArrayList<Player>();
		
		accountDBA = new AccountDBA(c);
		invitationController = new InvitationController(10, this);
		
	}

	public LobbyView getLobbyView() {
		return lobbyView;
	}

	public void makeInviteThread() {
		Thread invitationChecker = new Thread(invitationController);
		invitationChecker.start();
	}
	public void stopInviteThread() {
		invitationController.setRunning(false);
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
		makeInviteThread();
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
		
		Player challengerPlayer = player.getGame().getPlayerChallengerOfGameWithID(player.getGame().getGameID());
		lobbyView.inviteFromChallenger(challengerPlayer);
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
			game.addToolcards();
			game.makedie();
			Player player = new Player(connection,game);
			player.setAccount(account);
			player.setName(account.getUsername());
			player.setGame(game);
			player.setPlayerStatus(PlayerStatus.CHALLENGER);
			player.setPersonalObjectiveCardColor();
			player.addPlayer(player);
			player.createBoard();
			player.getBoard().AddBoardFieldsToDB();

			player.setScore(-20);
			
			game.setCurrentPlayer(player);
			invitePlayerList.add(player);
			
			for (Account account : inviteList) {
				Player p = new Player(connection,game);
				p.setAccount(account);
				p.setName(account.getUsername());
				p.setGame(game);
				p.setPlayerStatus(PlayerStatus.CHALLENGEE);
				p.setPersonalObjectiveCardColor();
				p.addPlayer(p);
				p.setScore(-20);
				p.createBoard();
				p.getBoard().AddBoardFieldsToDB();

				invitePlayerList.add(p);
			}

			game.getDicePoolFromDB();
			game.finishGameSetup(this);
			game.setCurrentPlayer(player);
			game.setPersonalPlayer(getAccount());
	}

	public ArrayList<Player> getInvitePlayerList() {
		return invitePlayerList;
	}

	public void render() {
		myScene.setContentPane(lobbyView.makeAccountPane());
		System.out.println("========== LOBBYVIEW GERENDERD");
	}
	
	public void joinGame(Player player, Game game) {
		boolean challengeeInGame = false;
		boolean refusedPlayer = false;
		for(Player p : game.getPlayers()) {
			if(p.getPlayerStatus().equals(PlayerStatus.CHALLENGEE)) {
				challengeeInGame = true;
				break;
			}
			else if(p.getPlayerStatus().equals(PlayerStatus.REFUSED)) {
				refusedPlayer = true;
				break;
			}
		}
		
		if(!challengeeInGame) {
			if(!refusedPlayer){

				stopInviteThread();
				
				player.setPatternCard(player.getPatternCard());
				
				
				System.out.println("GAME.getid: " + game.getGameID());
				System.out.println("GAME.getplayers: " + game.getPlayers());
		
				System.out.println("GAME.getplayers.0: " + game.getPlayers().get(0));
				
				game.finishGameSetup(this);
				game.addpublicobjectivecards();
				game.addToolcards();
				game.setPersonalPlayer(getAccount());
				game.getPersonalPlayer().createBoard();
				for(int x = 0; x < 5; x++) {
					for (int y = 0; y < 4; y++) {
						game.getPersonalPlayer().getBoard().getBoardFieldFromDB(x, y);
					}
				}
				
				GameController gameController = new GameController(connection, myScene, game, this);
				myScene.setContentPane(gameController.getGameView());
			}
			else {
				showWarning("game", "Een speler heeft de uitnodiging geweigerd, waardoor de game niet gestart wordt");
			}
			
		}
		else {
			showWarning("game", "Niet elke speler heeft gereageerd op de uitnodiging");
		}	
	}
	
	public void updateLobbyView() {
		lobbyView.clearInvitations();
		lobbyView.challengerListClear();
		lobbyView.updateGameViews();
		lobbyView.updateSentInvitations();
	}
}
