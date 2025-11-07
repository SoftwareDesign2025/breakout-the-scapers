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

	/**
     * Alters the hue of a Node by a given amount in DEGREES (0–360 range).
	 * assumes no value is greater than 180 and no value is less than -180
     */
    public static void alterViewHue(Node view, double hueShiftDegrees) {
        ColorAdjust adjust = getOrCreateColorAdjust(view);
		// so JavaFX hue adjustment goes from -1 to 1 where 0 is no change
		// so we convert degrees to that scale by dividing by 180 and clamping
    	adjust.setHue(adjust.getHue() + (hueShiftDegrees / 180.0));
    	view.setEffect(adjust);
    }

    
    // Alters the saturation of a Node by a given amount (-1.0 to 1.0).
    
    public static void alterViewSaturation(Node view, double saturationShift) {
        ColorAdjust adjust = getOrCreateColorAdjust(view);
        adjust.setSaturation(clamp(adjust.getSaturation() + saturationShift, -1, 1));
        view.setEffect(adjust);
    }

    // Alters the brightness of a Node by a given amount (-1.0 to 1.0).
    public static void alterViewBrightness(Node view, double brightnessShift) {
        ColorAdjust adjust = getOrCreateColorAdjust(view);
        adjust.setBrightness(clamp(adjust.getBrightness() + brightnessShift, -1, 1));
        view.setEffect(adjust);
    }

    // Gets the existing ColorAdjust effect or creates one if missing.
	// This ensures we don't overwrite existing effects, but add on to them.
    private static ColorAdjust getOrCreateColorAdjust(Node view) {
        if (view.getEffect() instanceof ColorAdjust existing) {
            return existing;
        }
        ColorAdjust newAdjust = new ColorAdjust();
        view.setEffect(newAdjust);
        return newAdjust;
    }

	// Clamps a value between a minimum and maximum.
	// Helper method to ensure values stay within bounds of the JavaFX
    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

}
