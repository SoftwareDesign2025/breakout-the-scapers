package GameElemtents;


import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;


public class PowerUps extends GameObject {

    private Point2D velocity; 
    private boolean active = true;
	
    
    // make our power up and as it spawns it falls to the ground
	public PowerUps(double x, double y, double radius, Paint color) {
        //makes a rectangle to visually represent the brick
		view = new Circle(x, y, radius, color);
		
		velocity = new Point2D(0, 150); // onlt thing it will do is fall
	}

	@Override
	public void update(double elapsedTime) {
        if (active) {
            view.setTranslateY(view.getTranslateY() + velocity.getY() * elapsedTime);
        }
		
	}
	
   public boolean isActive() {
        return active;
    }
   
   public void collect() {
       active = false; // stop movement
       view.setVisible(false);
   }
}
