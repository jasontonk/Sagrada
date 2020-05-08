package view;

import javafx.scene.paint.Color;
import model.ModelColor;

public class JavafxColor {

	Color color;
	public Color getJavafxColor(ModelColor modelcolor) {
		if(modelcolor == null) {
			color = Color.WHITE;
		}
		else {
			switch(modelcolor) {
				case RED:
					color = Color.RED;
					break;
				case GREEN:
					color = Color.GREEN;
					break;
				case YELLOW:
					color = Color.YELLOW;
					break;
				case PURPLE:
					color = Color.PURPLE;
					break;
				case BLUE:
					color = Color.BLUE;
					break;
				default:
					color = Color.WHITE;
			}
		}
		return color;
	}
}
