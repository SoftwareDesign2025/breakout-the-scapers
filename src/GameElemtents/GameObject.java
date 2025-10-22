package GameElemtents;

import GameUtils.GameColors;
import javafx.scene.Node;
//base class for anything put or updated onscreen. Gives a shared view
//field and a interface for updating objects
import javafx.scene.paint.Color;

public abstract class GameObject {
	//for a brick, it is a rectangle,for a Ball, it is a circle, for 
	//a paddle, this is a rectangle
	//the type is node because node is the superclass for all 
	//javafx objects.
    protected Color color = GameColors.SECONDARY_COLOR.getColor();
    protected Node view; //javafx node representing this object
    
    public Node getView() {
        return view;
    }

    public abstract void update(double elapsedTime);
    
    public boolean collideWithBall(Ball ball) {
    	// method stub, does nothing by default
    	// override depending on how you want to handle interactions 
    	return false;
    }

}

