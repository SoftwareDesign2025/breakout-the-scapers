package GameElemtents;


import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
//represents a single brick that can be hit by the ball
public class Brick extends GameObject{
	private int hp; //number of hits the brick can take
    private int points; //points gained when destroyed

    //makes a new brick at the given location and color
    public Brick(double x, double y, double width, double height, Paint color, int hp) {
        this.hp = hp;
        this.points = 100;
        //makes a rectangle to visually represent the brick
        view = new Rectangle(x, y, width, height);
        ((Rectangle) view).setFill(color);
    }

    @Override
    public void update(double elapsedTime) {}

    public boolean onHit() {
        hp--;
        return hp <= 0;
    }

    public int getPoints() {
        return points;
    }

}
