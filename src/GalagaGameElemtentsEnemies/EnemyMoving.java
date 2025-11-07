package GalagaGameElemtentsEnemies;

// Author: Jose Andres Besednjak Izquierdo 

import javafx.scene.Group;

public class EnemyMoving extends EnemyBase {
	
	String imagePath = "/breakout-the-scapers/src/Pictures/Enemies/movingEnemy.png";
	double originalX;
	double amplitude = 50;
	double timeSpent = 0;
	
	public EnemyMoving(double x, double y, double width, double height, int hp) {
		super(x, y, width, height, hp);
		originalX = x;
		loadImage(); // Load child's specific image
	}
	
	public EnemyMoving(double x, double y, double width, double height, int hp, Group group) {
		super(x, y, width, height, hp, group);
		loadImage(); // Load child's specific image
	}
	
	@Override
    public void update(double elapsedTime){
		// moves left and right given a sine wave pattern
		super.update(elapsedTime);
		timeSpent += elapsedTime;
		this.setPositionX(originalX + amplitude * Math.sin(timeSpent * 2));
	}

}
