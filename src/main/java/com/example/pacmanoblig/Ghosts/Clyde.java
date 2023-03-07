package com.example.pacmanoblig.Ghosts;


import com.example.pacmanoblig.Direction;

public class Clyde extends Ghost {

    String imagePath = "src/images/clyde.gif";

    public Clyde(double x, double y) {
        super(x, y);
        setImageFromPath(imagePath);
        Direction[] l = {Direction.LEFT, Direction.RIGHT, Direction.DOWN, Direction.UP};
        this.fillPlan(l);
    }
}
