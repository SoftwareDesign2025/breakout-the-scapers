package GameUtils;
import GameElemtents.Brick;
import GameElemtents.BrickUnbreakable;
import GameElemtents.PowerUps;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


//helper class with static methods that builds level objects
public class LevelBuilder {
	public boolean expandCreated = false;
	public boolean slowCreated = false;
	public boolean lifeCreated = false;
	
	public static Level createLevel1(Paint palettePrimary) {
        Level level = new Level();
        // simple single row
        for (int i = 0; i < 10; i++) {
        	level.bricks.add(new Brick(50 + i * 50, 100, 40, 20, (int)1));
        }
        //if wanted to add a single expand power-up attached to a particular brick:
//        if (!level.bricks.isEmpty()) {
//            Brick anchor = level.bricks.get(2);
//            level.powerUps.add(new PowerUps.ExpandPaddlePowerUp(anchor.getX(), anchor.getY()));
//        }
        return level;
    }

    public static Level createLevel2() {
        Level level = new Level();
        boolean expandCreated = false;
        boolean slowCreated = false;
        boolean lifeCreated = false;


        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 8; col++) {
                Brick brick = new Brick(70 + col * 60, 80 + row * 30, 50, 20, 2);
                level.bricks.add(brick);
                Point2D center = brick.getCenter();

                // ensure at most one of each power-up
                if (!expandCreated && Math.random() < 0.12) {
                	level.powerUps.add(new PowerUps.ExpandPaddlePowerUp(center.getX(), center.getY()));
                    expandCreated = true;
                }
                if (!slowCreated && Math.random() < 0.08) {
                	level.powerUps.add(new PowerUps.SlowBallPowerUp(center.getX(), center.getY()));
                    slowCreated = true;
                }
                if (!lifeCreated && Math.random() < 0.06) {
                	level.powerUps.add(new PowerUps.ExtraLifePowerUp(center.getX(), center.getY()));
                    lifeCreated = true;
                }
            }
        }
        // place some unbreakables to make the level interesting
        for (int i = 0; i < 5; i++) {
        	level.unbreakableBricks.add(new BrickUnbreakable(220 + i * 55, 250, 40, 20, 99999));
        }
        return level;
    }

    public static Level createLevel3(Paint primaryColor) {
        Level level = new Level();
        boolean expandCreated = false;
        boolean slowCreated = false;
        boolean lifeCreated = false;

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 9; col++) {
                boolean obstacle = (row == 2 && col % 3 == 0);
                Brick brick;
                
                if (obstacle) {
                	brick = new BrickUnbreakable(60 + col * 55, 80 + row * 25, 50, 20, 99999);
                    level.unbreakableBricks.add((BrickUnbreakable) brick);
                } else {
                	brick = new Brick(60 + col * 55, 80 + row * 25, 50, 20, 3);
                    level.bricks.add(brick);
                }

                Point2D center = brick.getCenter();
                
                if (!obstacle) {
                    if (!expandCreated && Math.random() < 0.15) {
                    	level.powerUps.add(new PowerUps.ExpandPaddlePowerUp(center.getX(), center.getY()));
                        expandCreated = true;
                    }
                    if (!slowCreated && Math.random() < 0.12) {
                    	level.powerUps.add(new PowerUps.SlowBallPowerUp(center.getX(), center.getY()));
                        slowCreated = true;
                    }
                    if (!lifeCreated && Math.random() < 0.12) {
                    	level.powerUps.add(new PowerUps.ExtraLifePowerUp(center.getX(), center.getY()));
                        lifeCreated = true;
                    }
                }
            }
        }
        return level;
    }

}
