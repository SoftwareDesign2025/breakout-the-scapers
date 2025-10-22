package GameElemtents;

import java.awt.Paint;

import GameUtils.BreakoutController;
import javafx.scene.Node;
//base class for anything put or updated onscreen. Gives a shared view
//field and a interface for updating objects

public abstract class GameObject {
	//for a brick, it is a rectangle,for a Ball, it is a circle, for 
	//a paddle, this is a rectangle
	//the type is node because node is the superclass for all 
	//javafx objects.
    protected Node view; //javafx node representing this object
    
    public Node getView() {
        return view;
    }

    public abstract void update(double elapsedTime);
    
    public boolean collideWithBall(Ball ball, BreakoutController controller) {
    	// method stub, does nothing by default
    	// override depending on how you want to handle interactions
    	//
    	return false;
    }
    
    public double getX() {
        if (view instanceof javafx.scene.shape.Rectangle rect) {
            return rect.getX();
        } else if (view instanceof javafx.scene.shape.Circle circle) {
            return circle.getCenterX();
        } else {
            return view.getLayoutX(); //for other shapes
        }
    }

    public double getY() {
        if (view instanceof javafx.scene.shape.Rectangle rect) {
            return rect.getY();
        } else if (view instanceof javafx.scene.shape.Circle circle) {
            return circle.getCenterY();
        } else {
            return view.getLayoutY(); //fallback
        }
    }
}

