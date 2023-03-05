package com.example.pacmanoblig;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import static javafx.geometry.VPos.*;

public class Top extends BorderPane {

    static int lvl = 1;


    Lives l = new Lives();

    Score s = new Score();
    public Top() {
        this.setWidth(300);
        this.setHeight(150);
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
