package GalagaGameElemtentsEnemies;
// Author: Jose Andres Besednjak Izquierdo 
import javafx.scene.Group;

public class EnemyTank extends EnemyBase {
	// wahetever HP it has triple it.
	public EnemyTank(double x, double y, double width, double height, int hp) {
		super(x, y, width, height, hp*3);
		loadImage("src\\Pictures\\Enemies\\diamond.png"); 
	}
	
	public EnemyTank(double x, double y, double width, double height, int hp, Group group) {
		this(x, y, width, height, hp);
		this.screenItBelongsTo = group;
	}
}
