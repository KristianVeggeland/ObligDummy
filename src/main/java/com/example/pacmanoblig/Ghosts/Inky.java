package com.example.pacmanoblig.Ghosts;

import com.example.pacmanoblig.Direction;

public class Inky extends Ghost{

    public Inky(double x, double y) {
        super(x, y, "src/images/inky.gif");
        setImageFromPath(imagePath);
        Direction[] l = {Direction.RIGHT, Direction.LEFT, Direction.RIGHT, Direction.LEFT, Direction.RIGHT, Direction.LEFT, };
        this.fillPlan(l);
    }
}
