package GameElemtents;

import GameUtils.ColorEditor;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
//represents a single brick that can be hit by the ball
public class Brick extends GameObject{
	protected int hp; //number of hits the brick can take
    protected int points; //points gained when destroyed
    protected boolean isBreakDead = false;
    private final double CHANCE_FOR_POWERUP = 0.1f;
    
    private Boolean powerUpBrick = false;
    //makes a new brick at the given location and color
    public Brick(double x, double y, double width, double height, int hp) {
        this.hp = hp;
        this.points = 150; 
        //makes a rectangle to visually represent the brick
        view = new Rectangle(x, y, width, height);
        ((Rectangle) view).setFill(color);
        
        // if the odds are right make it
        if (Math.random() < CHANCE_FOR_POWERUP) {
        	this.powerUpBrick = true;
        }
    }

    @Override
    public void update(double elapsedTime) {}

    // returns true if the hp is lower than 0
        public boolean onHit() {
        hp--;
        color = ColorEditor.alterColorHue(10.0f, color); // change color hue on hit by 10 degrees in hue scale
        setBrickColor(color);
        this.isBreakDead = hp <= 0;
        return isBreakDead;
    }

    protected void setBrickColor(Color newColor) {
        ((Rectangle) view).setFill(newColor);
    }
    
    public boolean hasPowerUp() {
        return powerUpBrick;
    }


    public int getPoints() {
        return points;
    }
    
    private void bounceBall(Ball ball) {
        Circle ballView = (Circle) ball.getView();
        Bounds brickBounds = this.getView().getBoundsInParent();

        double ballLeft = ballView.getCenterX() - ballView.getRadius();
        double ballRight = ballView.getCenterX() + ballView.getRadius();
        double ballTop = ballView.getCenterY() - ballView.getRadius();
        double ballBottom = ballView.getCenterY() + ballView.getRadius();

        double brickLeft = brickBounds.getMinX();
        double brickRight = brickBounds.getMaxX();
        double brickTop = brickBounds.getMinY();
        double brickBottom = brickBounds.getMaxY();

        double vx = ball.getVelocity().getX();
        double vy = ball.getVelocity().getY();

        // Vertical bounce check: ball hits top or bottom and is moving towards it
        boolean hitVertically = 
                ((Math.abs(ballBottom - brickTop) < Math.abs(ballRight - brickLeft) && vy > 0) || // hitting top while moving down
                 (Math.abs(ballTop - brickBottom) < Math.abs(ballRight - brickLeft) && vy < 0));  // hitting bottom while moving up

        
        // Horizontal bounce check: ball hits left or right and is moving towards it
        boolean hitHorizontally = 
                ((Math.abs(ballRight - brickLeft) < Math.abs(ballBottom - brickTop) && vx > 0) ||  // hitting left side while moving right
                 (Math.abs(ballLeft - brickRight) < Math.abs(ballBottom - brickTop) && vx < 0));   // hitting right side while moving left

        if (hitVertically) {
            ball.bounceVertical();
        }

        if (hitHorizontally) {
            ball.bounceHorizontal();
        }
    }

    
    // If the brick is dead, hide it
    public boolean deadBrick() {
    	if (isBreakDead) {
    		this.getView().setVisible(false); // hide broken brick
    	}
    	return isBreakDead;
    }
    
    @Override
    public boolean collideWithBall(Ball ball) {
    	Circle b = (Circle) ball.getView();
    	
    	if (b.getBoundsInParent().intersects(this.getView().getBoundsInParent())) {
            this.bounceBall(ball);; // bounce off the brick surface
            //onHit() reduces HP, returns true if brick is destroyed
            if (this.onHit()) {
                this.deadBrick();
            }
            return true;
        }
    	return false;
    }

}
