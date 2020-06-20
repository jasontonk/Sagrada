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
		toolcardView = new ArrayList<ToolcardView>();
		this.gamecontroller = gamecontroller;
		ToolcardIDs = gamecontroller.getGame().getToolcardIDs();
		createCards();
		this.getChildren().add(toolcardPool);
	}
	
	public void removeAllBorders() {
		for (int i = 0; i < ToolcardIDs.length; i++) {
			toolcardView.get(i).removeBorder();
		}
	}
	
	private void createCards() {
		for (int i = 0; i < ToolcardIDs.length; i++) {
			toolcardView.add(new ToolcardView(ToolcardIDs[i], gamecontroller));
			toolcardPool.getChildren().add(toolcardView.get(i));
		}
	}
}
