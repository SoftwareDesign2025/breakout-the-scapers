package GameElemtents;


import GameUtils.GameColors;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

//represents the paddle controlled by the player
//paddle can only move left and right
public class Paddle extends GameObject{
	private double speed = 800;
	private boolean moveLeft = false;
	private boolean moveRight = false;
	
	
    public Paddle(double x, double y, double width, double height, Paint color) {
        //makes a visual paddle as a rectangle
    	view = new Rectangle(x, y, width, height);
        ((Rectangle) view).setFill(color);
    }

    @Override
    public void update(double elapsedTime) {
    	
    	if (moveLeft) {
    		// move left lol
    		moveHorizontal(elapsedTime, -1);
    	}
    	if (moveRight) {
    		// move right
    		moveHorizontal(elapsedTime, 1);
    	}
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

}
