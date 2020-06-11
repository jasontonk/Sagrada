package view;
import java.util.ArrayList;

import controller.AccountController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Account;
import model.Player;
import model.PlayerStatus;

public class LobbyView extends BorderPane {
	
	private ArrayList<Account> accounts;
    private AccountController accountController;
    private Pane output; 
    private ArrayList<Player> players;
    private ArrayList<Account> inviteList;
    private VBox receivedInvitationsView;
    private VBox overview;
    private VBox invitationsView;
    private ArrayList<Player> challengerList;
    private Insets padding;
    private VBox sentinvitationsView;
	
	public LobbyView(AccountController accountController) {
		this.accountController = accountController;
        this.setPrefSize(800, 600); 
        receivedInvitationsView = new VBox();
        padding = new Insets(7);
	}

	public Pane makeAccountPane() {
		accounts = accountController.getAllAccounts();
        inviteList = new ArrayList<Account>();
        players = accountController.getAllPlayersOfThisAccount();   
        challengerList = new ArrayList<>();
        sentinvitationsView = new VBox();
         
		this.getChildren().clear();
		HBox accountView = new HBox();  
	
		accountView.getChildren().addAll(makeGamesView(), receivecInvitationsView(), makeInvitationsViewToSend(), sendInvitationsView());

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
		overview = new VBox();
		
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

            playerlist.getChildren().addAll(gameid,playGame);
            overview.getChildren().add(playerlist);  
		}
		
		overviewscroll.setContent(overview);
		Pane op = new Pane();
		this.output = op;
		output.setMinSize(250, 300);
		output.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		
		VBox left = new VBox();
		
//		left.setPrefWidth();
		
		left.getChildren().addAll(drawTitle("Game overview"),overviewscroll, output);
		
