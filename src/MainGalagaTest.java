import GameUtils.GalagaTestController;
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

/**
 * Main entry point for testing Galaga-related elements.
 * This bypasses the menu system and launches directly into the Galaga test environment.
 */
public class MainGalagaTest extends Application {
    // For animations and window
    private ScreenMaker screenMaker = new ScreenMaker();

    public final int FRAMES_PER_SECOND = 60;
    public final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    
    // For window appearance
    public final String TITLE = "Galaga Test";
    public final Paint BACKGROUND = GameColors.BACKGROUND.getColor();

    // Game state
    private Scene myScene;
    private GalagaTestController myController;

    @Override
    public void start(Stage stage) {
        // Start Galaga test directly
        startGalagaTest(stage);
    }

    // Start Galaga test controller
    private void startGalagaTest(Stage stage) {
        myController = new GalagaTestController();
        myScene = setupScene(screenMaker.SCREENWIDTH, screenMaker.SCREENHEIGHT, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();

        // Game loop
        // KeyFrame calls step() repeatedly at a constant time interval
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                e -> myController.step(SECOND_DELAY));

        Timeline animation = new Timeline();

        animation.setCycleCount(Timeline.INDEFINITE); // loop forever
        animation.getKeyFrames().add(frame);
        animation.play();

        myController.setAnimation(animation);
    }

    // Create the scene and connect keyboard input to paddle movement
    private Scene setupScene(int width, int height, Paint background) {
        Group root = myController.createRoot(width, height);
        Scene scene = new Scene(root, width, height, background);

        // Key press sets movement flags to true
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT -> myController.setMoveLeft(true);
                case RIGHT -> myController.setMoveRight(true);
                case UP -> myController.spawnBall();
                default -> {} // ignore other keys
            }
        });

        // Key release sets movement flags to false
        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT -> myController.setMoveLeft(false);
                case RIGHT -> myController.setMoveRight(false);
                default -> {}
            }
        });

        return scene;
    }

    // Running the program
    public void launchGame(String[] argStrings) {
        launch(argStrings);
    }

    /**
     * Main method to start the Galaga test.
     */
    public static void main(String[] args) {
        MainGalagaTest galagaTest = new MainGalagaTest();
        galagaTest.launchGame(args);
    }
}

