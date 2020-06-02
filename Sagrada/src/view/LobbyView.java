package view;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.AccountController;
import controller.GameController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Account;
import model.Invitation;
import model.Player;
import model.PlayerStatus;

public class LobbyView extends BorderPane {
	
	private ArrayList<Account> accounts;
    private AccountController accountController;
    private Pane output; 
    private ArrayList<Player> players;
    private ArrayList<Account> inviteList;
    private ArrayList<Player> gameLobby;
	
	public LobbyView(AccountController accountController) {
		this.accountController = accountController;
        this.setPrefSize(800, 600);
	}

	public Pane makeAccountPane() {
		accounts = accountController.getAllAccounts();
        inviteList = new ArrayList<Account>();
        players = accountController.getAllPlayersOfThisAccount();  
        
        
		this.getChildren().clear();
		HBox accountView = new HBox();  
		
		
		ScrollPane overviewscroll = new ScrollPane();
		overviewscroll.setMinSize(300, 400);
		VBox overview = new VBox();
		overview.setBackground(new Background(new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, null, null)));
		overview.setMinSize(300, 400);
		
		System.out.println(players);
		
		for(Player p : players) {
			HBox playerlist = new HBox();
			
			Label gameid = new Label("Game-ID: " + p.getGame().getGameID());
			gameid.setPadding(new Insets(5, 4, 5, 4));
			gameid.setMinWidth(150);
            
            Button playGame = buildButton("Speel!");
            playGame.setOnAction(e -> accountController.joinGame(p, p.getGame()));
            
//            playGame.setOnAction(e -> startGame());
            
            
            playerlist.getChildren().addAll(gameid,playGame);
            overview.getChildren().add(playerlist);
		}
		overviewscroll.setContent(overview);
		Pane op = new Pane();
		this.output = op;
		output.setMinSize(300, 200);
		output.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		
		VBox left = new VBox(overviewscroll, output);
		
		VBox center = new VBox();
		center.setMinSize(250, 600);
		
		VBox top = new VBox();
		
		top.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, null, null)));
		top.setMinSize(250, 600);


		Button refresh = new Button("Vernieuw");
		refresh.setOnAction(e -> accountController.render());
		top.getChildren().add(refresh);
		
		gameLobby = new ArrayList<Player>();
		for (Player player : accountController.getInvitePlayerList()) {
			if(!gameLobby.contains(player)) {
				gameLobby.add(player);
				System.out.println(gameLobby);
			}
		}
		
		for (Player player : gameLobby) {
			HBox playerlist = new HBox();
			Label username = new Label("Speler: " + player.getName() + " | Playerstatus: " + player.getPlayerStatus());
            username.setMinWidth(100);
            
            System.out.println("Speler:" + player.getName());
            playerlist.getChildren().add(username);
            top.getChildren().add(playerlist);
		}
		
		
		center.getChildren().addAll(top);
		
		VBox right = new VBox();
		right.setMinSize(250, 600);
		right.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		
		for(Account a : accounts) {
			HBox playerlist = new HBox();
			
			Label username = new Label("Speler: " + a.getUsername());
            username.setMinWidth(100);
            
            Button viewStatsButton = buildButton("Stats");
            viewStatsButton.setOnAction(e -> showStats(a));
            
            CheckBox invite = new CheckBox();
            invite.setText("Invite");
         
            invite.setOnAction(e -> addPlayerToInviteList(invite, a));

            playerlist.getChildren().addAll(username,viewStatsButton, invite);
            right.getChildren().add(playerlist);
		}
		
		Button inviteButton = buildButton("Invite");
		inviteButton.setOnAction(e -> accountController.inviteAccounts(inviteList));
		right.getChildren().add(inviteButton);
		
		accountView.getChildren().addAll(left, center, right);
		return accountView;
	}

	private void addPlayerToInviteList(CheckBox invite, Account a) {
		
		if(a.getUsername().equals(accountController.getAccount().getUsername())) {
			accountController.showWarning("invite", "Je kunt niet jezelf uitnodingen");
			return;
		}
		else if(invite.isSelected()) {
			inviteList.add(a);
		}
		else {
			inviteList.remove(a);
		}
	}
	
	private void startGame() {
		accountController.startGame(gameLobby);
	}

	

	private void showStats(Account account) {
		TextArea stats = new TextArea();
		stats.appendText("Naam speler: " + account.getUsername() + "\n");
		stats.appendText("Hoogste score: " + account.getHighestScore() + "\n");
		stats.appendText("Meest gebruikte kleur: " + account.getMostUsedColor() + "\n");
		stats.appendText("Meest gebruikte waarde:  " + account.getMostUsedValue() + "\n");
		stats.appendText("Verschillende tegenstanders: " + account.getValueOfDifferentPlayedAccounts() + "\n");
		
		int[] winsloses = account.getWinsAndLoses();
		
		stats.appendText("Overwinningen en verliezen: " + winsloses[0] + " - " + winsloses[1] + "\n");
		output.getChildren().add(stats);
	}
	
	private Button buildButton(String text) {
		Button button = new Button(text);
        return button;
	}
	
//	private void setGames() {
//		
//	}
}
