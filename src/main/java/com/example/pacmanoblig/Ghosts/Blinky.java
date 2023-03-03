package com.example.pacmanoblig.Ghosts;

public class Blinky extends Ghost {

    String imagePath = "src/images/blinky.gif";

    public Blinky(double x, double y) {
        super(x, y);
        setImageFromPath(imagePath);
    }
}
