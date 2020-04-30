package model;

public class Die {

	private Color color;
	private int number;
	
	
	/**
     * constructor to initialize all instance variables
     */
	public Die(Color color, int number) {
		this.color = color;
		this.number = number;
	}
	
	/**
     * constructor to initialize all instance variables
     */
	public Die(Die die) {
		this.color = die.getColor();
		this.number = die.getNumber();
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
     * returns the die number
     */
	public int getNumber() {
		return number;
	}

	/**
     * sets the die number
     */
	public void setNumber(int number) {
		this.number = number;
	}	
	
}
