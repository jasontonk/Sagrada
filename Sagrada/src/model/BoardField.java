package model;

public class BoardField {
	
	private GameDie die;
	private int xPos;
	private int yPos;
	
	public BoardField(int x, int y) {
		xPos = x;
		yPos = y;
	}
	
	public GameDie getDie() {
		return die;
	}
	
	public void setDie(GameDie die) {
		
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
	
    public void remove() {
        die = null;
    }

}
