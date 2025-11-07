package GameElemtents;
// Author: Jose Andres Besednjak Izquierdo 
import GameUtils.GameColors;
import java.net.URL;
import javafx.scene.Group;
import javafx.scene.Node;
//base class for anything put or updated onscreen. Gives a shared view
//field and a interface for updating objects
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Bounds;

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
    
    // Returns the top-left X position of the paddle
    public double getX() {
        // Final position = layoutX + translateX
        return view.getLayoutX() + view.getTranslateX();
    }

    // Returns the top-left Y position of the object
    public double getY() {
        // Final position = layoutY + translateY
        return view.getLayoutY() + view.getTranslateY();
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
    	setPositionX(view.getTranslateX() + deltaX);
    }

    // these methods offset the position of the object by the given delta values in the vertical direction
    public void offsetPositionVertival(double deltaY) {
    	setPositionY(view.getTranslateY() + deltaY);
    }
    
    public void setPositionX(double x) {
    	view.setTranslateX(x);
    }

    public void setPositionY(double y) {
    	view.setTranslateY(y);
    }

    // Loads an image from imagePath and replaces the view with an ImageView
    // Uses the view's bounds to determine size, avoiding type-specific casting
    protected void loadImage(String imagePath) {
    this.color = Color.TRANSPARENT; // Color irrelevant when using image

    if (imagePath == null || imagePath.isEmpty()) {
        System.out.println("Image path is null or empty.");
        return;
    }

    try {
        // Try to load from classpath first
        Image image;
        URL resource = getClass().getResource(imagePath);

        if (resource != null) {
            image = new Image(resource.toExternalForm());
        } else {
            // If not found in classpath, try as a file path
            image = new Image("file:" + imagePath);
        }

        // Verify image loaded successfully
        if (image.isError()) {
            System.out.println("Failed to load image: " + imagePath + " (" + image.getException() + ")");
            return;
        }

        // Create ImageView
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);

        // Use view bounds *if valid*, otherwise fallback to image size
        Bounds bounds = view != null ? view.getBoundsInLocal() : null;
        double width = (bounds != null && bounds.getWidth() > 0) ? bounds.getWidth() : image.getWidth();
        double height = (bounds != null && bounds.getHeight() > 0) ? bounds.getHeight() : image.getHeight();

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        // Preserve transforms and layout
        if (view != null) {
            imageView.setTranslateX(view.getTranslateX());
            imageView.setTranslateY(view.getTranslateY());
            imageView.setLayoutX(view.getLayoutX());
            imageView.setLayoutY(view.getLayoutY());
        }

        // Finally, replace the view
        view = imageView;

    } catch (Exception e) {
        System.out.println("Exception loading image: " + imagePath);
        e.printStackTrace();
    }
}

}

