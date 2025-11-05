package GalagaGameElemtentsEnemies;

import GameElemtents.Brick;
import javafx.scene.Group;

public class EnemyBase extends Brick {

	protected float fallSpeed = .25f;
	
	public EnemyBase(double x, double y, double width, double height, int hp) {
		super(x, y, width, height, hp);
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

	public void takeDamage(int dmg) {
        hp -= dmg;
        if (hp < 0) hp = 0;
    }
	
	public double getX(){ 
		return view.getLayoutX();
	}
	public double getY(){
		return view.getLayoutY();
	}
	
	public double getWidth(){
		return view.getBoundsInLocal().getWidth();
	}
	public double getHeight(){
		return view.getBoundsInLocal().getHeight();
	}
}
