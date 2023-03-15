package com.example.pacmanoblig.GameObjects;

// Imports.
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall extends Rectangle {

    // Cass variable.
    static final Color c = Color.DARKBLUE;

    // Constructor.
    public Wall(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
        this.setFill(c);
    }
}
