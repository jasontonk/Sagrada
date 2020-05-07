package model;

public class Die {

	private ModelColor modelColor;
	private int number;
	
	
	/**
     * constructor to initialize all instance variables
     */
	public Die(ModelColor modelColor, int number) {
		this.modelColor = modelColor;
		this.number = number;
	}
	
	/**
     * constructor to initialize all instance variables
     */
	public Die(Die die) {
		this.modelColor = die.getColor();
		this.number = die.getNumber();
	}
	
	/**
     * returns the color of the Die
     */
	public ModelColor getColor() {
		return modelColor;
	}
	
	/**
     * sets the color of the Die
     */
	public void setColor(ModelColor modelColor) {
		this.modelColor = modelColor;
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
