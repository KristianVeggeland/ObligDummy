package com.example.pacmanoblig.Ghosts;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Ghost extends ImageView {

    double x, y;

    public Ghost(double x, double y){
        this.x = x;
        this.y = y;

        setLayoutX(x);
        setLayoutY(y);
    }

    public void setInvulnerability () {

    }

    public void setImageFromPath(String imagePath){
        try {
            FileInputStream invStream = new FileInputStream(imagePath);
            Image invImage = new Image(invStream);
            this.setImage(invImage);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    // random movement + collision...
}
