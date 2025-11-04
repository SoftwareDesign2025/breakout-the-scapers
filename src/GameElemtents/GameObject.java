package GameElemtents;

import GameUtils.GameColors;
import javafx.scene.Group;
import javafx.scene.Node;
//base class for anything put or updated onscreen. Gives a shared view
//field and a interface for updating objects
import javafx.scene.paint.Color;

public abstract class GameObject {

    // default color for game objects is secondary color
    protected Color color = GameColors.SECONDARY_COLOR.getColor();
    protected Node view; //javafx node representing this object
    protected Group screenItBelongsTo;
    
    public Node getView() {
        return view;
    }

    public abstract void update(double elapsedTime);
    
    public boolean collideWithBall(Ball ball) {
    	// method stub, does nothing by default
    	// override depending on how you want to handle interactions 
    	return false;
    }

    // this method removes the object's view from the screen it belongs to
    // it assumes it has a parent group if screenItBelongsTo is null
    // hence the casting. 
    public void eraseFromScreen() {
        if (screenItBelongsTo != null){
            screenItBelongsTo.getChildren().remove(view);
        }
        else if (view.getParent() != null) {
            ((Group)view.getParent()).getChildren().remove(view);
            screenItBelongsTo = (Group)view.getParent();
        }
    }

    // these methods offset the position of the object by the given delta values in the horizontal direction
    public void offsetPositionHorizontal(double deltaX) {
    	view.setTranslateX(view.getTranslateX() + deltaX);
    }

    // these methods offset the position of the object by the given delta values in the vertical direction
    public void offsetPositionVertival(double deltaY) {
    	view.setTranslateY(view.getTranslateY() + deltaY);
    }

    // Returns the top-left X position of the paddle
    // Overrides GameObject.getX() to account for translations
    public double getX() {
        // Rectangle is at (0,0) in local coordinates
        // Final position = layoutX + translateX + rect.getX()
        // Since rect.getX() = 0, it's: layoutX + translateX
        return view.getLayoutX() + view.getTranslateX();
    }

    // Returns the top-left Y position of the paddle
    // Overrides GameObject.getY() to account for translations
    public double getY() {
        // Rectangle is at (0,0) in local coordinates
        // Final position = layoutY + translateY + rect.getY()
        // Since rect.getY() = 0, it's: layoutY + translateY
        return view.getLayoutY() + view.getTranslateY();
    }
}

