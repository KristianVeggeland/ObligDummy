package com.example.pacmanoblig.GameObjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

public class PacManArc extends Arc {

    static PacManArc instance;

    public PacManArc(){
        setRadiusX(16);
        setRadiusY(16);
        setStartAngle(-135);
        setLength(270);
        setFill(Color.YELLOW);
        setType(ArcType.ROUND);
        instance = this;
    }

    public static PacManArc getInstance(){
        return instance;
    }
}
