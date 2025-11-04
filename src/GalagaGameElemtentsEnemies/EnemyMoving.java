package GalagaGameElemtentsEnemies;

import javafx.scene.Group;

public class EnemyMoving extends EnemyBase {
	
	String imagePath = "/breakout-the-scapers/src/Pictures/Enemies/movingEnemy.png";
	
	public EnemyMoving(double x, double y, double width, double height, int hp) {
		super(x, y, width, height, hp);
		loadImage(); // Load child's specific image
	}
	
	public EnemyMoving(double x, double y, double width, double height, int hp, Group group) {
		super(x, y, width, height, hp, group);
		loadImage(); // Load child's specific image
	}
	
}
