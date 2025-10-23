package GameUtils;

import java.util.Iterator;
import java.util.List;

import GameElemtents.Ball;
import GameElemtents.Brick;
import GameElemtents.Paddle;

//keeps the physics and scoring logic separate from the controller
public class CollisionManager { 
	
	//checks if the ball hits the paddle, and makes it bounce upward proportionally on how much
	// to the right is with respect to the paddle Warning, there is a lot of math on this
	public static void handleBallPaddle(Ball ball, Paddle paddle) {
        paddle.collideWithBall(ball);
    }

	//checks collisions between the ball and all bricks
    //removes bricks when destroyed, and adds points
    public static void handleBallBricks(Ball ball, List<Brick> bricks, BreakoutController controller) {
        Iterator<Brick> it = bricks.iterator(); //safe way to remove while iterating
        
        while (it.hasNext()) {
            Brick brick = it.next();
            if (brick.collideWithBall(ball)) {
            	controller.addScore(brick.getPoints());
            };
            if (brick.deadBrick()) {
            	it.remove();
            }
        }
    }
    
    public static void handleBallBall(Ball ball) {
       ball.collideWithBall(ball);
    }

}
