package GameObjects;

//Imports
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Tablet extends Circle {

    static final double r = 6;
    final Color c = Color.WHITE;
    public Tablet(double x, double y) {
        super(x, y, r);
        this.setFill(c);
    }
}
