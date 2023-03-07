package com.example.pacmanoblig.Ghosts;

import com.example.pacmanoblig.Direction;

public class Blinky extends Ghost {

    String imagePath = "src/images/blinky.gif";


    public Blinky(double x, double y) {
        super(x, y);
        Direction[] l = {Direction.LEFT, Direction.RIGHT, Direction.DOWN, Direction.UP};
        setImageFromPath(imagePath);
        this.fillPlan(l);
    }
}
