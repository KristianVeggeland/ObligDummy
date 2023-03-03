package com.example.pacmanoblig.Ghosts;

public class Pinky extends Ghost{

    String imagePath = "src/images/pinky.gif";

    public Pinky(double x, double y) {
        super(x, y);
        setImageFromPath(imagePath);
    }
}
