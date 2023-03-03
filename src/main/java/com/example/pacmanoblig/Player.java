package com.example.pacmanoblig;


import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Player extends Circle {
    double x, y;

    double speed = 0.6;

    boolean moveLeft, moveRight, moveUp, moveDown;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;

        setLayoutX(x);
        setLayoutY(y);
        setFill(Color.YELLOW);
        setRadius(14);

    }

    public void update(){

        if (moveUp) {
            setLayoutY(getLayoutY() - speed);
        }

        if (moveDown) {
            setLayoutY(getLayoutY() + speed);
        }

        if (moveRight) {
            setLayoutX(getLayoutX() + speed);
        }
        if (moveLeft) {
            setLayoutX(getLayoutX() - speed);
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getText().equals("w")){
            moveDown = moveLeft = moveRight = false;
            moveUp = true;

        }
        if (e.getText().equals("s")){
            moveLeft = moveUp = moveRight = false;
            moveDown = true;

        }
        if (e.getText().equals("d")){
            moveDown = moveUp = moveLeft = false;
            moveRight = true;

        }

        if (e.getText().equals("a")){
            moveDown = moveUp = moveRight = false;
            moveLeft = true;
        }
    }

}
