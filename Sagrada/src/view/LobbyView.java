//package view;
//import java.util.ArrayList;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//
//import controller.AccountController;
//import controller.GameController;
//import javafx.geometry.Insets;
//import javafx.scene.control.Button;
//import javafx.scene.control.CheckBox;
//import javafx.scene.control.Label;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.control.TextArea;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundFill;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Pane;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Text;
//import jdk.internal.org.objectweb.asm.tree.IntInsnNode;
//import model.Account;
//import model.Invitation;
//import model.Player;
//import model.PlayerStatus;
//
//public class LobbyView extends BorderPane {
//	
//	private ArrayList<Account> accounts;
//    private AccountController accountController;
//    private Pane output; 
//    private ArrayList<Player> players;
//    private ArrayList<Account> inviteList;
//    private ArrayList<Player> receivedInvitations;
//    private ArrayList<Player> test;
//    private VBox center;
//    private ArrayList<Player> gameLobby;
//	
//	public LobbyView(AccountController accountController) {
//		this.accountController = accountController;
//        this.setPrefSize(800, 600); 
//        center = new VBox();
//	}
//
//	public Pane makeAccountPane() {
//		accounts = accountController.getAllAccounts();
//        inviteList = new ArrayList<Account>();
//        players = accountController.getAllPlayersOfThisAccount();  
//        receivedInvitations = new ArrayList<Player>();
//        test = new ArrayList<Player>(); 
//         
//		this.getChildren().clear();
//		HBox accountView = new HBox();  
//	
//		accountView.getChildren().addAll(makeGamesView(), receivecInvitationsView(), makeInvitationsViewToSend(), sendInvitationsView());
//		
//		
//		return accountView;
//	}
//
//	private void addPlayerToInviteList(CheckBox invite, Account a) {
//		
//		if(a.getUsername().equals(accountController.getAccount().getUsername())) {
//			accountController.showWarning("invite", "Je kunt niet jezelf uitnodingen");
//			return;
//		}
//		else if(invite.isSelected()) {
//			inviteList.add(a);
//		}
//		else {
//			inviteList.remove(a);
//		}
//	}
//	
//
//	private VBox makeGamesView() {
//		ScrollPane overviewscroll = new ScrollPane();
//		overviewscroll.setMinSize(300, 400);
//		VBox overview = new VBox();
//		overview.setBackground(new Background(new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, null, null)));
//		overview.setMinSize(300, 400);
//		
//		System.out.println(players);
//		
//		for(Player p : players) {
//			HBox playerlist = new HBox();
//			
//			Label gameid = new Label("Game-ID: " + p.getGame().getGameID());
//			gameid.setPadding(new Insets(5, 4, 5, 4));
//			gameid.setMinWidth(150);
//            
//            Button playGame = buildButton("Speel!");
//            playGame.setOnAction(e -> accountController.joinGame(p, p.getGame()));
//            
////            playGame.setOnAction(e -> startGame());
//            
//            
//            playerlist.getChildren().addAll(gameid,playGame);
//            overview.getChildren().add(playerlist);
//		}
//		overviewscroll.setContent(overview);
//		Pane op = new Pane();
//		this.output = op;
//		output.setMinSize(300, 200);
//		output.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
//		
//		VBox left = new VBox();
//		
//		left.getChildren().addAll(drawTitle("Speel overzicht"),overviewscroll, output);
//		
//		return left;
//	}
//	
//	private VBox makeInvitationsViewToSend() {
//		VBox invitations = new VBox();
//		invitations.setMinSize(250, 600);
//		invitations.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, null, null)));
//		
//		invitations.getChildren().add(drawTitle("Invitations to send overzicht"));
//		
//		return invitations;
//	}
//	
//	private VBox receivecInvitationsView() {
//		
//		
//		
//		
//		
//		
//		
//		
////		
////		for(Account a : accounts) {
////			HBox invitations = new HBox();
////			
////			Label username = new Label("Speler: " + a.getUsername());
////            username.setMinWidth(100);
////            
////            Button viewStatsButton = buildButton("Stats");
////            viewStatsButton.setOnAction(e -> showStats(a));
////            
////            invitations.getChildren().addAll(username,viewStatsButton);
////            
////            center.getChildren().add(invitations);
////            
////           
////            }
////
////		Button refresh = new Button("Vernieuw");
////		refresh.setOnAction(e -> accountController.render());
////		center.getChildren().add(refresh);
////		
////		gameLobby = new ArrayList<Player>();
////		for (Player player : accountController.getReceivedInvitations()) {
////			if(!gameLobby.contains(player)) {
////				gameLobby.add(player);
////				System.out.println(gameLobby);
////			}
////		}
////		
////		for (Player player : gameLobby) {
////			HBox playerlist = new HBox();
////			Label username = new Label("Speler: " + player.getName() + " | Playerstatus: " + player.getPlayerStatus());
////            username.setMinWidth(100);
////            
////            System.out.println("Speler:" + player.getName());
////            playerlist.getChildren().add(username);
////            center.getChildren().add(playerlist);
////		}
//		
//		return center;
//	}
//	
//	public void update() {
//		
//		center.getChildren().clear();
//		
//		center.setMinSize(250, 600);
//		center.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, null, null)));
//		
//		center.getChildren().add(drawTitle("Received invitations overzicht"));
//		
//		ArrayList<Player> tijdelijk = new ArrayList<Player>();
//		
//		tijdelijk = accountController.getReceivedInvitations();
//		
//		
//		if(receivedInvitations.size() == 0) {
//			if(tijdelijk.size() != 0) {
//				for(int i = 0; i < tijdelijk.size();i++) {
//					receivedInvitations.add(tijdelijk.get(i));
//					test.add(tijdelijk.get(i));
//				}
//			}
//		}else {
//			for(Player p :receivedInvitations) {
//				for(Player t:tijdelijk) {
//					
//					if(p.getGame().getGameID() != t.getGame().getGameID()) {
//						receivedInvitations.add(t);
//						test.add(t);
//					}
//				}
//			}
//		}
//		
//		
//		
//		System.out.println("receivedInvitations "+test.size());
//		
//		for(Player p :test ) {
//			
//			HBox playerlist = new HBox();
//					
//			Label username = new Label("Speler: " + p.getName());
//		    username.setMinWidth(100);
//		    ArrayList<Player> players = p.getGame().getPlayers();
//		     
//		    Button accept = buildButton("Accept"); 
//		      Button refuse = buildButton("Refuse");
//		     for (int i = 0; i < players.size(); i++) {
//		    	 final int index = i;
//		    	 if(players.get(i).getName().equals(accountController.getAccount().getUsername())){
//		    		 accept.setOnAction(e -> players.get(index).setPlayerStatus(PlayerStatus.ACCEPTED));
//				     refuse.setOnAction(e -> players.get(index).setPlayerStatus(PlayerStatus.REFUSED));
////				     test.remove(p);
//				     break;
//		    	 }
//		     }
//		     
//		      
//		      
//
//
//		      
//		      playerlist.getChildren().addAll(username,accept,refuse);
//		      
//		      center.getChildren().add(playerlist);
//		}
//	}
//	
//	private VBox sendInvitationsView() {
//		VBox right = new VBox();
//		right.setMinSize(250, 600);
//		right.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
//		
//		right.getChildren().add(drawTitle("Spelers overzicht"));
//		
//		for(Account a : accounts) {
//			HBox playerlist = new HBox();
//			
//			Label username = new Label("Speler: " + a.getUsername());
//            username.setMinWidth(100);
//            
//            Button viewStatsButton = buildButton("Stats");
//            viewStatsButton.setOnAction(e -> showStats(a));
//            
//            CheckBox invite = new CheckBox();
//            invite.setText("Invite");
//         
//            invite.setOnAction(e -> addPlayerToInviteList(invite, a));
//
//            playerlist.getChildren().addAll(username,viewStatsButton, invite);
//            right.getChildren().add(playerlist);
//		}
//		
//		Button inviteButton = buildButton("Invite");
//		inviteButton.setOnAction(e -> accountController.inviteAccounts(inviteList));
//		right.getChildren().add(inviteButton);
//		
//		return right;
//	}
//
//	private void showStats(Account account) {
//		TextArea stats = new TextArea();
//		stats.appendText("Naam speler: " + account.getUsername() + "\n");
//		stats.appendText("Hoogste score: " + account.getHighestScore() + "\n");
//		stats.appendText("Meest gebruikte kleur: " + account.getMostUsedColor() + "\n");
//		stats.appendText("Meest gebruikte waarde:  " + account.getMostUsedValue() + "\n");
//		stats.appendText("Verschillende tegenstanders: " + account.getValueOfDifferentPlayedAccounts() + "\n");
//		
//		int[] winsloses = account.getWinsAndLoses();
//		
//		stats.appendText("Overwinningen en verliezen: " + winsloses[0] + " - " + winsloses[1] + "\n");
//		output.getChildren().add(stats);
//	}
//	
//	private Button buildButton(String text) {
//		Button button = new Button(text);
//        return button;
//	}
//	
//	private BorderPane drawTitle(String s) {
//		BorderPane titlePane = new BorderPane();
//		Text title = new Text();
//		title.setText(s);
//		titlePane.setPadding(new Insets(5));
//		titlePane.setCenter(title);
//		return titlePane;
//	}
//	
////	private void setGames() {
////		
////	}
//}





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
import javafx.scene.text.Text;
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
	
		accountView.getChildren().addAll(makeGamesView(), makeInvitationsViewToReceived(), makeInvitationsViewToSend(), sendInvitationsView());
		
		
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
	

	private VBox makeGamesView() {
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
		
		VBox left = new VBox();
		
		left.getChildren().addAll(drawTitle("Speel overzicht"),overviewscroll, output);
		
		return left;
	}
	
	private VBox makeInvitationsViewToSend() {
		VBox invitations = new VBox();
		invitations.setMinSize(250, 600);
		invitations.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, null, null)));
		
		invitations.getChildren().add(drawTitle("Received invitations overzicht"));
		
		return invitations;
	}
	
	private VBox makeInvitationsViewToReceived() {
		VBox center = new VBox();
		
		center.setMinSize(250, 600);
		center.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, null, null)));
		
		center.getChildren().add(drawTitle("Invitations to send overzicht"));
		
		
		for(Account a : accounts) {
			HBox invitations = new HBox();
			
			Label username = new Label("Speler: " + a.getUsername());
            username.setMinWidth(100);
            
            Button viewStatsButton = buildButton("Stats");
            viewStatsButton.setOnAction(e -> showStats(a));
            
            invitations.getChildren().addAll(username,viewStatsButton);
            
            center.getChildren().add(invitations);
            
           
            }

		Button refresh = new Button("Vernieuw");
		refresh.setOnAction(e -> accountController.render());
		center.getChildren().add(refresh);
		
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
            center.getChildren().add(playerlist);
		}
		
		return center;
	}
	
	private VBox sendInvitationsView() {
		VBox right = new VBox();
		right.setMinSize(250, 600);
		right.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		
		right.getChildren().add(drawTitle("Spelers overzicht"));
		
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
		
		return right;
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
	
	private BorderPane drawTitle(String s) {
		BorderPane titlePane = new BorderPane();
		Text title = new Text();
		title.setText(s);
		titlePane.setPadding(new Insets(5));
		titlePane.setCenter(title);
		return titlePane;
	}
	
//	private void setGames() {
//		
//	}
}