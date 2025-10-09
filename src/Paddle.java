
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

//represents the paddle controlled by the player
//paddle can only move left and right
public class Paddle extends GameObject{
	private double speed = 10;

    public Paddle(double x, double y, double width, double height, Paint color) {
        //makes a visual paddle as a rectangle
    	view = new Rectangle(x, y, width, height);
        ((Rectangle) view).setFill(color);
    }

    @Override
    public void update(double elapsedTime) {}

    public void moveLeft() {
        view.setLayoutX(view.getLayoutX() - speed);
    }

    public void moveRight(double screenWidth) {
        if (view.getLayoutX() + ((Rectangle) view).getWidth() < screenWidth) {
            view.setLayoutX(view.getLayoutX() + speed);
        }
    }

}
