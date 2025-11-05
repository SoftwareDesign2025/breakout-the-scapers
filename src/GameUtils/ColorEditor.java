package GameUtils;
// Author: Jose Andres Besednjak Izquierdo
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public class ColorEditor {

	// method to alter the hue of a color by a given amount
	// returns new color after hue was shifted
	public static Color alterColorHue(double hueShift, Color originalColor) {
        // So hue is a value between 0 and 360 degrees thats why we mod by 360
        Color newColor = Color.hsb((
        		originalColor.getHue() + hueShift) % 360,
        		originalColor.getSaturation(),
        		originalColor.getBrightness());
        return newColor;
    }
	
	// method to alter the saturation of a color by a given amount
	// returns new color after saturation was shifted it makes no assumptions about the input value
	// so negative values will reduce saturation positive values will increase it
	// the saturation scale goes from 0 to 1 so we clamp the result to be in that range
	public static Color alterColorSaturation(double saturationShift, Color originalColor) {
		Color newColor = Color.hsb(
			originalColor.getHue(),
			Math.clamp(originalColor.getSaturation() + saturationShift, 0, 1),
			originalColor.getBrightness());
		return newColor;
	}

	// method to alter the brightness of a color by a given amount
	// returns new color after brightness was shifted it makes no assumptions about the input value
	// so negative values will reduce brightness positive values will increase it
	// the brightness scale goes from 0 to 1 so we clamp the result to be in that range
	public static Color alterColorBrightness(double brightnessShift, Color originalColor) {

		Color newColor = Color.hsb(
			originalColor.getHue(),
			originalColor.getSaturation(),
			Math.clamp(originalColor.getBrightness() + brightnessShift, 0, 1)
		);

		return newColor;
	}
}
