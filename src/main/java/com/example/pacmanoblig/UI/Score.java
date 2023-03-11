package com.example.pacmanoblig.UI;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class Score extends HBox {
    public static int score = 0;
    Label scoreLabel = new Label("Score: ");
    Label scoreTxt = new Label(Integer.toString(score));

    public Score() {
        scoreLabel.setTextFill(Color.WHITE);
        scoreTxt.setTextFill(Color.WHITE);
        this.setPrefHeight(30);
        this.getChildren().add(scoreLabel);
        this.getChildren().add(scoreTxt);
    }

    public void checkScore() {
        scoreTxt.setText(Integer.toString(score));
    }

}
