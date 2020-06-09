package view;

import java.util.ArrayList;

import javafx.scene.layout.HBox;

public class ToolcardPoolView extends HBox {
	
	private int[] ToolcardIDs;
	private ArrayList<ToolcardView> toolcardView;
	private HBox toolcardPool = new HBox();
	
	public ToolcardPoolView(int[] toolcardIDs) {
		ToolcardIDs = toolcardIDs;
		System.out.println(ToolcardIDs[0]);
		System.out.println(ToolcardIDs[1]);
		System.out.println(ToolcardIDs[2]);
		createCards();
		this.getChildren().add(toolcardPool);
	}
	
	
	private void createCards() {
		for (int i = 0; i < ToolcardIDs.length; i++) {
			toolcardPool.getChildren().add(new ToolcardView(ToolcardIDs[i]));
		}
	}
	
	
	
	
	
}
