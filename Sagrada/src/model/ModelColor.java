package model;

import java.util.Random;

public enum ModelColor {
	RED,
	GREEN,
	YELLOW,
	PURPLE,
	BLUE;
	
	public static <T extends Enum<ModelColor>> T randomColor(Class<T> clazz){
        Random r = new Random();
		int x = r.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}
