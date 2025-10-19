package GameUtils;

import java.util.Iterator;
import java.util.List;

import GameElemtents.Ball;
import GameElemtents.Brick;
import GameElemtents.Paddle;
import javafx.geometry.Bounds;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

//keeps the physics and scoring logic separate from the controller
public class CollisionManager {
	
	//checks if the ball hits the paddle, and makes it bounce upward proportionally on how much
	// to the right is with respect to the paddle Warning, there is a lot of math on this
	public void handleBallPaddle(Ball ball, Paddle paddle) {
        Circle b = (Circle) ball.getView(); // ball's graphical node
        Rectangle p = (Rectangle) paddle.getView(); // paddle's graphical node
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
        }
    }

	//checks collisions between the ball and all bricks
    //removes bricks when destroyed, and adds points
    public void handleBallBricks(Ball ball, List<Brick> bricks, BreakoutController controller) {
        Circle b = (Circle) ball.getView();
        Iterator<Brick> it = bricks.iterator(); //safe way to remove while iterating
        while (it.hasNext()) {
            Brick brick = it.next();
            brick.collideWithBall(ball, controller);
            if (brick.deadBrick()) {
            	it.remove();
            }
        }
    }

}
