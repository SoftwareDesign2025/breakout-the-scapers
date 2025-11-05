package GalagaGameElemtentsEnemies;
// Author: Jose Andres Besednjak Izquierdo
import javafx.scene.Group;

public class EnemyRegular extends EnemyBase {
	
	String imagePath = "/breakout-the-scapers/src/Pictures/Enemies/NormalEnemy.png";
	
	public EnemyRegular(double x, double y, double width, double height, int hp) {
		super(x, y, width, height, hp);
		loadImage(); // Load child's specific image
	}
	
	public EnemyRegular(double x, double y, double width, double height, int hp, Group group) {
		super(x, y, width, height, hp, group);
		loadImage(); // Load child's specific image
	}

}
