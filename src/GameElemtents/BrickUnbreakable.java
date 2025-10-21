package GameElemtents;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class BrickUnbreakable extends Brick {

	public BrickUnbreakable(double x, double y, double width, double height, Paint color, int hp) {
		super(x, y, width, height, color, hp);
		alterColorSaturation(.2f);
		alterColorBrightness(-.1f);
		super.alterColorHue(360/2);
		
	}
	
	// So we just do not substract the HP from the block
	@Override
	public boolean onHit() {
		super.onHit();
		// brick is never destroyed no matter how little life it has
		isBreakDead = false;
        return isBreakDead;
	}

	private void alterColorSaturation(double saturationShift) {
		Paint currentPaint = ((javafx.scene.shape.Rectangle) view).getFill();

		Color currentColor = (javafx.scene.paint.Color) currentPaint;
		Color newColor = Color.hsb(currentColor.getHue(),
									Math.max(0, currentColor.getSaturation() + saturationShift),
									currentColor.getBrightness());
		((javafx.scene.shape.Rectangle) view).setFill(newColor);
	}

	private void alterColorBrightness(double brightnessShift) {
		Paint currentPaint = ((javafx.scene.shape.Rectangle) view).getFill();

		Color currentColor = (javafx.scene.paint.Color) currentPaint;
		Color newColor = Color.hsb(currentColor.getHue(),
									currentColor.getSaturation(),
									Math.max(0, currentColor.getBrightness() + brightnessShift));
		((javafx.scene.shape.Rectangle) view).setFill(newColor);
	}

}