		return left;
	}
	
	private VBox makeInvitationsViewToSend() {
		
		sentinvitationsView.setMinSize(250, 600);
		sentinvitationsView.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, null, null)));
		
		updateSentInvitations();
		
		return sentinvitationsView;
	}
	
	public void updateSentInvitations() {
		
		sentinvitationsView.getChildren().clear();
		
		VBox gamesView = new VBox();
		
		ArrayList<Player> lobbyPlayerList = new ArrayList<Player>();
		
		sentinvitationsView.getChildren().add(drawTitle("Verzonden uitnodigingen"));
		
		for(Player player : accountController.getInvitePlayerList() ) {
			if(!lobbyPlayerList.contains(player)) {
				lobbyPlayerList.add(player);
			}
		}
		if(lobbyPlayerList.size() != 0) {
			for(int i = 0; i < 1; i++ ) {
				
				Label gameID = new Label("GameId: " + lobbyPlayerList.get(i).getGame().getGameID());
				gameID.setMinWidth(100);
				gameID.setPadding(padding);
				gamesView.getChildren().add(gameID);
				
				for(Player g :lobbyPlayerList ) {
					
					Label playerStatus = new Label("Speler: " + g.getName()+" | Status: " + g.getPlayerStatus());
					playerStatus.setMinWidth(100);
					playerStatus.setPadding(padding);
					gamesView.getChildren().add(playerStatus);
				}
			}
		}

		sentinvitationsView.getChildren().add(gamesView);
	}
	
	private VBox receivecInvitationsView() {
		invitationsView = new VBox();
		receivedInvitationsView.setMinSize(250, 600);
		receivedInvitationsView.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, null, null)));
		
		
		
		return receivedInvitationsView;
	}
	
	public void inviteFromChallenger(Player player) {
		
		VBox inviteListView = new VBox();
		invitationsView.getChildren().add(drawTitle("Ontvangen uitnodigingen"));
		if(challengerList.size() != 0) {
			for(Player p : challengerList ) {
				if(!player.getName().equals(p.getName()))
					challengerList.add(player);
			}
		}else {
			challengerList.add(player);
		}

		HBox challengerView = new HBox();
		
		for(int i = 0; i< challengerList.size(); i++) {	
				
				Label username = new Label("Speler: " + challengerList.get(i).getName());
				username.setMinWidth(100);

				 Button accept = buildButton("Accept"); 
				 Button refuse = buildButton("Refuse");
				    
				 for(Player p: accountController.getAccount().getChallengeePlayers()) {
					 if(p.getGame().getChallengerOfGameWithID(p.getGame().getGameID()).equals(challengerList.get(i).getName())) {
						 accept.setOnAction(e -> p.setPlayerStatus(PlayerStatus.ACCEPTED));
					     refuse.setOnAction(e -> p.setPlayerStatus(PlayerStatus.REFUSED));
					     
					     if(p.getPlayerStatus() == PlayerStatus.ACCEPTED || p.getPlayerStatus() == PlayerStatus.REFUSED) {
					    	 challengerList.remove(challengerList.get(i)); 
					     }
					 }
				 }
				 
				 challengerView.getChildren().addAll(username,accept,refuse);
			    
			    invitationsView.getChildren().add(challengerView);

			   
			}
		
		inviteListView.getChildren().add(invitationsView);
		receivedInvitationsView.getChildren().add(inviteListView);
	}
	
	public void updateGameViews() {
		
		players = accountController.getAllPlayersOfThisAccount();
		overview.getChildren().clear();
		
		for(int i = 0; i< players.size(); i++) {
			final int index =i;
				HBox playerlist = new HBox();
				
				Label gameid = new Label("Game-ID: " + players.get(i).getGame().getGameID());
				gameid.setPadding(new Insets(5, 4, 5, 4));
				gameid.setMinWidth(150);
	            
	            Button playGame = buildButton("Speel!");
	            playGame.setOnAction(e -> accountController.joinGame(players.get(index), players.get(index).getGame()));
	            
	            playerlist.getChildren().addAll(gameid,playGame);
	            overview.getChildren().add(playerlist);
		}
	}
	
	private ScrollPane sendInvitationsView() {
		
		ScrollPane overviewscroll = new ScrollPane();
		overviewscroll.setMinSize(400, 600);
		
		VBox right = new VBox();
		right.setMinSize(400, 600);
		right.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		
		right.getChildren().add(drawTitle("Spelers overzicht"));
		
		for(Account a : accounts) {
			HBox playerlist = new HBox();
			
			Label username = new Label("Name: " + a.getUsername());
            username.setMinWidth(160);
            username.setPadding(padding);
            
            Button viewStatsButton = buildButton("Stats");
            viewStatsButton.setPadding(padding);
            viewStatsButton.setOnAction(e -> showStats(a));
            
            CheckBox invite = new CheckBox();
            invite.setPadding(padding);
            invite.setText("Invite");
         
            invite.setOnAction(e -> addPlayerToInviteList(invite, a));

            playerlist.getChildren().addAll(username,viewStatsButton, invite);
            right.getChildren().add(playerlist);
		}
		
		Button inviteButton = buildButton("Invite");
		inviteButton.setPadding(padding);
		inviteButton.setOnAction(e -> accountController.inviteAccounts(inviteList));
		
		right.getChildren().add(inviteButton);
		
		overviewscroll.setContent(right);
		
		return overviewscroll;
	}

	private void showStats(Account account) {
		VBox statusView = new VBox();
		statusView.setPadding(padding);
		output.getChildren().clear();
		
		Text username = new Text("Naam speler: " + account.getUsername());
		Text hoogstescore = new Text("Hoogste score: " + account.getHighestScore());
		Text meestGebruikteKleur = new Text("Meest gebruikte kleur: " + account.getMostUsedColor());
		Text meestGebruikteWaarde = new Text("Meest gebruikte waarde:  " + account.getMostUsedValue());
		Text verschillendeTegenstanders = new Text("Verschillende tegenstanders: " + account.getValueOfDifferentPlayedAccounts());
		
		int[] winsloses = account.getWinsAndLoses();
		
		Text overwinningenEnVerliezen = new Text("Overwinningen en verliezen: " + winsloses[0] + " - " + winsloses[1]);
		
		statusView.getChildren().addAll(drawTitle("Player Status"), username,hoogstescore,meestGebruikteKleur,meestGebruikteWaarde,verschillendeTegenstanders,overwinningenEnVerliezen);
		output.getChildren().add(statusView);
	}
	
	private Button buildButton(String text) {
		Button button = new Button(text);
        return button;
	}
	
	private BorderPane drawTitle(String s) {
		BorderPane titlePane = new BorderPane();
		Text title = new Text();
		title.setText(s);
		titlePane.setPadding(padding);
		titlePane.setCenter(title);
		return titlePane;
	}

	public void clearInvitations() {
		invitationsView.getChildren().clear();
	}
}