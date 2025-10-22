package GameElemtents;


import javafx.geometry.Bounds;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Window;

//represents the paddle controlled by the player
//paddle can only move left and right 
public class Paddle extends GameObject{
	private double speed = 800;
	private boolean moveLeft = false;
	private boolean moveRight = false;
	private boolean outOfBoundsLeft = false;
	private boolean outOfBoundsRight = false;
	
	private double originalWidth; //for level change and powerups
	
    public Paddle(double x, double y, double width, double height, Paint color) {
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
	    Rectangle rect = (Rectangle) getView();
	    double currentWidth = rect.getWidth();
	    double diff = currentWidth - originalWidth;
	    rect.setWidth(originalWidth);
	    rect.setX(rect.getX() + diff / 2); // re-center after shrinking back
	}

}
