package model;

public class Die {
	
	private Color color;
	private int eyes;
	
	
	/**
     * constructor to initialize all instance variables
     */
	public Die(Color color, int eyes) {
		this.color = color;
		this.eyes = eyes;
	}
	
	/**
     * constructor to initialize all instance variables
     */
	public Die(Die die) {
		this.color = die.getColor();
		this.eyes = die.getEyes();
	}
	
	/**
     * returns the color of the Die
     */
	public Color getColor() {
		return color;
	}
	
	/**
     * sets the color of the Die
     */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
     * returns the amount of eyes
     */
	public int getEyes() {
		return eyes;
	}

	/**
     * sets the amount of eyes
     */
	public void setEyes(int eyes) {
		this.eyes = eyes;
	}	
	
}
