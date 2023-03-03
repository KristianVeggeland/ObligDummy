package GameObjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall extends Rectangle {
    static final Color c = Color.DARKBLUE;

    public Wall(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
        this.setFill(c);
    }
}
