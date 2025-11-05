package GalagaGameElemtentsEnemies;

// Author: Jose Andres Besednjak Izquierdo
import javafx.scene.Group;

public class EnemyFast extends EnemyBase {
	
	public EnemyFast(double x, double y, double width, double height, int hp) {
		super(x, y, width, height, hp);
		loadImage("src\\Pictures\\Enemies\\FastEnemy.png"); 
	}
	
	public EnemyFast(double x, double y, double width, double height, int hp, Group group) {
		this(x, y, width, height, hp);
		this.screenItBelongsTo = group;
	}

	@Override
    public void update(double elapsedTime){
		// falls quadruple as fast as a normal enemy
		super.update(elapsedTime);
		fallDown();
		fallDown();
		fallDown();
	}

}
