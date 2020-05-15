package model;

public class PatternCardField {

	private ModelColor modelColor;
	private int value;
	private int positionY;
	private int positionX;

	public PatternCardField() {
		modelColor = ModelColor.WHITE;
		value = 0;
	}
	// Return the color of this PatternCardField.

	public ModelColor getColor() {
		return modelColor;
	}

	// Set the color of this PatternCardField.
	public void setColor(ModelColor modelColor) {

		this.modelColor = modelColor;
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
	public int getPositionY() {

		return positionY;
	}

	// Set the PositionY of this PatternCardField.

	public void setPositionY(int positionY) {

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

	public PatternCardField(ModelColor modelColor, int value, int positionY, int positionX) {
		this.modelColor = modelColor;
		this.value = value;
		this.positionY = positionY;
		this.positionX = positionX;
	}

}
