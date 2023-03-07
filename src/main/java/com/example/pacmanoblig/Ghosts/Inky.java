package com.example.pacmanoblig.Ghosts;

import com.example.pacmanoblig.Direction;

public class Inky extends Ghost{

    String imagePath = "src/images/inky.gif";

    public Inky(double x, double y) {
        super(x, y);
        setImageFromPath(imagePath);
        Direction[] l = {Direction.LEFT, Direction.RIGHT, Direction.DOWN, Direction.UP};
        this.fillPlan(l);
    }
}
