package GameElemtents;
// Author: Jose Andres Besednjak Izquierdo 

import GameUtils.GameColors;
import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

//represents the moving ball in the game
public class Ball extends GameObject{
	// keeps track of current angle
	private double angleDegrees = 45;
    final double DEFAULT_ANGLE = 360-90-90-90;
	
    private final double DEFAULT_SPEED = 300;
    private double speed = DEFAULT_SPEED;
	private Point2D velocity;
	private double radius = 10;
    private Paint color = GameColors.ACCENT_COLOR.getColor();
    // this method uses some trigonometry tricks to set the direction of the ball
    // based on the angle its moving and the speed. the math is explained in the comments
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
        this(x, y);
        this.radius = radius;
        this.color = color;
        setDirection(DEFAULT_ANGLE);
    }

    public Ball(double x, double y) {
        view = new Circle(0, 0, radius, color);
        view.setTranslateX(x);
        view.setTranslateY(y);
        setDirection(DEFAULT_ANGLE);
    }

    //update ball position based on velocity and elapsed time it assumes the wall of the screen is at x=0 and x=600
    @Override
    public void update(double elapsedTime) {

        offsetPositionHorizontal( velocity.getX() * elapsedTime);
        offsetPositionVertival(velocity.getY() * elapsedTime);

        // bounce off left/right walls
        if (this.getX() <= this.radius || this.getX() >= 600 - this.radius ) {
            velocity = new Point2D(-velocity.getX(), velocity.getY());
            if (this.getX() <= this.radius ) {
            	offsetPositionHorizontal(this.radius - this.getX());
            }
            else {
            	offsetPositionHorizontal(-(this.getX() - (600 - this.radius)));
            }
        }

        // bounce off top
        if (this.getY() <= this.radius) {
           bounceVertical();
           offsetPositionVertival(this.radius - this.getY());
        }
    }

    // this method changes the vertical direction of the ball
    public void bounceVertical() {
        velocity = new Point2D(velocity.getX(), -velocity.getY());
    }

    // this method changes the horizontal direction of the ball
    public void bounceHorizontal() {
        velocity = new Point2D(-velocity.getX(), velocity.getY());
    }

    // this method sets the position of the value to the x and y cordinates given
    public void reset(double x, double y) {
        view.setTranslateX(x);
        view.setTranslateY(y);
        this.speed = DEFAULT_SPEED;
        setDirection(this.DEFAULT_ANGLE);
    }

    
    // method to get velocity of ball
    public Point2D getVelocity() {
    	return this.velocity;
    }
    
    //especially for powerups
    public void setVelocity(Point2D newVelocity) {
        updateSpeed(newVelocity.magnitude());
    }
    
    // method to update speed of ball
    public void updateSpeed(double newSpeed) {
    	this.speed = newSpeed; // set new speed
    	// update velocity vector continuing with current angle
    	setDirection(this.angleDegrees);

    }
    
    // this implementation of collide with ball
    // takes in the ball, it checks if its colliding with the ball
    // if it is it bounces the ball both horizontally and vertically to deflect it
    // not really physically accurate but works for our game for now
    // it assume the ball is represented by a circle
    @Override
    public boolean collideWithBall(Ball ball) {

    	if (ball.getView().getBoundsInParent().intersects(this.getView().getBoundsInParent())) {
            this.bounceHorizontal();
            this.bounceVertical();
            return true;
        }
    	return false;
    }

    // this function allows for you to increase or decrease the speed of the ball
    // it returns nothing and takes in the amount of speed to change by as the parameter
    public void accelerate(double deltaSpeed) {
    	this.speed += deltaSpeed;
    	updateSpeed(this.speed);
    }

    public void changeAngle(double deltaAngle) {
    	this.angleDegrees += deltaAngle;
    	setDirection(this.angleDegrees);
    }
    
    public void resetSpeed() {
        // reset to your initial speed, e.g.:
        updateSpeed(DEFAULT_SPEED);
    }

    public double getRadius() {
        return radius;
    }
}
