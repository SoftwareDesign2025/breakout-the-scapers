package GameUtils;

import java.util.Iterator;
import java.util.List;

import GameElemtents.Ball;
import GameElemtents.Brick;
import GameElemtents.Paddle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

//keeps the physics and scoring logic separate from the controller
public class CollisionManager {
	
	//checks if the ball hits the paddle, and makes it bounce upward if so
	public void handleBallPaddle(Ball ball, Paddle paddle) {
        Circle b = (Circle) ball.getView(); // ball's graphical node
        Rectangle p = (Rectangle) paddle.getView(); // paddle's graphical node

     // check for intersection of bounding boxes
        if (b.getBoundsInParent().intersects(p.getBoundsInParent())) {
            ball.bounceVertical(); // reverse vertical direction
        }
    }

	//checks collisions between the ball and all bricks
    //removes bricks when destroyed, and adds points
    public void handleBallBricks(Ball ball, List<Brick> bricks, BreakoutController controller) {
        Circle b = (Circle) ball.getView();
        Iterator<Brick> it = bricks.iterator(); //safe way to remove while iterating

        while (it.hasNext()) {
            Brick brick = it.next();
         //check if ball and brick overlap
            if (b.getBoundsInParent().intersects(brick.getView().getBoundsInParent())) {
                ball.bounceVertical(); // bounce off the brick surface
                //onHit() reduces HP, returns true if brick is destroyed
                if (brick.onHit()) {
                    controller.addScore(brick.getPoints());
                    brick.getView().setVisible(false); // hide broken brick
                    it.remove(); // remove from list
                }
                break; //only handle one brick per frame
            }
        }
    }

}
