package view;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.AccountController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Account;
import model.Player;

public class LobbyView extends BorderPane {
	
	private ArrayList<Account> accounts;
    private AccountController accountController;
    private Pane output; 
    private ArrayList<Player> players;
//  private MyScene myScene;
	
	public LobbyView(AccountController accountController) {
		this.accountController = accountController;
        accounts = new ArrayList<Account>();
//        players = accountController.getAllPlayersOfThisAccount();
        this.setPrefSize(800, 600);
	}

	public Pane makeAccountPane() {
		this.getChildren().clear();
		HBox accountView = new HBox();  //test
		
		accounts = accountController.getAllAccounts();
		
		Pane overview = new Pane();
		overview.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
		overview.setMinSize(550, 400);
		
//		for(Player p : players) {
//			HBox playerlist = new HBox();
//			
//			Label gameid = new Label("Game-ID: " + p.getGame().getGameID());
//			gameid.setPadding(new Insets(5, 4, 5, 4));
//			gameid.setMinWidth(150);
//            
//            Button playGame = buildButton("Speel!");
//            playGame.setOnAction(e -> System.out.println("speel"));
//            
//            playerlist.getChildren().addAll(gameid,playGame);
//            overview.getChildren().add(playerlist);
//		}
		
		Pane op = new Pane();
		this.output = op;
		output.setMinSize(200, 200);
		output.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		
		VBox left = new VBox(overview, output);
		
		VBox right = new VBox();
		right.setMinSize(250, 600);
		right.setBackground(new Background(new BackgroundFill(Color.GREY, null, null)));
		
		for(Account a : accounts) {
			HBox playerlist = new HBox();
			
			Label username = new Label("Speler: " + a.getUsername());
			username.setPadding(new Insets(5, 4, 5, 4));
            username.setMinWidth(150);
            
            Button viewStatsButton = buildButton("Stats");
            viewStatsButton.setOnAction(e -> showStats(a));
            
            Button invite = buildButton("Invite");
            invite.setOnAction(e -> invite());//TODO invite
            
            playerlist.getChildren().addAll(username,viewStatsButton, invite);
            right.getChildren().add(playerlist);
		}
		
		accountView.getChildren().addAll(left, right);
		return accountView;
	}
	
	private void invite() {
		accountController.viewInvitation(); 
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
