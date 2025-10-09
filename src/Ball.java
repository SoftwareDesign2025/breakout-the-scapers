
import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

//represents the moving ball in the game
public class Ball extends GameObject{
	private Point2D velocity;

	//constructor for a ball with position, radius, and color
    public Ball(double x, double y, double radius, Paint color) {
        view = new Circle(x, y, radius, color);
        velocity = new Point2D(150, 150); //set initial velocity
    }

    //update ball position based on velocity and elapsed time
    @Override
    public void update(double elapsedTime) {
        move(elapsedTime);
    }

    public void move(double elapsedTime) {
        Circle c = (Circle) view;
        c.setCenterX(c.getCenterX() + velocity.getX() * elapsedTime);
        c.setCenterY(c.getCenterY() + velocity.getY() * elapsedTime);

        // bounce off left/right walls
        if (c.getCenterX() <= c.getRadius() || c.getCenterX() >= 600 - c.getRadius()) {
            velocity = new Point2D(-velocity.getX(), velocity.getY());
        }

        // bounce off top
        if (c.getCenterY() <= c.getRadius()) {
            velocity = new Point2D(velocity.getX(), -velocity.getY());
        }
    }

    public void bounceVertical() {
        velocity = new Point2D(velocity.getX(), -velocity.getY());
    }

    public void bounceHorizontal() {
        velocity = new Point2D(-velocity.getX(), velocity.getY());
    }

    public void reset(double x, double y) {
        ((Circle) view).setCenterX(x);
        ((Circle) view).setCenterY(y);
        velocity = new Point2D(150, 150);
    }
    
    public double getX() {
        return ((Circle) view).getCenterX();
    }

    public double getY() {
        return ((Circle) view).getCenterY();
    }

}
