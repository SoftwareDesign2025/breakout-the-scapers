package GameUtils;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
// This class is in charge of making the screens that will be used in the game
public class ScreenMaker {
	
	
	public static final int SCREENWIDTH = 600;
	public static final int SCREENHEIGHT = 800;
	
	
    // makes the stage that shows the player the game has been won 
    public void win_game(Timeline animation, int score, ScoreKeeper scoreKeeper, Text scoreLabel) {
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
            Scene scene = new Scene(root, SCREENWIDTH, SCREENHEIGHT);
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
    public void endGame(Timeline animation, int score, ScoreKeeper scoreKeeper, Text scoreLabel) {
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
            Scene scene = new Scene(root, SCREENWIDTH, SCREENHEIGHT);
            stage.setScene(scene);
            stage.show();

            Button exitButton = (Button) root.lookup("#exitButton");
            exitButton.setOnAction(e -> stage.close());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
   
    
    
    
    
    
    
}
