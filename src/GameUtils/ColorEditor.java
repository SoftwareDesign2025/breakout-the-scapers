package GameUtils;

import javafx.scene.paint.Color;

public class ColorEditor {

	public static Color alterColorHue(double hueShift, Color originalColor) {
        // So hue is a value between 0 and 360 degrees thats why we mod by 360
        Color newColor = Color.hsb((
        		originalColor.getHue() + hueShift) % 360,
        		originalColor.getSaturation(),
        		originalColor.getBrightness());
        return newColor;
    }
	
	public static Color alterColorSaturation(double saturationShift, Color originalColor) {
		Color newColor = Color.hsb(
			originalColor.getHue(),
			Math.max(0, originalColor.getSaturation() + saturationShift),
			originalColor.getBrightness());
		return newColor;
	}

	public static Color alterColorBrightness(double brightnessShift, Color originalColor) {

		Color newColor = Color.hsb(
			originalColor.getHue(),
			originalColor.getSaturation(),
			Math.max(0, originalColor.getBrightness() + brightnessShift));

		return newColor;
	}
}
