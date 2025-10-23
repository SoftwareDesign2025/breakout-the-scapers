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

    // for now update does nothing
    @Override
    public void update(double elapsedTime) {}

    // returns true if the brick is destroyed
    public boolean onHit() {
        if (isUnbreakable()) return false;
        hp--;
        color = ColorEditor.alterColorHue(15.0f, color);
        setBrickColor(color);
        this.isBreakDead = hp <= 0;
        return isBreakDead;
    }

    // assumes the brick is a rectangle
    // sets the color of the brick
    protected void setBrickColor(Color newColor) {
        ((Rectangle) view).setFill(newColor);
    }
   
    
    public boolean hasPowerUp() {
        return powerUpBrick;
    }

    public int getPoints() {
        return points;
    }
    
    // method to handle bouncing the ball off the brick
    // the logic it uses first checks which side of the brick the ball is hitting
    // then it checks if the ball is moving towards that side
    // if both are true it reverses the appropriate velocity component
    // which are the big boolean checks. To do that it gets the bounds of both the ball and the bricks
    // for both up down left and right sides it checks if the distance between the ball edge and brick edge is less than the distance to the other axis edge
    // this way we know which side the ball is closest to. The reason we check the direction of movement is to avoid weird behavior when the ball is inside the brick due to high speed
    // sometimes the ball bounces in the side of the brick and while is technically touching the side
    // it is already moving away from it so we dont want to reverse the velocity in that case
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

    
    public boolean deadBrick() {
        return hp <= 0;
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
        }
        return false; // brick still alive
    }
    
    private void setVisible(boolean visible) {
        view.setVisible(visible);
    }

	public int getHP() {
		return this.hp;
	}

}
