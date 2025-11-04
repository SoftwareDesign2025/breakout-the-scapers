package GalagaGameElemtentsEnemies;

import java.awt.Color;
import javafx.scene.Group;

public class EnemyFast extends EnemyBase {
	
	String imagePath = "/breakout-the-scapers/src/Pictures/Enemies/FastEnemy.png";
	
	public EnemyFast(double x, double y, double width, double height, int hp) {
		super(x, y, width, height, hp);
		loadImage(); // Load child's specific image
	}
	
	public EnemyFast(double x, double y, double width, double height, int hp, Group group) {
		super(x, y, width, height, hp, group);
		loadImage(); // Load child's specific image
	}

}
