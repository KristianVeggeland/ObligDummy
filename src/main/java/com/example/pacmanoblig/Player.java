package com.example.pacmanoblig;


import GameObjects.Dot;
import GameObjects.Tablet;
import GameObjects.Wall;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

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
        if (!checkCollision()) {
        }

    }

    public void keyPressed(KeyEvent e) {
        if (e.getText().equals("w")){
            upM();

        }
        if (e.getText().equals("s")){
            downM();
        }
        if (e.getText().equals("d")){
          rightM();
        }

        if (e.getText().equals("a")){
            leftM();
        }
    }

    public void upM() {
        moveDown = moveLeft = moveRight = false;
        moveUp = true;
    }

    public void downM() {
        moveRight = moveUp = moveLeft = false;
        moveDown = true;
    }

    public void rightM() {
        moveDown = moveUp = moveLeft = false;
        moveRight = true;
    }

    public void leftM() {
        moveDown = moveUp = moveRight = false;
        moveLeft = true;
    }
    

    public boolean checkCollision() {
        Group g = (Group) this.getParent();
        Node helper;
        double distance = 0;
        ArrayList<Shape> listOfObjects = new ArrayList<>();
        for (int i = 0; i < g.getChildren().size(); i++) {
            helper = g.getChildren().get(i);
            if (helper instanceof Dot
                    || helper instanceof Tablet
                    || helper instanceof Wall) {
                listOfObjects.add((Shape) helper);
            }
        }

        for (Shape n: listOfObjects) {
            Shape intersects = Shape.intersect(this, n);

            if (intersects.getBoundsInLocal().getWidth() != -1) {
                if (n instanceof Dot) {
                    System.out.println("o");
                    g.getChildren().remove(n);
                }

                if (n instanceof Tablet) {
                    System.out.println("*");
                    g.getChildren().remove(n);
                }

                if (n instanceof Wall) {
                    System.out.println();
                    if (moveUp) {
                       moveUp = false;
                       setLayoutY(getLayoutY() + speed);
                    }

                    if (moveDown) {
                        moveDown = false;
                        setLayoutY(getLayoutY() - speed);
                    }

                    if (moveRight) {
                        moveRight = false;
                        setLayoutX(getLayoutX() - speed);
                    }

                    if (moveLeft) {
                        moveLeft = false;
                        setLayoutX(getLayoutX() + speed);
                    }
                }

                return false;
            }
        }
        return true;
    }



}
