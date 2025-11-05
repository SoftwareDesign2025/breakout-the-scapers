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
	
	
	
	private void screenSetter(int score, Text scoreLabel, String gameName, Timeline animation) {
		try {
            if (animation != null) {
                animation.stop(); // Stop the game loop
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(WINGAMEFILE));
            Parent root = loader.load();
            // set current score
            Label showScore = (Label) root.lookup(DISPLAYSCORE);
            if (showScore != null) {
            	showScore.setText(DISPLAYFINALSCORE + score);
            }
            
            
            Label oldScoreLabel = (Label) root.lookup(DISPLAYPREVHIGHSCORE);
            

        
            scoreKeeper.checkHighScore(score, gameName);
        	oldScoreLabel.setText(DISPLAYHIGHSCORE + score);
            
       
            
            Stage stage = (Stage) scoreLabel.getScene().getWindow();
            Scene scene = new Scene(root, SCREENWIDTH, SCREENHEIGHT);
            stage.setScene(scene);
            stage.show();
	       } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
            
       
        
	}
	
    // makes the stage that shows the player the game has been won 
    public void win_game(Timeline animation, int score, Text scoreLabel, String gameName) {
    	
        try {
            if (animation != null) {
                animation.stop(); // Stop the game loop
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(WINGAMEFILE));
            Parent root = loader.load();
            // set current score
            Label showScore = (Label) root.lookup(DISPLAYSCORE);
            if (showScore != null) {
            	showScore.setText(DISPLAYFINALSCORE + score);
            }
            
            
            Label oldScoreLabel = (Label) root.lookup(DISPLAYPREVHIGHSCORE);
            

        
            scoreKeeper.checkHighScore(score, gameName);
        	oldScoreLabel.setText(DISPLAYHIGHSCORE + score);
            
            
            
            Stage stage = (Stage) scoreLabel.getScene().getWindow();
            Scene scene = new Scene(root, SCREENWIDTH, SCREENHEIGHT);
            stage.setScene(scene);
            stage.show();
            
            
            
//            System.out.println(scoreKeeper.readLastNumberFromFile(gameName));
            
            Button exitButton = (Button) root.lookup(DISPLAYEXITBUTTON);
            exitButton.setOnAction(e -> stage.close());

        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }
    
    // when lives tick to 0 cut the game and tell the player game over in a new window
    public void endGame(Timeline animation, int score, Text scoreLabel, String gameName) {
        try {
            if (animation != null) {
                animation.stop(); // Stop the game loop
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(LOSEGAMEFILE));
            Parent root = loader.load();
            
            Label showScore = (Label) root.lookup(DISPLAYSCORE);
            if (showScore != null) {
            	showScore.setText(DISPLAYFINALSCORE + score);
            }
            
            
            Label oldScoreLabel = (Label) root.lookup(DISPLAYPREVHIGHSCORE);
            
            
//            System.out.println(scoreKeeper.readLastNumberFromFile(gameName));
//            System.out.println(score);
            
            scoreKeeper.checkHighScore(score,gameName);
        	oldScoreLabel.setText(DISPLAYHIGHSCORE + scoreKeeper.readLastNumberFromFile(gameName));
      
            
            
            Stage stage = (Stage) scoreLabel.getScene().getWindow();
            Scene scene = new Scene(root, SCREENWIDTH, SCREENHEIGHT);
            stage.setScene(scene);
            stage.show();

            Button exitButton = (Button) root.lookup(DISPLAYEXITBUTTON);
            exitButton.setOnAction(e -> stage.close());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
   
    
    
    
    
    
    
}
