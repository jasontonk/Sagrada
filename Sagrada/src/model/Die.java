package model;

public class Die {

	
	private ModelColor modelColor;
	private int dieNumber;
	
	
	/**
     * constructor to initialize all instance variables
     */
	public Die(ModelColor modelColor, int number) {
		this.modelColor = modelColor;
		this.dieNumber = number;
	}
	
	/**
     * constructor to initialize all instance variables
     */
	public Die(Die die) {
		this.modelColor = die.getColor();
		this.dieNumber = die.getNumber();
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
		return dieNumber;
	}

	/**
     * sets the die number
     */
	public void setNumber(int number) {
		this.dieNumber = number;
	}	
	
}
