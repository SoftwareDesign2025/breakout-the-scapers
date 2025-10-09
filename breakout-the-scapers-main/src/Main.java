
import GameUtils.BreakoutController;
import GameUtils.GameColors;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application{
	//for animations and window
	public static final int WIDTH = 600;
	public static final int HEIGHT = 800;
	public static final int FRAMES_PER_SECOND = 60;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    //for window appearance
	public static final String TITLE = "Breakout Game";
	public static final Paint BACKGROUND = GameColors.BACKGROUND.getColor();
    
	public static final int SIZE = 400;

    //game state
    private Scene myScene;
    private BreakoutController myController;

    @Override
    public void start(Stage stage) {
        myController = new BreakoutController();
        myScene = setupScene(WIDTH, HEIGHT, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        
 
        //game loop
        //KeyFrame calls step() repeatedly at a constant time interval
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                e -> myController.step(SECOND_DELAY));
        
        Timeline animation = new Timeline(); // does not like it when the game ends ask profs about
        
        animation.setCycleCount(Timeline.INDEFINITE);// loop forever
        animation.getKeyFrames().add(frame);
        animation.play();
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


	/**
	 * Start the program.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
