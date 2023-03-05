package com.example.pacmanoblig;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Lives extends HBox {
    static int lives = 3;

    Label livesLabel = new Label("Lives: ");
    Label livesTxt = new Label(Integer.toString(lives));

    public Lives() {
        this.getChildren().add(livesLabel);
        this.getChildren().add(livesTxt);
    }

    public void checkLivesLeft() {

    }

}
