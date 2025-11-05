package GalagaGameElemtents;

import GameElemtents.Ball;
import javafx.scene.Group;

public class GalagaBall extends Ball {

	int projectileDamage = 1;
	int ptojectileLife = 1;
	
	public GalagaBall(double x, double y) {
		super(x, y);
		loadImage();
	}
	
	public GalagaBall(double x, double y, Group group) {
		super(x, y);
		this.screenItBelongsTo = group;
		loadImage();
	}
}
