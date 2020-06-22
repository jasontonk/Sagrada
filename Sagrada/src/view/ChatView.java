package view;

import java.util.ArrayList;

import controller.GameController;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.Chat;

public class ChatView extends BorderPane {

	private final int CHATPANE_HEIGHT = 220;
	private final int CHATPANE_WIDTH = 300;
	private final int TEXTFIELD_HEIGHT = 50;
	private final int TEXTFIELD_WIDTH = 300;
	private GameController gameController;
	private VBox messagebox;
	private ArrayList<Chat> chats;
	private ScrollPane chatpane;
	private TextField textfield;
	public TextField getTextfield() {
		return textfield;
	}

	private int countchats;
	    
	public ChatView (GameController gameController) {
        this.gameController = gameController;
        chats = new ArrayList<>();
        setCountchats(0);
        messagebox = new VBox();
        makeChat();
    }

	public void makeChat() {
		this.getChildren().clear();
            textfield = new TextField();
            textfield.setMaxHeight(TEXTFIELD_HEIGHT);
            textfield.setMinHeight(TEXTFIELD_HEIGHT);
            textfield.setMaxWidth(TEXTFIELD_WIDTH);
            textfield.setMinWidth(TEXTFIELD_WIDTH);
            textfield.setOnAction(e -> {
            gameController.actionSendMessage(textfield.getText(), this);
            });
            setBottom(textfield);

        chatpane = new ScrollPane();
        chatpane.setContent(messagebox);
        chatpane.setMaxHeight(CHATPANE_HEIGHT);
        chatpane.setMinHeight(CHATPANE_HEIGHT);
        chatpane.setMaxWidth(CHATPANE_WIDTH);
        chatpane.setMinWidth(CHATPANE_WIDTH);
        chatpane.setVvalue(1.0);
        setTop(chatpane);
    }

	public void addMessage(Chat chat) {
		chats.add(chat);
        String playername = chat.getPlayer().getName();
        String message = chat.getMessage();
        
        int hour = chat.getTime().getHours();
        int minute = chat.getTime().getMinutes();
        int second = chat.getTime().getSeconds();
        
        Label label = new Label("[" + hour + ":" + minute + ":" + second + "] " + playername + ": " + message);
        messagebox.getChildren().add(label);
        setCountchats(getCountchats() + 1);
    }
	
	/**
	 * @return the chats
	 */
	public ArrayList<Chat> getChats() {
		return chats;
	}

	public void deleteAllChats() {
		messagebox.getChildren().clear();
	}

	/**
	 * @return the countchats
	 */
	public int getCountchats() {
		return countchats;
	}

	/**
	 * @param countchats the countchats to set
	 */
	public void setCountchats(int countchats) {
		this.countchats = countchats;
	}
}
