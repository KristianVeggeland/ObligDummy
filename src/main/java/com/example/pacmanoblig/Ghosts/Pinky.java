package com.example.pacmanoblig.Ghosts;

import com.example.pacmanoblig.Direction;

public class Pinky extends Ghost{



    public Pinky(double x, double y) {
        super(x, y, "src/images/pinky.gif");
        Direction[] l = {Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN};
        this.fillPlan(l);
    }
}
