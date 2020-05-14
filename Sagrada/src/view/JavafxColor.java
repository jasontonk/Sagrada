package view;

import javafx.scene.paint.Color;
import model.ModelColor;

public class JavafxColor {

	Color color;
	ModelColor modelColor;
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
	public ModelColor getModelColor(Color color) {
		if(color == null) {
			modelColor = null;
		}
		else {
			if(color == Color.RED) {
				modelColor = ModelColor.RED;
			}
			else if(color == Color.GREEN) {
				modelColor = ModelColor.GREEN;
			}
			else if(color == Color.YELLOW) {
				modelColor = ModelColor.YELLOW;		
			}
			else if(color == Color.PURPLE) {
				modelColor = ModelColor.PURPLE;
			}
			else if(color == Color.BLUE) {
				modelColor = ModelColor.BLUE;
			}
			else{
				modelColor = null;
			}
		}
		return modelColor;
	}
}
