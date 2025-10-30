package GameElemtents;


import GameUtils.BreakoutController;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


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
        	Circle c = (Circle) view;
        	c.setCenterY(c.getCenterY() + velocity.getY() * elapsedTime);
        }
		
	}
	
   public boolean isActive() {
        return active;
    }
   
   public void collect() {
       active = false; // stop movement
       view.setVisible(false);
   }
   
 //to check bounds
   public javafx.scene.Node getView() {
	    return view;
	}
   
   //powerup subclasses
   //expands paddle
   public static class ExpandPaddlePowerUp extends PowerUps {
       public ExpandPaddlePowerUp(double x, double y) {
           super(x, y, 10, Color.BLUE);
       }
       
       public void applyEffect(Paddle paddle) {
           Rectangle view = (Rectangle) paddle.getView();
           view.setWidth(view.getWidth() * 1.5);
       }
   }
   
   //slows down the ball temporarily
	public static class SlowBallPowerUp extends PowerUps {
	    public SlowBallPowerUp(double x, double y) {
	        super(x, y, 10, Color.PURPLE);
	    }
	
	    public void applyEffect(Ball ball) {
	        // reduce ball speed by 30%
	        ball.setVelocity(ball.getVelocity().multiply(0.7));
	    }
	}
	
	//gives player an extra life
	public static class ExtraLifePowerUp extends PowerUps {
	    public ExtraLifePowerUp(double x, double y) {
	        super(x, y, 10, Color.GOLD);
	    }

	    public void applyEffect(BreakoutController controller) {
	        controller.addLife();
	    }
	}

//   public static class MultiBallPowerUp extends PowerUps {
//       public MultiBallPowerUp(double x, double y) {
//           super(x, y, 10, Color.GREEN);
//       }

}
