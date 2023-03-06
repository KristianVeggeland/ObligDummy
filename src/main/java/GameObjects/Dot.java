package GameObjects;

//Imports
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Dot extends Circle {
    static final double r = 3;
    static final Color c = Color.WHITE;
    public Dot(double x, double y) {
        super(x, y, r);
        this.setFill(c);
    }

}
