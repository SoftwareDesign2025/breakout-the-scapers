
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class BreakoutController {
    public static final int PADDLE_SPEED = 10;
    public static final int BALL_RADIUS = 10;
    public static final int LIVES_START = 3;

    private Paddle paddle;
    private Ball ball; 
    private List<Brick> bricks;
    private CollisionManager collisionManager;

    private int score;
    private int lives;
    private Text scoreLabel;
    private Text livesLabel;

    private double width;
    private double height;

    //create game objects and sets up the initial scene layout
    public Group createRoot(int windowWidth, int windowHeight) {
        width = windowWidth;
        height = windowHeight;

        Group root = new Group();
        paddle = new Paddle(width / 2 - 50, height - 50, 100, 15, Color.WHITE);
     
        //create ball at the center of screen
        ball = new Ball(width / 2, height / 2, BALL_RADIUS, Color.CYAN);
        
        //create bricks
        bricks = new ArrayList<>();
        collisionManager = new CollisionManager();

        //create a simple row of bricks
        for (int i = 0; i < 10; i++) {
            Brick brick = new Brick(50 + i * 50, 100, 40, 20, Color.ORANGE, 1);
            bricks.add(brick);
            //add visual node to scene
            root.getChildren().add(brick.getView());
        }

        score = 0;
        lives = LIVES_START;

        scoreLabel = new Text(20, 20, "Score: 0");
        scoreLabel.setFill(Color.WHITE);
        livesLabel = new Text(500, 20, "Lives: " + lives);
        livesLabel.setFill(Color.WHITE);

        //add visual components to root group
        root.getChildren().addAll(paddle.getView(), ball.getView(), scoreLabel, livesLabel);
        return root;
    }

    public void step(double elapsedTime) {
    	//move the ball based on its velocity and the elapsed frame time
        ball.move(elapsedTime);
        
        //collisions with ball paddle/bricks
        collisionManager.handleBallPaddle(ball, paddle);
        collisionManager.handleBallBricks(ball, bricks, this);

        //if ball falls below the screen, lose a life and reset ball
        if (ball.getY() > height) {
            lives--;
            resetBall();
        }

        scoreLabel.setText("Score: " + score);
        livesLabel.setText("Lives: " + lives);
    }

    public void movePaddle(boolean right) {
        if (right) paddle.moveRight(width);
        else paddle.moveLeft();
    }

    public void addScore(int points) {
        score += points;
    }

    //reset ball to the center of the screen
    public void resetBall() {
        ball.reset(width / 2, height / 2);
    }

    public void gameOver() {
        System.out.println("Game Over! Final score: " + score);
    }
}