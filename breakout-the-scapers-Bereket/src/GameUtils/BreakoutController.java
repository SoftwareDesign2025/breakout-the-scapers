package GameUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javafx.animation.Timeline;
import GameElemtents.Ball;
import GameElemtents.Brick;
import GameElemtents.Paddle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdk.incubator.vector.VectorOperators.Test;
import GameElemtents.PowerUps;


public class BreakoutController extends Scoring{
	private final Paint PADDLE_COLOR = GameColors.PRIMARY_COLOR.getColor();
    public static final int PADDLE_SPEED = 10;
    
    private final Paint BALL_COLOR = GameColors.ACCENT_COLOR.getColor();
    public final int BALL_RADIUS = 10;
    private final int BALL_SPEED = 20;
    
    public final int LIVES_START = 3;

    private Paddle paddle;
    private Ball ball; 
    
    private final Paint BRICK_COLOR = GameColors.SECONDARY_COLOR.getColor();
    private List<Brick> bricks;
    private CollisionManager collisionManager;

    private int score;
    private int lives;
    private Text scoreLabel;
    private Text livesLabel;
    
    private Text highScoreText;
    
    private double width;
    private double height;
    
    private Timeline animation;

    
    
    private List<PowerUps> powerUps = new ArrayList<>();
    private Group root;
    
    public void setAnimation(Timeline animation) {
        this.animation = animation;
    }


    //create game objects and sets up the initial scene layout
    public Group createRoot(int windowWidth, int windowHeight) {
        width = windowWidth;
        height = windowHeight;

        root = new Group();
        paddle = new Paddle(width / 2 - 50, height - 50, 100, 15, PADDLE_COLOR);
     
        //create ball at the center of screen
        ball = new Ball(width / 2, height / 2, BALL_RADIUS, BALL_COLOR);
        
        //create bricks
        bricks = new ArrayList<>();
        collisionManager = new CollisionManager();

        // toDo for later make this into a method and call that 3 times
        
      //create a simple row of bricks
        for (int i = 0; i < 10; i++) {
            Brick brick = new Brick(50 + i * 50, 100, 40, 20, BRICK_COLOR, 1);
            bricks.add(brick);
            //add visual node to scene
            root.getChildren().add(brick.getView());
        }
        
      //create a simple row of bricks
        for (int i = 0; i < 10; i++) {
            Brick brick = new Brick(50 + i * 50, 150, 40, 20, BRICK_COLOR, 1);
            bricks.add(brick);
            //add visual node to scene
            root.getChildren().add(brick.getView());
        }
        
      //create a simple row of bricks
        for (int i = 0; i < 10; i++) {
            Brick brick = new Brick(50 + i * 50, 200, 40, 20, BRICK_COLOR, 1);
            bricks.add(brick);
            //add visual node to scene
            root.getChildren().add(brick.getView());
        }

        score = 0;
        lives = LIVES_START;

        scoreLabel = new Text(20, 20, "Score: 0");
        scoreLabel.setFill(Color.BLACK);
        livesLabel = new Text(500, 20, "Lives: " + lives);
        livesLabel.setFill(Color.BLACK);
        
//        highScoreText = new Text(20,20, "High Score: " + readLastNumberFromFile());
//        highScoreText.setFill(Color.BLACK);

        //add visual components to root group
        root.getChildren().addAll(paddle.getView(), ball.getView(), scoreLabel, livesLabel);
        return root;
    }
    
    

    // makes the stage that shows the player the game has been won 
    private void win_game() {
    	System.out.println(readLastNumberFromFile());
        try {
            if (animation != null) {
                animation.stop(); // Stop the game loop
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("win_game.fxml"));
            Parent root = loader.load();
            
            Label showScore = (Label) root.lookup("#scoreLabel");
            if (showScore != null) {
            	showScore.setText("Final Score: " + score);
            }
            
            
            Label oldScoreLabel = (Label) root.lookup("#prevHigh");
            
            if (score > readLastNumberFromFile()) {
            	checkHighScore(score);
            	oldScoreLabel.setText("High Score is: " + score);
            }
            
            
            Stage stage = (Stage) scoreLabel.getScene().getWindow();
            Scene scene = new Scene(root, 600, 800);
            stage.setScene(scene);
            stage.show();
            System.out.println(readLastNumberFromFile());
            Button exitButton = (Button) root.lookup("#exitButton");
            exitButton.setOnAction(e -> stage.close());

        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }


    // when lives tick to 0 cut the game and tell the player game over in a new window
    private void endGame() {
        try {
            if (animation != null) {
                animation.stop(); // Stop the game loop
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("game_over.fxml"));
            Parent root = loader.load();
            

            
            Stage stage = (Stage) scoreLabel.getScene().getWindow();
            Scene scene = new Scene(root, 600, 800);
            stage.setScene(scene);
            stage.show();

            Button exitButton = (Button) root.lookup("#exitButton");
            exitButton.setOnAction(e -> stage.close());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    
    public void step(double elapsedTime) {
    	//move the ball based on its velocity and the elapsed frame time
    	
    	
    	
        ball.move(elapsedTime);
        paddle.update(elapsedTime);
        //collisions with ball paddle/bricks
        collisionManager.handleBallPaddle(ball, paddle);
        collisionManager.handleBallBricks(ball, bricks, this);
        
        
        if (bricks.isEmpty()) {
        	win_game();
        	return;
        }

        //if ball falls below the screen, lose a life and reset ball
        if (ball.getY() > height) {
            lives--;
            resetBall();
            
            if (lives <= 0) {
                endGame();
                return;
            }
        }

        scoreLabel.setText("Score: " + score);
        livesLabel.setText("Lives: " + lives);
        
        
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
    
    public void setMoveLeft(boolean isMoving) {
        paddle.setMoveLeft(isMoving);
    }

    public void setMoveRight(boolean isMoving) {
        paddle.setMoveRight(isMoving);
    }

}