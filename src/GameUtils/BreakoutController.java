package GameUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import javafx.animation.Timeline;
import GameElemtents.Ball;
import GameElemtents.Brick;
import GameElemtents.BrickUnbreakable;
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
import GameElemtents.PowerUps;


public class BreakoutController {
	private final Paint PADDLE_COLOR = GameColors.PRIMARY_COLOR.getColor();
    public static final int PADDLE_SPEED = 10;
    private ScoreKeeper scoreKeeper = new ScoreKeeper();
    
    private final Paint BALL_COLOR = GameColors.ACCENT_COLOR.getColor();
    public final int BALL_RADIUS = 10;
    
    public final int LIVES_START = 3;
    
    private Paddle paddle;
    private Ball ball; 

    private List<Brick> bricks;
    private List<Brick> bricksOptional;

    private int score;
    private int lives;
    private Text scoreLabel;
    private Text livesLabel;
    private Color textColor = GameColors.TEXT_COLOR.getColor(); 
    
    private Text highScoreText;
    
    
    private double width;
    private double height;
    
    private Timeline animation;

    
    
    private List<PowerUps> powerUps = new ArrayList<>();
    private Group root;
    
    //level fields
    private int currentLevel = 1;
    private static final int MAX_LEVELS = 3;
    
    public void setAnimation(Timeline animation) {
        this.animation = animation;
    }


    //create game objects and sets up the initial scene layout
    public Group createRoot(int windowWidth, int windowHeight) {
        width = windowWidth;
        height = windowHeight;

        root = new Group();
        //change 200 back to 100
        paddle = new Paddle(width / 2 - 50, height - 50, 200, 15, PADDLE_COLOR);
     
        //create ball at the center of screen
        ball = new Ball(width / 2, height / 2, BALL_RADIUS, BALL_COLOR);
        
        //create bricks
        bricks = new ArrayList<>();
        bricksOptional = new ArrayList<>();

        // toDo for later make this into a method and call that 3 times
        
        brickMaker(100);
        brickMaker(150);
        brickMaker(200);
        brickMakerUbreakable(250);

        score = 0;
        lives = LIVES_START;
        //track current level
        int currentLevel = 1;

        //call helper to load level layout dynamically
        loadLevel(currentLevel, root);

        // Score and lives text setup
        scoreLabel = new Text(20, 20, "Score: 0");
        scoreLabel.setFill(textColor);
        livesLabel = new Text(500, 20, "Lives: " + lives);
        livesLabel.setFill(textColor);
        
//        highScoreText = new Text(20,20, "High Score: " + readLastNumberFromFile());
//        highScoreText.setFill(Color.BLACK);

        //add visual components to root group
        root.getChildren().addAll(paddle.getView(), ball.getView(), scoreLabel, livesLabel);
        return root;
        
    }
    
    private void brickMaker(int yaxis) {
        for (int i = 0; i < 10; i++) {
            Brick brick = new Brick(50 + i * 50, yaxis, 40, 20, 3);
            bricks.add(brick);
            //add visual node to scene
            root.getChildren().add(brick.getView());
        }
    }
    
    private void brickMakerUbreakable(int yaxis) {
        for (int i = 0; i < 10; i++) {
            Brick brick = new BrickUnbreakable(50 + i * 50, yaxis, 40, 20, 3);
            bricks.add(brick);
            //add visual node to scene
            root.getChildren().add(brick.getView());
        }
    }
    

