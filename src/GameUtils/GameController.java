package GameUtils;

import java.util.ArrayList;
import java.util.Iterator;
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
import javafx.scene.text.Text;

import GameElemtents.PowerUps;


public abstract class GameController {
    

    protected double width;
    protected double height;
    
    protected Timeline animation;
    protected Group root;
    protected int score;
    protected int lives;
    protected final int LIVES_START = 3;
    
    protected List<Paddle> paddles = new ArrayList<>();
    protected List<Ball> balls = new ArrayList<>();
    protected List<Brick> bricks = new ArrayList<>();
    protected List<Brick> unbreakableBricks = new ArrayList<>();
    protected List<PowerUps> powerUps = new ArrayList<>();
    
    protected Text scoreLabel;
    protected Text livesLabel;
    private Color textColor = GameColors.TEXT_COLOR.getColor();
    protected ScreenMaker screenMaker = new ScreenMaker();
    
    protected int currentLevel = 1;
    protected List<Level> levels = new ArrayList<>();
    
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
        currentLevel = 1;

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
    
    protected void setTextAndLabels(){
        // Score and lives text setup
        scoreLabel = new Text(20, 20, "Score: 0");
        scoreLabel.setFill(textColor);
        livesLabel = new Text(500, 20, "Lives: " + lives);
        livesLabel.setFill(textColor);

        root.getChildren().addAll(scoreLabel, livesLabel);
    }

  


    
    
    public abstract void step(double elapsedTime);

    public void addScore(int points) {
        score += points;
    }

 
    
  //apply the effect of a collected power-up
    protected abstract void applyPowerUp(PowerUps pu);

    
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
    protected abstract void loadAllLevels();

    // new loadLevel which uses Level objects
    protected void loadLevel(int levelNumber, Group root) {
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
    protected abstract void nextLevel();
   
   //to check if all removable bricks are removed
   protected boolean allBreakableBricksCleared() {
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