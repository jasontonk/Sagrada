package view;

import java.util.ArrayList;

import controller.GameController;
import javafx.scene.layout.HBox;

public class ToolcardPoolView extends HBox {
	
	private int[] ToolcardIDs;
	private ArrayList<ToolcardView> toolcardView;
	private HBox toolcardPool = new HBox();
	private GameController gamecontroller;
	
	public ToolcardPoolView(GameController gamecontroller) {
		this.gamecontroller = gamecontroller;
		ToolcardIDs = gamecontroller.getGame().getToolcardIDs();
		createCards();
		this.getChildren().add(toolcardPool);
	}
	
	
	private void createCards() {
		for (int i = 0; i < ToolcardIDs.length; i++) {
			toolcardPool.getChildren().add(new ToolcardView(ToolcardIDs[i], gamecontroller));
		}
	}
	
	
	
	
	
}
