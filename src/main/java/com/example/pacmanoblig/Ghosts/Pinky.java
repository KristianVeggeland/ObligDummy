package com.example.pacmanoblig.Ghosts;

import com.example.pacmanoblig.Direction;

public class Pinky extends Ghost{

    String imagePath = "src/images/pinky.gif";

    public Pinky(double x, double y) {
        super(x, y);
        setImageFromPath(imagePath);
        Direction[] l = {Direction.LEFT, Direction.RIGHT, Direction.DOWN, Direction.UP};
        this.fillPlan(l);
    }
}
