package GameElemtents;
// Author: Jose Andres Besednjak Izquierdo 

import GameUtils.ColorEditor;


public class BrickUnbreakable extends Brick {

	// makes a new unbreakable brick at the given location and color
	// it uses the parent constructor but then alters the color to make it look different
	public BrickUnbreakable(double x, double y, double width, double height, int hp) {
		super(x, y, width, height, hp);
		color = ColorEditor.alterColorSaturation(.2f, color);
		color = ColorEditor.alterColorBrightness(-.15f, color);
		color = ColorEditor.alterColorHue(360/2, color);
		setBrickColor(color);
	}
	
	// So we just say the brick is never destroyed
	// but we still keep track of its HP to make it stop earning points once the HP is 0
	// this way we stop players from farming points off unbreakable bricks
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
        return isBreakDead;
	}

	@Override
	public boolean deadBrick() {
		return false; // unbreakable bricks are never dead
	}


}
