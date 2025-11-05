package GalagaGameElemtents;

import GameElemtents.Ball;
import javafx.scene.Group;

public class GalagaBall extends Ball {

	int projectileDamage = 1;
	int ptojecTileLife = 1;
	boolean shouldRemove = false;
	
	public GalagaBall(double x, double y) {
		super(x, y);
		loadImage("src\\Pictures\\BallPixelArt.png");
		velocity = new Point2D(0, -400);
	}
	
	public GalagaBall(double x, double y, Group group) {
		this(x, y);
		this.screenItBelongsTo = group;
		loadImage("src\\Pictures\\BallPixelArt.png");
		velocity = new Point2D(0, -400);
	}
	
	@Override
    public void update(double elapsedTime) {
        // move only; no bouncing
        offsetPositionHorizontal(velocity.getX() * elapsedTime);
        offsetPositionVertival(velocity.getY() * elapsedTime);
    }
}
