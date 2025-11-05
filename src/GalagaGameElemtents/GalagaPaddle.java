package GalagaGameElemtents;
// Author: Jose Andres Besednjak Izquierdo
import GameElemtents.Paddle;
import javafx.scene.Group;

public class GalagaPaddle extends Paddle {

	public GalagaPaddle(double x, double y, double width, double height) {
		super(x, y, width, height);
		imagePath = "/breakout-the-scapers/src/Pictures/SpaceShips/Ship_1.png";
		loadImage();
		this.speed = 200;
		
	}
	
	public GalagaPaddle(double x, double y, double width, double height, Group group) {
		this(x, y, width, height);
		this.screenItBelongsTo = group;
	}

}
