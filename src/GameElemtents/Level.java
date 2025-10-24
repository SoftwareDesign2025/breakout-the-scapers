package GameElemtents;

import java.util.ArrayList;
import java.util.List;


import javafx.scene.text.Text;

// A level represents all the individual blocks for a single screen. 
// it keeps track of the score for itself individually as well as keeping
// track of the type of powerups that can be obtain in the level
public class Level {

    private List<Brick> bricks;

    private int score;
    private Text scoreLabel;
    private int difficulty;

    private List<PowerUps> powerUps = new ArrayList<>();
    
    // a level without any elements will create an empty screen
    public Level() {
    	
    }

    // a level with an integer difficulty will create random levels using the integer as a dificulty scaler
    // the higher the number the harder the level
    public Level(int difficulty) {
    	// TODO: create different levels based on difficulty
    }

    // Util method to create a row of bricks
    private void createRowOfBricks(double yPosition, int numBricks, double brickWidth, double brickHeight, int hp) {
        for (int i = 0; i < numBricks; i++) {
            double xPosition = i * brickWidth;
            Brick brick = new Brick(xPosition, yPosition, brickWidth, brickHeight, hp);
            bricks.add(brick);
        }
    }
	
}
