package com.example.pacmanoblig.Ghosts;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Ghost extends ImageView {

    double x, y;
    private static boolean inBlueMode;
    private Image originalImage;
    private Timer blueTimer;
    private Timer transitionTimer;

    private static List<Ghost> instances = new ArrayList<>();

    public Ghost(double x, double y){
        this.x = x;
        this.y = y;

        setLayoutX(x);
        setLayoutY(y);

        instances.add(this);
    }

    public void blueMode () {

        if (originalImage == null) {
            originalImage = getImage();
        }

        setImageFromPath("src/images/bluemode.gif");

        inBlueMode = true; // legges til for å senere sjekke om pacman kan spise den på collision

        if (blueTimer != null) {
            blueTimer.cancel();
        }
        if (transitionTimer != null) {
            transitionTimer.cancel();
        }

        blueTimer = new Timer();
        transitionTimer = new Timer();
        blueTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                setImageFromPath("src/images/transition.gif");

                transitionTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        setImage(originalImage);
                        inBlueMode = false;
                    }
                }, 3500);
            }
        }, 5000);
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


    public static List<Ghost> getAllInstances(){
        return instances;
    }

    public static boolean isBlueMode(){
        return inBlueMode;
    }


}
