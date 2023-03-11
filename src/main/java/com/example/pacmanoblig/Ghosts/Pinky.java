package com.example.pacmanoblig.Ghosts;

import com.example.pacmanoblig.Direction;

public class Pinky extends Ghost{

    public Pinky(double x, double y) {
        super(x, y, "src/images/pinky.gif");
        setImageFromPath(imagePath);
        Direction[] l = {Direction.RIGHT, Direction.LEFT, Direction.RIGHT, Direction.LEFT, Direction.RIGHT, Direction.LEFT, Direction.UP, Direction.LEFT, Direction.DOWN, Direction.LEFT};
        this.fillPlan(l);
    }
}
