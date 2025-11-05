package GalagaGameElemtentsEnemies;

import javafx.scene.Group;

public class EnemyRegular extends EnemyBase {
	
	public EnemyRegular(double x, double y, double width, double height, int hp) {
		super(x, y, width, height, hp);

		loadImage("src\\Pictures\\Enemies\\NormalEnemy.png");
	}
	
	public EnemyRegular(double x, double y, double width, double height, int hp, Group group) {
		this(x, y, width, height, hp);
		this.screenItBelongsTo = group;
	}

}
