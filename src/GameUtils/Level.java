package GameUtils;

import java.util.ArrayList;
import java.util.List;

import GameElemtents.Brick;
import GameElemtents.BrickUnbreakable;
import GameElemtents.PowerUps;
import javafx.scene.Group;
import javafx.scene.text.Text;

public class Level {

    private int score;
    private Text scoreLabel;
    private int difficulty;
	public final List<Brick> bricks = new ArrayList<>();
    public final List<BrickUnbreakable> unbreakableBricks = new ArrayList<>(); 
    public final List<PowerUps> powerUps = new ArrayList<>();
    
    public void populate(Group root, List<Brick> outBricks, List<Brick> outUnbreakableBricks, List<PowerUps> outPowerUps) {
        //add brick nodes and track them
        for (Brick b : bricks) {
            root.getChildren().add(b.getView());
            outBricks.add(b);
        }
        for (Brick b : unbreakableBricks) {
            root.getChildren().add(b.getView());
            outUnbreakableBricks.add(b);
        }
        for (PowerUps p : powerUps) {
            root.getChildren().add(p.getView());
            outPowerUps.add(p);
        }
    }


    // a level with an integer difficulty will create random levels using the integer as a dificulty scaler


    // util method to create a row of bricks
    private void createRowOfBricks(double yPosition, int numBricks, double brickWidth, double brickHeight, int hp) {
        for (int i = 0; i < numBricks; i++) {
            double xPosition = i * brickWidth;
            Brick brick = new Brick(xPosition, yPosition, brickWidth, brickHeight, hp);
            bricks.add(brick);
        }
    }

    
    private void brickMakerUbreakable(int yaxis, Group root) {
        for (int i = 0; i < 10; i++) {
            Brick brick = new BrickUnbreakable(50 + i * 50, yaxis, 40, 20, 3);
            bricks.add(brick);
            //add visual node to scene
            root.getChildren().add(brick.getView());
        }
    }
    
    public  void updateAll(double elapsedTime) {
    	
    }
	
}
