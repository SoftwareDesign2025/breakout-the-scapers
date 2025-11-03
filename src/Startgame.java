import GameUtils.BreakoutController;
import GameUtils.GameColors;
import GameUtils.ScreenMaker;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Startgame extends Application{
	//for animations and window
	
		private ScreenMaker screenMaker = new ScreenMaker();

		public  final int FRAMES_PER_SECOND = 60;
		public  final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
		public  final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	    //for window appearance
		public  final String TITLE = "Breakout Game";
		public  final Paint BACKGROUND = GameColors.BACKGROUND.getColor();
	    
		public  final int SIZE = 400;

	    //game state
	    private Scene myScene;
	    private BreakoutController myController;

	    @Override
	    public void start(Stage stage) {
	        myController = new BreakoutController(); //
	        myScene = setupScene(screenMaker.SCREENWIDTH, screenMaker.SCREENHEIGHT, BACKGROUND);
	        stage.setScene(myScene);
	        stage.setTitle(TITLE); 
	        stage.show();
	 
	        //game loop
	        //KeyFrame calls step() repeatedly at a constant time interval
	        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
	                e -> myController.step(SECOND_DELAY));
	        
	        Timeline animation = new Timeline(); 
	        
	        animation.setCycleCount(Timeline.INDEFINITE);// loop forever
	        animation.getKeyFrames().add(frame);
	        animation.play();
	        
	        myController.setAnimation(animation);
	    }

	    //create the scene and connects keyboard input to paddle movement
	    private Scene setupScene(int width, int height, Paint background) {
	        Group root = myController.createRoot(width, height);
	        Scene scene = new Scene(root, width, height, background);

	        // key press sets movement flags to true
	        scene.setOnKeyPressed(e -> {
	            switch (e.getCode()) {
	                case LEFT -> myController.setMoveLeft(true);
	                case RIGHT -> myController.setMoveRight(true);
	                default -> {} // ignore other keys
	            }
	        });

	        // key release sets movement flags to false
	        scene.setOnKeyReleased(e -> {
	            switch (e.getCode()) {
	                case LEFT -> myController.setMoveLeft(false);
	                case RIGHT -> myController.setMoveRight(false);
	                default -> {}
	            }
	        });

	        return scene;
	    }
	    
	    
	    // running the program
	    public void launch_game(String[] argStrings) {
	    	launch(argStrings);
	    }
}
