package model;

public class BoardField {
	
	private Die die;
	
	public BoardField() {
		// TODO Auto-generated constructor stub
		this.die = setDie(die);
	}
	
	public Die getDie() {
		return die;
	}
	
	public void setDie(Die die) {
		this.die = die;
	}

}
