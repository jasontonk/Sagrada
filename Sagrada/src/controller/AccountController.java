package controller;

import java.util.ArrayList;
import java.util.Optional;

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
		
		lobbyView = new LobbyView(this);
		chooseView = new ChooseView(this);
		registerView = new RegisterView(this);
		loginView = new LoginView(this);
		invitePlayerList = new ArrayList<Player>();
		
		accountDBA = new AccountDBA(c);
		makeThread();
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public void actionLogin(String username, String password) {
		Account account = accountDBA.GetAccountDB(username);
		
		if(account.accountExists(username)) {
			if(password.equals(account.getPassword(username))) {
				this.account = account;
				viewLobby();
			}
			else {
				showWarning("wachtwoord", "Dit is niet het juiste wachtwoord");
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
		}else {
			account.setAccount(username, password);
			viewLogin();
		}
	}
	
	public ArrayList<Account> getAllAccounts() {
		return account.getAllAccounts();
	}
	
	public ArrayList<Player> getAllPlayersOfThisAccount() {
		return account.getPlayers();
//		return null;
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
		myScene.setContentPane(lobbyView.makeAccountPane());
	}
	
	public void makeThread() {
		Thread invitationChecker = new Thread(new InvitationController(account, 3, this));
		invitationChecker.start();
	}
	
	public void showWarning(String header, String text) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Error");
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();
	}

	public void makeGame() {
		GameController gameContoller = new GameController(connection, myScene);
	}

	public void inviteAccounts(ArrayList<Account> inviteList) {
		System.out.println(inviteList);
		Invitation invite = new Invitation();
		Game game = new Game(connection, false);
		Player player = new Player(connection);
		player.setAccount(account);
		player.setName(account.getUsername());
		player.setGame(game);
		player.setPlayerStatus(PlayerStatus.CHALLENGER);
		player.setColor(ModelColor.BLUE);
		player.addPlayer(player);
		invitePlayerList.add(player);
		
		for (Account account : inviteList) {
			Player p = new Player(connection);
			p.setAccount(account);
			p.setName(account.getUsername());
			p.setGame(game);
			p.setPlayerStatus(PlayerStatus.CHALLENGEE);
			p.setColor(ModelColor.BLUE);
			p.addPlayer(p);
			invitePlayerList.add(p);
			PlayerController pc = new PlayerController(this);
		}
		
	}

	public ArrayList<Player> getInvitePlayerList() {
		return invitePlayerList;
	}

	public void render() {
		myScene.setContentPane(lobbyView.makeAccountPane());
		System.out.println("hij werkt");
	}
}
