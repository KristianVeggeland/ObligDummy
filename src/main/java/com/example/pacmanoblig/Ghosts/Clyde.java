package com.example.pacmanoblig.Ghosts;


public class Clyde extends Ghost {

    String imagePath = "src/images/clyde.gif";

    public Clyde(double x, double y) {
        super(x, y);
        setImageFromPath(imagePath);
    }
}
