package GameElemtents;


import GameUtils.BreakoutController;
import javafx.geometry.Bounds;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
//represents a single brick that can be hit by the ball
public class Brick extends GameObject{
	private int hp; //number of hits the brick can take
    private int points; //points gained when destroyed
    private boolean isBreakDead = false;
    
    private Boolean powerUpBrick = false;
    //makes a new brick at the given location and color
    public Brick(double x, double y, double width, double height, Paint color, int hp) {
        this.hp = hp;
        this.points = 150; 
        //makes a rectangle to visually represent the brick
        view = new Rectangle(x, y, width, height);
        ((Rectangle) view).setFill(color);
        
        // make a 10% chance of a brick being a power up brick
        if (Math.random() > 0.9) {
        	this.powerUpBrick = true;
        }
        
        
    }

    @Override
    public void update(double elapsedTime) {}

    // returns true if the hp is lower than 0
    public boolean onHit() {
        hp--;
        this.isBreakDead = hp <= 0;
        return isBreakDead;
        
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
        boolean hitVertically = (ballRight >= brickLeft && ballLeft <= brickRight) &&
                ((Math.abs(ballBottom - brickTop) < Math.abs(ballRight - brickLeft) && vy > 0) || // hitting top while moving down
                 (Math.abs(ballTop - brickBottom) < Math.abs(ballRight - brickLeft) && vy < 0));  // hitting bottom while moving up

        // Horizontal bounce check: ball hits left or right and is moving towards it
        boolean hitHorizontally = (ballBottom >= brickTop && ballTop <= brickBottom) &&
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
    public boolean collideWithBall(Ball ball, BreakoutController controller) {
    	Circle b = (Circle) ball.getView();
    	
    	if (b.getBoundsInParent().intersects(this.getView().getBoundsInParent())) {
            this.bounceBall(ball);; // bounce off the brick surface
            //onHit() reduces HP, returns true if brick is destroyed
            if (this.onHit()) {
                controller.addScore(this.getPoints());
                this.deadBrick();
            }
            return true;
        }
    	return false;
    }

}
