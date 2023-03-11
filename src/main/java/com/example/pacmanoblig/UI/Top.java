package com.example.pacmanoblig.UI;

import javafx.scene.layout.BorderPane;

public class Top extends BorderPane {

    static int lvl = 1;


    Lives l = new Lives();

    Score s = new Score();
    public Top() {
        this.setWidth(300);
        this.setHeight(150);
        this.setStyle("-fx-font-family: Courier; -fx-font-size: 15pt");
        this.setRight(l);
        this.setLeft(s);

    }

    public Lives getL() {
        return l;
    }

    public Score getS() {
        return s;
    }
}
