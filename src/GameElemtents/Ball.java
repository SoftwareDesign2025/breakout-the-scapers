package GameElemtents;


import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

//represents the moving ball in the game
public class Ball extends GameObject{
	// keeps track of current angle
	private double angleDegrees = 45;
    final double DEFAULT_ANGLE = 45;
	private double speed = 500;
	private Point2D velocity;
	
public void setDirection(double angleDegrees){
		this.angleDegrees = angleDegrees;
		// to get the velocity in x we do cosine of angle * speed
		// cosine means how horizontal something is so x axis
		double x = Math.cos(Math.toRadians(angleDegrees))* speed;
		
		// to get the velocity in the y direction we use sin instead
		// sine means how vertical something is
		double y = Math.sin(Math.toRadians(angleDegrees))* speed;
//		System.out.println("X:" + x + " Y:" + y);
		
		// now that we have our x and y velocities we can update the velocity vector
		velocity = new Point2D(x,y);
	}

	//constructor for a ball with position, radius, and color
    public Ball(double x, double y, double radius, Paint color) {
        view = new Circle(x, y, radius, color);
        setDirection(45);
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
        setDirection(this.DEFAULT_ANGLE);
    }
    
    public double getX() {
        return ((Circle) view).getCenterX();
    }

    public double getY() {
        return ((Circle) view).getCenterY();
    }
    
    public void updateSpeed(double newSpeed) {
    	this.speed = newSpeed; // set new speed
    	// update velocity vector continuing with current angle
    	setDirection(this.angleDegrees);
    }

}
