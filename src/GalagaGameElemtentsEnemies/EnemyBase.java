package GalagaGameElemtentsEnemies;

import GameElemtents.Brick;
import javafx.scene.Group;
// Author: Jose Andres Besednjak Izquierdo
public class EnemyBase extends Brick {

	protected float fallSpeed = .25f;
	
	public EnemyBase(double x, double y, double width, double height, int hp) {
		super(x, y, width, height, hp);
		loadImage();
	}
	
	public EnemyBase(double x, double y, double width, double height, int hp, Group group) {
		this(x, y, width, height, hp);
		this.screenItBelongsTo = group;
	}

	protected void fallDown(){
		this.offsetPositionVertival(fallSpeed);
	}

	@Override
    public void update(double elapsedTime){
		fallDown();
	}

}
