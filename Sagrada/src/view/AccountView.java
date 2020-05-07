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

public class AccountView extends BorderPane {
	
	private ArrayList<Account> accounts;
    private AccountController accountController;
    private Pane output; 
    private ArrayList<Game> games;
	
	public AccountView() {
		this.accountController = accountController;
        accounts = new ArrayList<Account>();
        this.setPrefSize(800, 600);
        this.makeAccountPane();
	}
	
	private void makeAccountPane() {
		// TODO Auto-generated method stub
		HBox accountView = new HBox();
		
//		Account azmat = new Account("azmat", 1, "rood");
//		Account rutger = new Account("rutger", 5, "blauw");
//		Account milan = new Account("milan", 4, "groen");
		
//		accounts.add(0, azmat);
//		accounts.add(1, rutger);
//		accounts.add(2, milan);
		
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
		this.getChildren().add(accountView);
	}
	
	private void showStats(Account a) {
		TextArea stats = new TextArea();
//		stats.appendText(a.getStats(a) + "\n");
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
