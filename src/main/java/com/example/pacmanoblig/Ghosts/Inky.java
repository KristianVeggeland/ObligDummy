package com.example.pacmanoblig.Ghosts;

public class Inky extends Ghost{

    String imagePath = "src/images/inky.gif";

    public Inky(double x, double y) {
        super(x, y);
        setImageFromPath(imagePath);
    }
}
