

import GameUtils.BreakoutController;
import GameUtils.GameColors;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main {
	
	/**
	 * Start the program.
	 */
	public static void main(String[] args) {
		Startgame gaming = new Startgame();
		gaming.launch_game(args);
	}
}
