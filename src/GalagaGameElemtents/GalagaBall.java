package GalagaGameElemtents;

import GameElemtents.Ball;
import javafx.geometry.Point2D;
import javafx.scene.Group;

public class GalagaBall extends Ball {

	int projectileDamage = 1;
	int ptojectileLife = 1;
	
	public GalagaBall(double x, double y) {
		super(x, y);
		loadImage();
		velocity = new Point2D(0, -400);
	}
	
	public GalagaBall(double x, double y, Group group) {
		super(x, y);
		this.screenItBelongsTo = group;
		loadImage();
		velocity = new Point2D(0, -400);
	}
	
	@Override
    public void update(double elapsedTime) {
        // move only; no bouncing
        offsetPositionHorizontal(velocity.getX() * elapsedTime);
        offsetPositionVertival(velocity.getY() * elapsedTime);
    }
}

		//loadImage("src\\Pictures\\BallPixelArt.png");
