package com.example.pacmanoblig.UI;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class Lives extends HBox {
    public static int lives = 3;

    Label livesLabel = new Label("Lives: ");
    Label livesTxt = new Label(Integer.toString(lives));

    public Lives() {
        this.setPrefHeight(30);
        livesLabel.setTextFill(Color.WHITE);
        livesTxt.setTextFill(Color.WHITE);
        this.getChildren().add(livesLabel);
        this.getChildren().add(livesTxt);
    }

    public void checkLivesLeft() {
        livesTxt.setText(Integer.toString(lives));
    }

}
