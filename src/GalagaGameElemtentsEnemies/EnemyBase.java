package GalagaGameElemtentsEnemies;

import GameElemtents.Brick;
import javafx.scene.Group;

public class EnemyBase extends Brick {

	final int fallSpeed = 10;
	private int hp; 
	
	public EnemyBase(double x, double y, double width, double height, int hp) {
		super(x, y, width, height, hp);
		this.hp = hp;
		loadImage();
	}
	
	public EnemyBase(double x, double y, double width, double height, int hp, Group group) {
		super(x, y, width, height, hp);
		this.hp = hp;
		this.screenItBelongsTo = group;
		loadImage();
	}
	
	public int getHP() {
        return hp;
    }

    public void takeDamage(int dmg) {
        hp -= dmg;
        if (hp < 0) hp = 0;
    }

    @Override
    public void update(double elapsedTime) {
        // Example: move enemies downward
        offsetPositionVertival(fallSpeed * elapsedTime);
    }

}
