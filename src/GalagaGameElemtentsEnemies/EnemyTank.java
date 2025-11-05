package GalagaGameElemtentsEnemies;

import javafx.scene.Group;

public class EnemyTank extends EnemyBase {
	
	public EnemyTank(double x, double y, double width, double height, int hp) {
		super(x, y, width, height, hp);
		loadImage("src\\Pictures\\Enemies\\diamond.png"); 
	}
	
	public EnemyTank(double x, double y, double width, double height, int hp, Group group) {
		super(x, y, width, height, hp, group);
		loadImage(); // Load child's specific image
	}
}
