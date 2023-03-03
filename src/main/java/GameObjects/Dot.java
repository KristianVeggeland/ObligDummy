package GameObjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Dot extends Circle {
    double x;
    double y;

    static final double r = 3;
    final Color c = Color.WHITE;
    public Dot(double x, double y) {
        super(x, y, r);
        this.setFill(c);
    }

}
