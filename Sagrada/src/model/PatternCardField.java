package model;

public class PatternCardField {

	private String color;
	private int value;
	private char positionY;
	private int positionX;

	// Return the color of this PatternCardField.

	public String getColor() {

		return color;
	}

	// Set the color of this PatternCardField.
	public void setColor(String color) {

		this.color = color;
	}

	// Return the value of this PatternCardField.
	public int getValue() {

		return value;
	}

	// Set the value of this PatternCardField.
	public void setValue(int value) {

		this.value = value;
	}

	// Return the PositionY of this PatternCardField.
	public char getPositionY() {

		return positionY;
	}

	// Set the PositionY of this PatternCardField.

	public void setPositionY(char positionY) {

		this.positionY = positionY;
	}

	// Return the PositionX of this PatternCardField.

	public int getPositionX() {

		return positionX;
	}

	// Set the PositionX of this PatternCardField.

	public void setPositionX(int positionX) {

		this.positionX = positionX;
	}

	// Partial constructor, generate the color, value and position of
	// PatternCardField.

	public PatternCardField(String color, int value, char positionY, int positionX) {
		this.color = color;
		this.value = value;
		this.positionY = positionY;
		this.positionX = positionX;
	}

}
