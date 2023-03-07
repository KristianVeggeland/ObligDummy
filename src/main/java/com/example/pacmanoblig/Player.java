package com.example.pacmanoblig;


import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Arrays;

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
        Group g = (Group) this.getParent();

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
}







