package GameUtils;
// Jose Besednjak
///This class purpose is to give colors to the in game items. Ideally we could just change them 
/// all from here. and we could have like different color schemes. maybe even light and darkmode
/// I have linked the documentation about JavaFx colors and the website I like to use for
/// Colors when I dont know what to pick
import javafx.scene.paint.Color;
// https://colorhunt.co/palette/0046ff73c8d2f5f1dcff9013
// https://docs.oracle.com/javase/8/javafx/api/javafx/scene/paint/Color.html
public enum GameColors {
	FOREGROUND(Color.web("#EF7722")), 
	BACKGROUND(Color.web("#EBEBEB")),
	TEXT_COLOR(Color.web("#EF7722")),
	PRIMARY_COLOR(Color.web("#EF7722")),
	SECONDARY_COLOR(Color.web("#0BA6DF")),
	ACCENT_COLOR(Color.web("#FAA533"));
	
	private final Color color;

    GameColors(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
