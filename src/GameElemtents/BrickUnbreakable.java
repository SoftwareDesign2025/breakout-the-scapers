package GameElemtents;

import GameUtils.ColorEditor;


public class BrickUnbreakable extends Brick {

	public BrickUnbreakable(double x, double y, double width, double height, int hp) {
		super(x, y, width, height, hp);
		color = ColorEditor.alterColorSaturation(.2f, color);
		color = ColorEditor.alterColorBrightness(-.1f, color);
		color = ColorEditor.alterColorHue(360/2, color);
		setBrickColor(color);
	}
	
	// So we just do not substract the HP from the block
	@Override
	public boolean onHit() {
		super.onHit();
		// brick is never destroyed no matter how little life it has
		if (isBreakDead) {
			// make the color have no saturation. the scale goes from 0 to 1 so -1 ensures it is 0
			color = ColorEditor.alterColorSaturation(-1, color);
			setBrickColor(color);
			// after "brick is dead" earns no points
			this.points = 0;
		}
		this.isBreakDead = false;
        return isBreakDead;
	}


}
