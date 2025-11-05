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
		this.updateSpeed(800);
	}
	
	public GalagaBall(double x, double y, Group group) {
		this(x, y);
		this.screenItBelongsTo = group;
	}
	
	public int getProjectileDamage() {
		return projectileDamage;
	}
	
	public void markForRemoval() {
		shouldRemove = true;
	}
	
	public boolean shouldRemove() {
		return shouldRemove;
	}
}
