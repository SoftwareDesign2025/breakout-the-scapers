package GameElemtents;

import java.awt.Paint;

import javafx.scene.Node;
//base class for anything put or updated onscreen. Gives a shared view
//field and a interface for updating objects

public abstract class GameObject {
	//for a brick, it is a rectangle,for a Ball, it is a circle, for 
	//a paddle, this is a rectangle
	private Paint color;
	//the type is node because node is the superclass for all 
	//javafx objects.
    protected Node view; //javafx node representing this object
    
    public Node getView() {
        return view;
    }

    public abstract void update(double elapsedTime);
}
