package GameElemtents;


import GameUtils.GameColors;
import javafx.geometry.Bounds;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Window;

//represents the paddle controlled by the player
//paddle can only move left and right 
public class Paddle extends GameObject{
	private double speed = 1200;
	private boolean moveLeft = false;
	private boolean moveRight = false;
	private boolean outOfBoundsLeft = false;
	private boolean outOfBoundsRight = false;
	private Paint color = GameColors.PRIMARY_COLOR.getColor();
	private double originalWidth; //for level change and powerups
	
    public Paddle(double x, double y, double width, double height, Paint color) {
        //makes a visual paddle as a rectangle
    	view = new Rectangle(x, y, width, height);
        ((Rectangle) view).setFill(color);
        this.originalWidth = width;  // store the starting size
    }
	public Paddle(double x, double y, double width, double height) {
		//makes a visual paddle as a rectangle
		view = new Rectangle(x, y, width, height);
		((Rectangle) view).setFill(color);
		this.originalWidth = width;  // store the starting size
	}

    @Override
    public void update(double elapsedTime) {
    	checkOutOfBounds();
    	if (moveLeft && !outOfBoundsLeft) {
    		// move left lol
    		moveHorizontal(elapsedTime, -1);
    	}
    	if (moveRight && !outOfBoundsRight) {
    		// move right
    		moveHorizontal(elapsedTime, 1);
    	}
    }
    
    private void checkOutOfBounds() {
        Bounds viewBounds = view.localToScreen(view.getBoundsInLocal());
        Window window = view.getScene().getWindow();

        double windowMinX = window.getX();
        double windowMaxX = windowMinX + window.getWidth();

        // Check if the view is outside the horizontal window bounds
        outOfBoundsLeft = viewBounds.getMinX() < windowMinX;
        outOfBoundsRight = viewBounds.getMaxX() > windowMaxX;
    }


	private void moveHorizontal(double elapsedTime, int directionalMultiplier) {
    	// Ensures smooth movement
    	view.setLayoutX(view.getLayoutX() + ( speed * directionalMultiplier * elapsedTime) ) ;
    }
    
    public void setMoveLeft(boolean value) {
        moveLeft = value;
    }

    public void setMoveRight(boolean value) {
        moveRight = value;
    }
    
    @Override
    public boolean collideWithBall(Ball ball) {
    	Circle b = (Circle) ball.getView(); // ball's graphical node
        Rectangle p = (Rectangle) this.getView(); // paddle's graphical node
        // 
        double minAngle = 15;
        // so 180 is a full semi circle we want to make it so that there is a range
        double maxAngle = 180 - minAngle;
        double angleRange = maxAngle-minAngle;
        double bounceAngle;

        if (b.getBoundsInParent().intersects(p.getBoundsInParent())) {
        	// with the angle range we can calculate proportionally how much
        	// the ball is in the paddle
        	Bounds paddleBounds = p.getBoundsInParent();
        	double ballX = b.getCenterX();
        	double distanceFromScreenEdge = paddleBounds.getMinX();
        	double ballRelativeOffsetToPaddle = ballX - distanceFromScreenEdge;
        	double proportionBallPaddle = ballRelativeOffsetToPaddle / p.getWidth();
        	// we want this to be a proportion so we need to keep it between 0 and 1 to make sure the
        	// minimum and max angles are respected
        	proportionBallPaddle = Math.clamp(proportionBallPaddle, 0, 1); 
        	// so we get how much of the full range we keep based on how close the x position
        	// of the ball is to the with of the paddle
        	// we add the minimum and that way we get out bounce angle
        	bounceAngle =  (angleRange * proportionBallPaddle) + minAngle;

            // Move the ball just above the paddle to avoid re-collision
            double newY = p.getY() - b.getRadius(); // 1 pixel gap for safety
            b.setCenterY(newY);
            // Set new bounce direction to be up by adding 180. because
            // up to this point we were going under the assumption +y is up but its down
            // so we add 180 to ofsset that
            ball.setDirection(180 + bounceAngle);
            return true;
        }
        return false;
    }

	public void expand() {
		Rectangle rect = (Rectangle) getView();
	    double oldWidth = rect.getWidth();
	    double newWidth = oldWidth * 2;  // doubles the width

	    // Center the paddle by shifting it left half of the growth amount
	    rect.setX(rect.getX() - (newWidth - oldWidth) / 2);
	    rect.setWidth(newWidth);
		
	}
	
	// resets paddle width to original when a new level starts
	public void resetSize() {
		Rectangle rect = (Rectangle) view;
	    rect.setWidth(originalWidth);
	}

}
