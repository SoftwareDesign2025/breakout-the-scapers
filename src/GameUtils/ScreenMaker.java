package GameUtils;
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
	
	
	public final int SCREENWIDTH = 600;
	public final int SCREENHEIGHT = 800;
	private final ScoreKeeper scoreKeeper = new ScoreKeeper();
	
	private final String WINGAMEFILE = "win_game.fxml";
	private final String LOSEGAMEFILE = "game_over.fxml";
	private final String DISPLAYSCORE = "#scoreLabel";
	private final String DISPLAYFINALSCORE = "Final Score: ";
	private final String DISPLAYHIGHSCORE = "High Score is: ";
	private final String DISPLAYPREVHIGHSCORE = "#prevHigh";
	private final String DISPLAYEXITBUTTON = "#exitButton";
	
	
	
	// checks the high score and updates the end game screen
	private void highScoreCheck(int score, String gameName, Parent root) {
        Label showScore = (Label) root.lookup(DISPLAYSCORE);
        if (showScore != null) {
        	showScore.setText(DISPLAYFINALSCORE + score);
        }
		Label oldScoreLabel = (Label) root.lookup(DISPLAYPREVHIGHSCORE);
		scoreKeeper.checkHighScore(score, gameName);
    	oldScoreLabel.setText(DISPLAYHIGHSCORE + score);
	}
	// sets up the final screen
	private Stage showScreen(Text scoreLabel, Parent root) {
        Stage stage = (Stage) scoreLabel.getScene().getWindow();
        Scene scene = new Scene(root, SCREENWIDTH, SCREENHEIGHT);
        stage.setScene(scene);
        stage.show();
        return stage;
	}
	
	// exits the screen the user was just on
	private void exitGame(Timeline animation) {
        if (animation != null) {
            animation.stop(); // Stop the game loop
        }
	}
	
	
	// makes the stage that shows the player the game has been won 
    public void winGame(Timeline animation, int score, Text scoreLabel, String gameName) {
    	
        try {
            exitGame(animation);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(WINGAMEFILE));
            Parent root = loader.load();

            highScoreCheck(score, gameName, root);
            Stage stage = showScreen(scoreLabel, root);
            
    
            Button exitButton = (Button) root.lookup(DISPLAYEXITBUTTON);
            exitButton.setOnAction(e -> stage.close());

        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }
    
    
    
    
    
    // when lives tick to 0 cut the game and tell the player game over in a new window
    public void endGame(Timeline animation, int score, Text scoreLabel, String gameName) {
        try {
            exitGame(animation);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(LOSEGAMEFILE));
            Parent root = loader.load();
            
            highScoreCheck(score, gameName, root);
            Stage stage = showScreen(scoreLabel, root);

            Button exitButton = (Button) root.lookup(DISPLAYEXITBUTTON);
            exitButton.setOnAction(e -> stage.close());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
   
    
    
    
    
    
    
}
