package GalagaGameElemtentsEnemies;
// Author: Jose Andres Besednjak Izquierdo
import javafx.scene.Group;

public class EnemyMoving extends EnemyBase {
	
	double originalX;
	double amplitude = 50;
	double timeSpent = 0;
	
	public EnemyMoving(double x, double y, double width, double height, int hp) {
		super(x, y, width, height, hp);
		originalX = x;
		loadImage("src\\Pictures\\Enemies\\movingEnemy.png");
	}
	
	public EnemyMoving(double x, double y, double width, double height, int hp, Group group) {
		this(x, y, width, height, hp);
		this.screenItBelongsTo = group;
	}
	
	@Override
    public void update(double elapsedTime){
		// moves left and right given a sine wave pattern
		super.update(elapsedTime);
		timeSpent += elapsedTime;
		this.setPositionX(originalX + amplitude * Math.sin(timeSpent * 2));
	}

}
