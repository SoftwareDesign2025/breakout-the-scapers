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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import GameElemtents.PowerUps;

import GameUtils.Level;


//score keeper
public class BreakoutController extends Scoring{
    
    
    public final int LIVES_START = 3;

    private List<Paddle> paddles;
    private List<Ball> balls; 
    private List<Brick> bricks;
    private List<Brick> unbreakableBricks;

    private int score;
    private int lives;
    private Text scoreLabel;
    private Text livesLabel;
    private Color textColor = GameColors.TEXT_COLOR.getColor(); 
    
    
    private double width;
    private double height;
    
    private Timeline animation;

    
    
    private List<PowerUps> powerUps = new ArrayList<>();
    private Group root;
    
    //level fields
    private int currentLevel = 1;
    private static final int MAX_LEVELS = 3;
    private List<Level> levels = new ArrayList<>();
    
    public void setAnimation(Timeline animation) {
        this.animation = animation;
    }


    //create game objects and sets up the initial scene layout
    public Group createRoot(int windowWidth, int windowHeight) {
        width = windowWidth;
        height = windowHeight;

        root = new Group();
        
        
        //create bricks
        bricks = new ArrayList<>();
        unbreakableBricks = new ArrayList<>();

        score = 0;
        lives = LIVES_START;
        //track current level
        int currentLevel = 1;

        loadAllLevels();
        loadLevel(currentLevel, root);
        setTextAndLabels();
        
        return root;
    }

    private void resetBallsPaddles(){
    	//remove old paddle nodes
        if (paddles != null) {
            for (Paddle p : paddles) {
                root.getChildren().remove(p.getView());
            }
        }
        //removes ball for new one to be placed
        if (balls != null) {
            for (Ball b : balls) {
                root.getChildren().remove(b.getView());
            }
        }
        
        Paddle paddle = new Paddle(width / 2 - 50, height - 50, 100, 15); //change 200 back to 100
        paddles = new ArrayList<>();
        paddles.add(paddle);
        //create ball at the center of screen
        // its default direction is upward at 90 degrees
        Ball ball = new Ball(width / 2, height / 2);
        balls = new ArrayList<>();
        balls.add(ball);
        //add visual components to root group
        for (Paddle p : paddles) {
            root.getChildren().add(p.getView());
        }
        for (Ball b : balls) {
            root.getChildren().add(b.getView());
        }
        
    }
    