    // makes the stage that shows the player the game has been won 
    private void win_game() {
    	System.out.println(scoreKeeper.readLastNumberFromFile());
        try {
            if (animation != null) {
                animation.stop(); // Stop the game loop
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("win_game.fxml"));
            Parent root = loader.load();
            // set current score
            Label showScore = (Label) root.lookup("#scoreLabel");
            if (showScore != null) {
            	showScore.setText("Final Score: " + score);
            }
            
            
            Label oldScoreLabel = (Label) root.lookup("#prevHigh");
            

        
            scoreKeeper.checkHighScore(score);
        	oldScoreLabel.setText("High Score is: " + score);
            
            
            
            Stage stage = (Stage) scoreLabel.getScene().getWindow();
            Scene scene = new Scene(root, 600, 800);
            stage.setScene(scene);
            stage.show();
            System.out.println(scoreKeeper.readLastNumberFromFile());
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
            
            Label showScore = (Label) root.lookup("#scoreLabel");
            if (showScore != null) {
            	showScore.setText("Your Failed Final Score: " + score);
            }
            
            
            Label oldScoreLabel = (Label) root.lookup("#prevHigh");
            
            
            System.out.println(scoreKeeper.readLastNumberFromFile());
            System.out.println(score);
            
            scoreKeeper.checkHighScore(score);
        	oldScoreLabel.setText("High Score is: " + scoreKeeper.readLastNumberFromFile());
      
            
            
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
    	
    	
    	
        ball.update(elapsedTime);
        paddle.update(elapsedTime);
        //collisions with ball paddle/bricks
        CollisionManager.handleBallPaddle(ball, paddle);
        CollisionManager.handleBallBricks(ball, bricks, this);
        CollisionManager.handleBallBricks(ball, bricksOptional, this);
        
        //update and collect power-ups
        Iterator<PowerUps> puIterator = powerUps.iterator();
        while (puIterator.hasNext()) {
            PowerUps pu = puIterator.next();
            pu.update(elapsedTime); // fall downward

            // Check if paddle collects power-up
            if (pu.isActive() && pu.getView().getBoundsInParent().intersects(paddle.getView().getBoundsInParent())) {
                pu.collect();           // hide and deactivate
                applyPowerUp(pu);       // apply effect (expand paddle, etc.)
                puIterator.remove();    // remove from list
            }
        }
        //level progression
        if (allBreakableBricksCleared()) {
            nextLevel();
            if (currentLevel <= MAX_LEVELS) {
                resetBall();
            }
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

//        if (bricks.isEmpty()) {
//            nextLevel();
//        }
        
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
    
  //apply the effect of a collected power-up
    private void applyPowerUp(PowerUps pu) {
        if (pu instanceof PowerUps.ExpandPaddlePowerUp) {
            paddle.expand(); // expands paddle width
        } 
        else if (pu instanceof PowerUps.SlowBallPowerUp) {
            ((PowerUps.SlowBallPowerUp) pu).applyEffect(ball);
        } 
        else if (pu instanceof PowerUps.ExtraLifePowerUp) {
            ((PowerUps.ExtraLifePowerUp) pu).applyEffect(this);
        }
//        else if (pu instanceof PowerUps.MultiBallPowerUp) {
//            // logic to spawn additional balls if implemented
//        }
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
    
    //level generator
    private void loadLevel(int level, Group root ) {
    	//remove previous power-up nodes from scene (important: do this BEFORE clearing the list)
        if (powerUps != null) {
            for (PowerUps pu : powerUps) {
                if (pu != null && pu.getView() != null) {
                    root.getChildren().remove(pu.getView());
                }
            }
        }
        // now clear the list
        powerUps.clear();

        //clear bricks (and remove their nodes)
        if (bricks != null) {
            for (Brick b : bricks) {
                if (b != null && b.getView() != null) {
                    root.getChildren().remove(b.getView());
                }
            }
        }
        bricks.clear();

        //reset paddle/power-up state
        paddle.resetSize();

        //remove any leftover rectangles (old bricks) but keep paddle/ball/labels
        root.getChildren().removeIf(node -> {
            // keep paddle and ball and UI texts
            if (node == paddle.getView() || node == ball.getView() || node == scoreLabel || node == livesLabel) {
                return false;
            }
            bricksOptional.clear();
            // remove shapes (rectangles / circles) that are not the UI/paddle/ball
            return node instanceof javafx.scene.shape.Shape;
            
        });

        //make sure we create at most one powerup per level
        boolean expandPowerUpCreated = false;
        boolean slowBallPowerUpCreated = false;
        boolean extraLifePowerUpCreated = false;
        
        switch (level) {
            case 1 -> {
                for (int i = 0; i < 1; i++) {
                    Brick brick = new Brick(50 + i * 50, 100, 40, 20, 1);
                    bricks.add(brick);
                    root.getChildren().add(brick.getView());
                }

                // for testing I will add unbreakable bricks in level 1 as well
                for (int i = 0; i < 5; i++) {
                    Brick brick = new BrickUnbreakable(60 + i * 55, 250, 40, 40, 3);
                    bricksOptional.add(brick);
                    root.getChildren().add(brick.getView());
                }
            }
            case 2 -> {
                for (int row = 0; row < 5; row++) {
                    for (int col = 0; col < 8; col++) {
                        Brick brick = new Brick(70 + col * 60, 80 + row * 30, 50, 20, 2);
                        bricks.add(brick);
                        root.getChildren().add(brick.getView());
                        
                        if (!expandPowerUpCreated && Math.random() < 0.15) {
                            PowerUps p = new PowerUps.ExpandPaddlePowerUp(brick.getX(), brick.getY());
                            powerUps.add(p);
                            root.getChildren().add(p.getView());
                            expandPowerUpCreated = true;
                        }
                        if (!slowBallPowerUpCreated && Math.random() < 0.10) {
                            PowerUps slow = new PowerUps.SlowBallPowerUp(brick.getX(), brick.getY());
                            powerUps.add(slow);
                            root.getChildren().add(slow.getView());
                            slowBallPowerUpCreated = true;
                        }

                        if (!extraLifePowerUpCreated && Math.random() < 0.10) {
                            PowerUps extraLife = new PowerUps.ExtraLifePowerUp(brick.getX(), brick.getY());
                            powerUps.add(extraLife);
                            root.getChildren().add(extraLife.getView());
                            extraLifePowerUpCreated = true;
                        }
                       
                    }
                }
                // for testing I will add unbreakable bricks in level 2 as well
                for (int i = 0; i < 5; i++) {
                    Brick brick = new BrickUnbreakable(220 + i * 55, 250, 40, 20, 3);
                    bricksOptional.add(brick);
                    root.getChildren().add(brick.getView());
                }
            }
            case 3 -> {
                for (int row = 0; row < 6; row++) {
                    for (int col = 0; col < 9; col++) {
                        Brick brick = new Brick(60 + col * 55, 80 + row * 25, 50, 20,
                                3);
                        bricks.add(brick);
                        root.getChildren().add(brick.getView());
                        
                        if (!expandPowerUpCreated && Math.random() < 0.20) {
                            PowerUps p = new PowerUps.ExpandPaddlePowerUp(brick.getX(), brick.getY());
                            powerUps.add(p);
                            root.getChildren().add(p.getView());
                            expandPowerUpCreated = true;
                        }
                        
                        if (!slowBallPowerUpCreated && Math.random() < 0.15) {
                            PowerUps slow = new PowerUps.SlowBallPowerUp(brick.getX(), brick.getY());
                            powerUps.add(slow);
                            root.getChildren().add(slow.getView());
                            slowBallPowerUpCreated = true;
                        }
                        
                        if (!extraLifePowerUpCreated && Math.random() < 0.15) {
                            PowerUps extraLife = new PowerUps.ExtraLifePowerUp(brick.getX(), brick.getY());
                            powerUps.add(extraLife);
                            root.getChildren().add(extraLife.getView());
                            extraLifePowerUpCreated = true;
                        }
                    }
                }

                // lasttly for level 3 we add a row of unbreakable bricks
                for (int i = 0; i < 9; i++) {
                    Brick brick = new BrickUnbreakable(60 + i * 55, 250, 50, 50, Integer.MAX_VALUE);
                    bricksOptional.add(brick);
                    root.getChildren().add(brick.getView());
                }
            }
            default -> {
                // fallback: treat as level 1
                loadLevel(1, root);
            }
        }
    }
    
    //single-parameter version
    private void loadLevel(int levelNumber) {
        loadLevel(levelNumber, this.root);
    }

    //called when all bricks are cleared to progress to next level.
   private void nextLevel() {
	   currentLevel++;
       System.out.println("Level cleared! Loading next level: " + currentLevel);
       
       if (currentLevel > MAX_LEVELS) {
           win_game();
           return;
       }

       //load the next level
       loadLevel(currentLevel, root);
       //reset any temporary power-ups
       paddle.resetSize();
       ball.resetSpeed();
       //reset ball and continue
       resetBall();
   }
   
   //to check if all removable bricks are removed
   private boolean allBreakableBricksCleared() {
	    for (Brick b : bricks) {
	        if (b.getHP() > 0 && b.getHP() != Integer.MAX_VALUE) {
	            return false; //still breakable bricks remaining
	        }
	    }
	    return true;
	}
   
   //for life powerup
   public void addLife() {
	    lives++;
	}
}