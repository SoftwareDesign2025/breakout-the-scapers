package GalagaGameElemtentsEnemies;

import GameElemtents.Brick;
import javafx.scene.Group;

public class EnemyBase extends Brick {

	final int fallSpeed = 10;
	
	public EnemyBase(double x, double y, double width, double height, int hp) {
		super(x, y, width, height, hp);
		loadImage();
	}
	
	public EnemyBase(double x, double y, double width, double height, int hp, Group group) {
		super(x, y, width, height, hp);
		this.screenItBelongsTo = group;
		loadImage();
	}

}