    private void setTextAndLabels(){
        // Score and lives text setup
        scoreLabel = new Text(20, 20, "Score: 0");
        scoreLabel.setFill(textColor);
        livesLabel = new Text(500, 20, "Lives: " + lives);
        livesLabel.setFill(textColor);

        root.getChildren().addAll(scoreLabel, livesLabel);
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
            // set current score
            Label showScore = (Label) root.lookup("#scoreLabel");
            if (showScore != null) {
            	showScore.setText("Final Score: " + score);
            }
            
            
            Label oldScoreLabel = (Label) root.lookup("#prevHigh");
            
            System.out.println(readLastNumberFromFile());
            System.out.println(score);
        
        	checkHighScore(score);
        	oldScoreLabel.setText("High Score is: " + score);
            
            
            
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
            
            Label showScore = (Label) root.lookup("#scoreLabel");
            if (showScore != null) {
            	showScore.setText("Your Failed Final Score: " + score);
            }
            
            
            Label oldScoreLabel = (Label) root.lookup("#prevHigh");
            
            
            System.out.println(readLastNumberFromFile());
            System.out.println(score);
            
        	checkHighScore(score);
        	oldScoreLabel.setText("High Score is: " + readLastNumberFromFile());
      
            
            
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

        //collisions with ball paddle/bricks
        CollisionManager.handleBallPaddle(balls, paddles);
        CollisionManager.handleBallBricks(balls, bricks, this);
        CollisionManager.handleBallBricks(balls, unbreakableBricks, this);


        //move the ball based on its velocity and the elapsed frame time
        for (Ball ball : balls) {
            ball.update(elapsedTime);
        }
        for (Paddle paddle : paddles) {
            paddle.update(elapsedTime);
        }
        
        //update and collect power-ups
        Iterator<PowerUps> puIterator = powerUps.iterator();
        while (puIterator.hasNext()) {
            PowerUps pu = puIterator.next();
            pu.update(elapsedTime); // fall downward

            // Check if paddle collects power-up
            for (Paddle paddle : paddles)
            {
                if (pu.isActive() && pu.getView().getBoundsInParent().intersects(paddle.getView().getBoundsInParent())) {
                pu.collect();           // hide and deactivate
                applyPowerUp(pu);       // apply effect (expand paddle, etc.)
                puIterator.remove();    // remove from list
                }
            }
        }
        //level progression
        if (allBreakableBricksCleared()) {
            nextLevel();
            return;
        }

        //if ball falls below the screen, lose a life and reset ball
        for (Ball ball : balls)
        {
            System.out.println(ball.getY());
            if (ball.getY() > height) {
            lives--;
            resetBall(ball);
            
            if (lives <= 0) {
                endGame();
                return;
                }
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
    public void resetBall(Ball ball) {
        ball.reset(width / 2, height / 2);
    }
    
  //apply the effect of a collected power-up
    private void applyPowerUp(PowerUps pu) {
        for (Paddle paddle : paddles) {
            if (pu instanceof PowerUps.ExpandPaddlePowerUp) {
            paddle.expand(); // expands paddle width
            } 
        }
        if (pu instanceof PowerUps.SlowBallPowerUp) {
            for (Ball ball : balls) {
                ((PowerUps.SlowBallPowerUp) pu).applyEffect(ball);
            }
        } 
        else if (pu instanceof PowerUps.ExtraLifePowerUp) {
            ((PowerUps.ExtraLifePowerUp) pu).applyEffect(this);
        }
        
//        else if (pu instanceof PowerUps.MultiBallPowerUp) {
//            // logic to spawn additional balls if implemented
//        }
    }

    
    public void setMoveLeft(boolean isMoving) {
        for (Paddle paddle : paddles){
            paddle.setMoveLeft(isMoving);
        }
    }

    public void setMoveRight(boolean isMoving) {
        for (Paddle paddle : paddles){
         paddle.setMoveRight(isMoving);
        }
    }
    
    //level generator
    //to be called once during initialization in createRoot
    private void loadAllLevels() {
        levels.clear();
        levels.add(LevelBuilder.createLevel1(GameColors.SECONDARY_COLOR.getColor()));
        levels.add(LevelBuilder.createLevel2());
        levels.add(LevelBuilder.createLevel3(GameColors.SECONDARY_COLOR.getColor()));
    }

    // new loadLevel which uses Level objects
    private void loadLevel(int levelNumber, Group root) {
        // defensive bounds
        if (levelNumber < 1) levelNumber = 1;
        if (levels.isEmpty()) loadAllLevels();
        if (levelNumber > levels.size()) levelNumber = levels.size();

        // remove old powerups & bricks (keep paddles/balls/UI)
        if (powerUps != null) {
            for (PowerUps pu : powerUps) {
                if (pu != null && pu.getView() != null) {
                    root.getChildren().remove(pu.getView());
                }
            }
        }
        powerUps.clear();

        if (bricks != null) {
            for (Brick b : bricks) {
                if (b != null && b.getView() != null) root.getChildren().remove(b.getView());
            }
        }
        bricks.clear();

        if (unbreakableBricks != null) {
            for (Brick b : unbreakableBricks) {
                if (b != null && b.getView() != null) root.getChildren().remove(b.getView());
            }
        }
        unbreakableBricks.clear();

        // remove leftover shapes but keep paddles/balls/labels
        root.getChildren().removeIf(node -> {
            if (node == scoreLabel || node == livesLabel) return false;
            if (paddles != null && paddles.stream().anyMatch(p -> p.getView() == node)) return false;
            if (balls != null && balls.stream().anyMatch(b -> b.getView() == node)) return false;
            // else remove old shapes
            return node instanceof javafx.scene.shape.Shape;
        });

        // populate from Level object
        Level level = levels.get(levelNumber - 1);
        level.populate(root, bricks, unbreakableBricks, powerUps);
        

        // reset paddles and balls for the level
        resetBallsPaddles();
        for (Paddle p : paddles) {
            p.resetSize();
        }
    }
    


    //called when all bricks are cleared to progress to next level.
    private void nextLevel() {
        currentLevel++;
        if (currentLevel > levels.size()) {
            win_game();
            return;
        }
        loadLevel(currentLevel, root);
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