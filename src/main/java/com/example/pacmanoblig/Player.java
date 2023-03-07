package com.example.pacmanoblig;


import com.example.pacmanoblig.GameObjects.Dot;
import com.example.pacmanoblig.GameObjects.Tablet;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player extends Circle {

    // Enum that controls direction of player object.
    enum Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    // Variable that take all the cells of the map and stores them.
    int[][] cells = GameMap.getCells();
    Direction inputDirection;
    boolean moveDown, moveUp, moveLeft, moveRight = true;

    // Attributes that are used to show pacman location.
    double x,y;
    // Attributes determining the player's velocity.
    private double vx, vy;
    private final double speed = 2;
    double moveCounter = 0;

    // Class constructor.
    public Player(double x, double y) {
        this.x = x;
        this.y = y;

        setLayoutX(x);
        setLayoutY(y);
        setRadius(16);
        setFill(Color.YELLOW);
    }

    // Method that is keeps track of the player.
    public void update() {
        checkDirection();
        checkCollision();

        setLayoutY(getLayoutY() - vy);
        setLayoutX(getLayoutX() + vx);
    }

    public void handleKeyEvent(KeyEvent e) {
        if (e.getCode() == KeyCode.W) {
            inputDirection = Direction.UP;
        }

        if (e.getCode() == KeyCode.A) {
            inputDirection = Direction.LEFT;
        }

        if (e.getCode() == KeyCode.S) {
            inputDirection = Direction.DOWN;
        }

        if (e.getCode() == KeyCode.D) {
            inputDirection = Direction.RIGHT;
        }
    }

    // Method that checks the players direction and controls adjusts the velocity.
    public void checkDirection() {

        int row = (int) (getLayoutY()/ 32);
        int col = (int) (getLayoutX()/ 32);

        moveUp = cells[row-1][col]!= 35;
        moveDown = cells[row+1][col] != 35;
        moveLeft = cells[row][col-1] != 35;
        moveRight = cells[row][col+1] != 35;

        if (getLayoutX() - getRadius() == col * 32) {
            if (inputDirection == Direction.UP && moveUp) {
                vx = 0;
                vy = speed;
                moveCounter = 0;
            }
            if (inputDirection == Direction.DOWN && moveDown) {
                vx = 0;
                vy = -speed;
                moveCounter = 0;
            }

            if (inputDirection == Direction.UP && !moveUp) {
                moveCounter++;
                if (moveCounter == 16/speed) {
                    vx = 0;
                    vy = 0;
                }
            }
            if (inputDirection == Direction.DOWN && !moveDown) {
                moveCounter++;
                if (moveCounter == (16/speed)+1) {
                    vx = 0;
                    vy = 0;
                }
            }
        }

        if (getLayoutY() - getRadius() == row * 32) {
            if (inputDirection == Direction.RIGHT && moveRight) {
                vx = speed;
                vy = 0;
                moveCounter = 0;
            }
            if (inputDirection == Direction.LEFT && moveLeft) {
                vx = -speed;
                vy = 0;
                moveCounter = 0;
            }

            if (inputDirection == Direction.LEFT && !moveLeft) {
                moveCounter++;
                if (moveCounter == 16/speed) {
                    vx = 0;
                    vy = 0;
                }
            }
            if (inputDirection == Direction.RIGHT && !moveRight) {
                moveCounter++;
                if (moveCounter == (16/speed)+1) {
                    vx = 0;
                    vy = 0;
                }
            }
        }
    }

    public void checkCollision() {

        Group g = (Group) this.getParent();
        ArrayList<Shape> listOfObjects = new ArrayList<>();
        Node helper;

        for (int i = 0; i < g.getChildren().size(); i++) {
            helper = g.getChildren().get(i);
            if (helper instanceof Dot || helper instanceof Tablet) {
                listOfObjects.add((Shape) helper);
            }
        }

        for (Shape n: listOfObjects) {
            Shape intersects = Shape.intersect(this, n);

            if (intersects.getBoundsInLocal().getWidth() != -1) {
                if (n instanceof Dot) {
                    g.getChildren().remove(n);
                }
                if (n instanceof Tablet) {
                    g.getChildren().remove(n);
                }
            }
        }




    }
}







