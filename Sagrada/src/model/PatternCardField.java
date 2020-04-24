package model;

public class PatternCardField {

	private String color;
	private int value;
	private char positionY;
	private int positionX;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public char getPositionY() {
		return positionY;
	}

	public void setPositionY(char positionY) {
		this.positionY = positionY;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public PatternCardField(String color, int value, char positionY, int positionX) {
		this.color = color;
		this.value = value;
		this.positionY = positionY;
		this.positionX = positionX;
	}

}
