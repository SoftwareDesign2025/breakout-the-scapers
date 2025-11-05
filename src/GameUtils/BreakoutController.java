package GameUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import GameElemtents.Ball;
import GameElemtents.Brick;
import GameElemtents.Paddle;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import GameElemtents.PowerUps;


public class BreakoutController extends GameController{

	public static final int PADDLE_SPEED = 10;
    private ScoreKeeper scoreKeeper = new ScoreKeeper();
    
    public final int LIVES_START = 3;
    
    @Override
    protected void loadAllLevels() {
        levels.clear();
        levels.add(LevelBuilder.createLevel1(GameColors.SECONDARY_COLOR.getColor()));
        levels.add(LevelBuilder.createLevel2());
        levels.add(LevelBuilder.createLevel3(GameColors.SECONDARY_COLOR.getColor()));
    }
    
	
	@Override
	public void step(double elapsedTime) {

        //collisions with ball paddle/bricks
        CollisionManager.handleBallPaddle(balls, paddles);
        addScore(CollisionManager.handleBallBricks(balls, bricks));
        addScore(CollisionManager.handleBallBricks(balls, unbreakableBricks));

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
            if (ball.getY() > height) {
            lives--;
            resetBall(ball);
            
            if (lives <= 0) {
                screenMaker.endGame(animation, score, scoreLabel, "Breakout");
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
	
	//called when all bricks are cleared to progress to next level.
	@Override
    protected void nextLevel() {
        currentLevel++;
        if (currentLevel > levels.size()) {
            screenMaker.winGame(animation, score, scoreLabel, "Breakout");
            return;
        }
        loadLevel(currentLevel, root);
    }
	
	@Override
	//apply the effect of a collected power-up
    protected void applyPowerUp(PowerUps pu) {
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
        

    }
    
    // personal easter egg (and for testing win game)
	@Override
    public void easterEggCheck(Boolean isPressed, String gameName) {
    	if (isPressed) {
    		screenMaker.winGame(animation, 999999, scoreLabel, gameName);
    	}
    }
	
	//reset ball to the center of the screen
    public void resetBall(Ball ball) {
        ball.reset(width / 2, height / 2);
    }

}
