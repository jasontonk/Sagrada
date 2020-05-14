package view;
import java.util.ArrayList;

import controller.AccountController;
import model.Account;
import model.Game;
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

public class LobbyView extends BorderPane {
	
	private ArrayList<Account> accounts;
    private AccountController accountController;
    private Pane output; 
    private ArrayList<Game> games;
//  private MyScene myScene;
	
	public LobbyView(AccountController accountController) {
		this.accountController = accountController;
        accounts = new ArrayList<Account>();
        this.setPrefSize(800, 600);
	}

	public Pane makeAccountPane() {
		this.getChildren().clear();
		HBox accountView = new HBox();  //test
		
		accounts = accountController.getAllAccounts();
		
		Pane overview = new Pane();
		overview.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
		overview.setMinSize(600, 400);
		
		
		Pane op = new Pane();
		this.output = op;
		output.setMinSize(200, 200);
		output.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		
		VBox left = new VBox(overview, output);
		
		VBox right = new VBox();
		right.setMinSize(200, 600);
		right.setBackground(new Background(new BackgroundFill(Color.GREY, null, null)));
		
		for(Account a : accounts) {
			HBox playerlist = new HBox();
			
			Label username = new Label("Speler: " + a.getUsername());
			username.setPadding(new Insets(5, 4, 5, 4));
            username.setMinWidth(150);
            
            Button viewStatsButton = buildButton();
            viewStatsButton.setOnAction(e -> showStats(a));
            
            playerlist.getChildren().addAll(username,viewStatsButton);
            right.getChildren().add(playerlist);
		}
		
		accountView.getChildren().addAll(left, right);
		return accountView;
	}
	
	private void showStats(Account account) {
		TextArea stats = new TextArea();
		stats.appendText("Hoogste score: " + account.getHighestScore() + "\n");
		stats.appendText("Meest gebruikte kleur: " + account.getMostUsedColor() + "\n");
		stats.appendText("Meest gebruikte waarde:  " + account.getMostUsedValue() + "\n");
		stats.appendText("Verschillende tegenstanders: " + account.getValueOfDifferentPlayedAccounts() + "\n");
		
		int[] winsloses = account.getWinsAndLoses();
		
		stats.appendText("Overwinningen en verliezen: " + winsloses[0] + " - " + winsloses[1] + "\n");
		output.getChildren().add(stats);
	}
	
	private Button buildButton() {
		Button button = new Button("stats");
        return button;
	}
	
//	private void setGames() {
//		
//	}
}
