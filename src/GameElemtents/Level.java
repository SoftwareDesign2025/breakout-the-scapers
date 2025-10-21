package GameElemtents;

import java.util.ArrayList;
import java.util.List;

import GameUtils.CollisionManager;
import GameUtils.GameColors;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class Level {

	private final Paint PADDLE_COLOR = GameColors.PRIMARY_COLOR.getColor();
    public static final int PADDLE_SPEED = 10;
    
    private final Paint BALL_COLOR = GameColors.ACCENT_COLOR.getColor();
    private Ball ball; 
    public final int BALL_RADIUS = 10;
    private final int BALL_SPEED = 20;
    
    public final int LIVES_START = 3;

    private Paddle paddle;
    
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
    
    public Level() {
    	
    }
	/// Creates all level elements
	public Group createRoot(int windowWidth, int windowHeight) {
        int width = windowWidth;
        int height = windowHeight;

        root = new Group();
        paddle = new Paddle(width / 2 - 50, height - 50, 100, 15, PADDLE_COLOR);
     
        //create ball at the center of screen
        ball = new Ball(width / 2, height / 2, BALL_RADIUS, BALL_COLOR);
        
        //create bricks
        bricks = new ArrayList<>();

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
            root.getChildren().add(brick.getView());//
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
}
