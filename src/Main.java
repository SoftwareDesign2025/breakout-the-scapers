import java.awt.*;

import GameElemtents.Ball;
import GameUtils.GameColors;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

	public static final int SIZE = 400;
	public static final int FRAMES_PER_SECOND = 60;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	public static final String TITLE = "Example JavaFX";
    public static final Paint BACKGROUND = GameColors.BACKGROUND.getColor();

	private Scene myScene;
	private Group root;
	private Ball ball;

	/**
	 * Initialize what will be displayed and how it will be updated.
	 */
	@Override
	public void start(Stage stage) {
		root = new Group();

		// Create the ball and add it to the scene graph
		ball = new Ball(SIZE, SIZE);
		root.getChildren().add(ball.getView());

		// Create scene with root group
		myScene = new Scene(root, SIZE, SIZE, BACKGROUND);

		// Set up and show stage
		stage.setScene(myScene);
		stage.setTitle(TITLE);
		stage.show();

		// Setup animation timeline
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();

		// I do not know what this is but seems important for the future
		myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
	}

	// This method is called every frame to update the ball's position and bounce it
	private void step(double elapsedTime) {
		ball.move(elapsedTime);
		ball.bounce(SIZE, SIZE);
	}

	private void handleKeyInput(KeyCode code) {
		// TODO
	}

	private void handleMouseInput(double x, double y) {
		// TODO
	}

	/**
	 * Start the program.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
