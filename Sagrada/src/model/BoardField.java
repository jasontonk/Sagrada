package model;

public class BoardField {
	
	private Die die;
	private int xPos;
	private int yPos;
	
	public BoardField() {
		// TODO Auto-generated constructor stub
	}
	
	public Die getDie() {
		return die;
	}
	
	public void setDie(Die die) {
		this.die = die;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	
	public boolean hasDie() {
		return die != null;
	}

}
