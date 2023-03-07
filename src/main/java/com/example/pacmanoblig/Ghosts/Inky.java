package com.example.pacmanoblig.Ghosts;

import com.example.pacmanoblig.Direction;

public class Inky extends Ghost{

    public Inky(double x, double y) {
        super(x, y, "src/images/inky.gif");

        Direction[] l = {Direction.UP, Direction.RIGHT, Direction.UP, Direction.LEFT, Direction.DOWN, Direction.LEFT, Direction.UP};
        this.fillPlan(l);
    }
}
