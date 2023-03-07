package com.example.pacmanoblig;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Score extends HBox {
    static int score = 0;
    Label scoreLabel = new Label("Score: ");
    Label scoreTxt = new Label(Integer.toString(score));

    public Score() {
        this.getChildren().add(scoreLabel);
        this.getChildren().add(scoreTxt);
    }

    public void checkScore() {
        scoreTxt.setText(Integer.toString(score));
    }

}
