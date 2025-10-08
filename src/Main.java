
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
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
    public static final Paint BACKGROUND = Color.BLACK;

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
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);// loop forever
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    //create the scene and connects keyboard input to paddle movement
    private Scene setupScene(int width, int height, Paint background) {
        Group root = myController.createRoot(width, height);
        Scene scene = new Scene(root, width, height, background);

        //for arrow key presses to move the paddle
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return scene;
    }

    private void handleKeyInput(KeyCode code) {
        switch (code) {
            case LEFT -> myController.movePaddle(false);
            case RIGHT -> myController.movePaddle(true);
            default -> {} //ignore other keys
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
