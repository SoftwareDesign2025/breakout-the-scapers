package GameElemtents;
import GameUtils.GameColors;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Circle;


/**
 * Simple ball bouncing around
 *
 * @author Jose Besednjak
 */
public class Ball {
    public static final int BAll_SPEED = 250;
    public static final int BALL_SIZE = 15;

    private Circle myBouncyBall;
    private Point2D myVelocity = new Point2D(BAll_SPEED,BAll_SPEED-5);


    /**
     * Create a bouncy ball
     */
    public Ball (int screenWidth, int screenHeight) {
        myBouncyBall = new Circle();
        myBouncyBall.setCenterX(screenWidth/2);
        myBouncyBall.setCenterY(screenHeight/2);
        myBouncyBall.setRadius(BALL_SIZE);
        myBouncyBall.setFill(GameColors.SECONDARY_COLOR.getColor());
    }

    /**
     * Move by taking one step based on its velocity.
     * Note, elapsedTime is used to ensure consistent speed across different machines.
     */
    public void move (double elapsedTime) {
        myBouncyBall.setCenterX(myBouncyBall.getCenterX() + myVelocity.getX() * elapsedTime);
        myBouncyBall.setCenterY(myBouncyBall.getCenterY() + myVelocity.getY() * elapsedTime);
    }

    /**
     * Bounce off the walls represented by the edges of the screen.
     * Notice we need to consider the radious now to see if its out of it
     */
    public void bounce(double screenWidth, double screenHeight) {
        double radius = myBouncyBall.getRadius();

        if (myBouncyBall.getCenterX() - radius < 0 || myBouncyBall.getCenterX() + radius > screenWidth) {
            myVelocity = new Point2D(-myVelocity.getX(), myVelocity.getY());
        }
        if (myBouncyBall.getCenterY() - radius < 0 || myBouncyBall.getCenterY() + radius > screenHeight) {
            myVelocity = new Point2D(myVelocity.getX(), -myVelocity.getY());
        }
    }

    /**
     * Returns internal view of bouncer to interact with other JavaFX methods.
     */
    public Node getView () {
        return myBouncyBall;
    }
}
