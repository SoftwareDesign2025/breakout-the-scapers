package GameElemtents;

import GameUtils.ColorEditor;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
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
        view = new Rectangle(width, height, color);
        this.setPositionX(x);
        this.setPositionY(y);
        // if the odds are right make it
        if (Math.random() < CHANCE_FOR_POWERUP) {
        	this.powerUpBrick = true;
        }
    }

    // for now update does nothing
    @Override
    public void update(double elapsedTime) {}

    // returns true if the brick is destroyed default 1 damage
    public boolean onHit() {
        return onHit(1);
    }
    
    // applies damage to the brick and returns true if destroyed
    public boolean onHit(int damage) {
        hp -= damage;
        
        ColorEditor.alterViewHue(view, 15);
        this.isBreakDead = hp <= 0;
        return isBreakDead;
    }
   
    
    public boolean hasPowerUp() {
        return powerUpBrick;
    }

    public int getPoints() {
        int temp = points;
        points/=2; // half the points each time to prevent farming
        return temp;
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
        ball.accelerate(10f); // speed up the ball slightly on each hit to make it harder and avoid softblocks
        Bounds brickBounds = this.getView().getBoundsInParent();

        // Use translated center position for accurate collision calculations
        double ballCenterX = ball.getX();
        double ballCenterY = ball.getY();
        double ballLeft = ballCenterX - ball.getRadius();
        double ballRight = ballCenterX + ball.getRadius();
        double ballTop = ballCenterY - ball.getRadius();
        double ballBottom = ballCenterY + ball.getRadius();

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

        offsetBallPositionAcordingly(ball, 
        ballLeft, ballRight, ballTop, ballBottom, 
        brickLeft, brickRight, brickTop, brickBottom);

        // now bounce accordingly
        if (hitVertically) {
            ball.bounceVertical();
        }

        if (hitHorizontally) {
            ball.bounceHorizontal();
        }
        
    }


    private void offsetBallPositionAcordingly( Ball ball, double ballLeft, double ballRight,
            double ballTop, double ballBottom,
            double brickLeft, double brickRight,
            double brickTop, double brickBottom) {
        // ball intersects brick from the right
        if (ballLeft > brickRight){
            ball.offsetPositionHorizontal(brickRight - ballLeft );
        }
        if( ballRight < brickLeft) {
        	ball .offsetPositionHorizontal(brickLeft - ballRight );
        }
        if (ballTop > brickBottom) {
        	ball.offsetPositionVertival(brickBottom - ballTop );
        }
        if (ballBottom < brickTop) {
        	ball.offsetPositionVertival(brickTop - ballBottom );
        }
    }
    
    public boolean deadBrick() {
        return hp <= 0;
    }
    
    @Override
    public boolean collideWithBall(Ball ball) {
    	Circle b = (Circle) ball.getView();
    	
    	if (b.getBoundsInParent().intersects(this.getView().getBoundsInParent())) {
            bounceBall(ball); // bounce off the brick surface
            //onHit() reduces HP, returns true if brick is destroyed
            if (this.onHit()) {
                if (deadBrick()) {
                    this.setVisible(false); // hide the brick
                }
            }
            return true; // brick was hit
        }
        return false; // brick was not hit
    }
    
    private void setVisible(boolean visible) {
        view.setVisible(visible);
    }

	public int getHP() {
		return this.hp;
	}
	
	//helpers influencing power-up spawns
	public Point2D getCenter() {
	    Rectangle rect = (Rectangle) view;
	    double centerX = rect.getX() + rect.getWidth() / 2;
	    double centerY = rect.getY() + rect.getHeight() / 2;
	    return new Point2D(centerX, centerY);
	}

}