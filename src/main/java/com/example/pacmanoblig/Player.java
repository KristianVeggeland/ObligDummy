package com.example.pacmanoblig;


import com.example.pacmanoblig.GameObjects.Dot;
import com.example.pacmanoblig.GameObjects.Tablet;
import com.example.pacmanoblig.GameObjects.Wall;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Player extends Circle {

    // Enum that controls direction of player object.
    enum Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT,
        NONE
    }

    // Variable that take all the cells of the map and stores them.
    int[][] cells = GameMap.getCells();
    Direction pacmanDirection, inputDirection = Direction.NONE;
    boolean startDirection, moveDown, moveUp, moveLeft, moveRight = true;

    boolean isMoving = false;

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
        startDirection = true;
    }

    // Method that is keeps track of the player.
    public void update() {
        checkCollision();
        checkDirection();

        setLayoutY(getLayoutY() - vy);
        setLayoutX(getLayoutX() + vx);
    }

    public void handleKeyEvent(KeyEvent e) {
        if (e.getCode() == KeyCode.W) {
            inputDirection = Direction.UP;
            isMoving = true;
        }

        else if (e.getCode() == KeyCode.A) {
            inputDirection = Direction.LEFT;
            isMoving = true;

        }

        else if (e.getCode() == KeyCode.S) {
            inputDirection = Direction.DOWN;
            isMoving = true;

        }

        else if (e.getCode() == KeyCode.D) {
            inputDirection = Direction.RIGHT;
            isMoving = true;

        }
    }

    // Method that checks the players direction and controls adjusts the velocity.
    public void checkDirection() {

        if (startDirection) {
            inputDirection = Direction.LEFT;
            pacmanDirection = Direction.LEFT;
            isMoving = true;
            startDirection = false;
        }

        int row = (int) (getLayoutY()/ 32);
        int col = (int) (getLayoutX()/ 32);

        moveUp = cells[row-1][col]!= 35;
        moveDown = cells[row+1][col] != 35;
        moveLeft = cells[row][col-1] != 35;
        moveRight = cells[row][col+1] != 35;

        if (getLayoutX() - getRadius() == col * 32) {
            if (inputDirection == Direction.UP && moveUp) {
                pacmanDirection = inputDirection;
                vx = 0;
                vy = speed;
                moveCounter = 0;
            }
            else if (inputDirection == Direction.DOWN && moveDown) {
                pacmanDirection = inputDirection;
                vx = 0;
                vy = -speed;
                moveCounter = 0;
            }

            if (inputDirection == Direction.UP && !moveUp) {
                moveCounter++;
                if (moveCounter == 16/speed) {
                    stopMoving();
                }
            }
            else if (inputDirection == Direction.DOWN && !moveDown) {
                moveCounter++;
                if (moveCounter == (16/speed)+1) {
                    stopMoving();
                }
            }
        }

        if (getLayoutY() - getRadius() == row * 32) {
            if (inputDirection == Direction.RIGHT && moveRight) {
                pacmanDirection = inputDirection;
                vx = speed;
                vy = 0;
                moveCounter = 0;
            }
            else if (inputDirection == Direction.LEFT && moveLeft) {
                pacmanDirection = inputDirection;
                vx = -speed;
                vy = 0;
                moveCounter = 0;
            }

            if (inputDirection == Direction.LEFT && !moveLeft) {
                moveCounter++;
                if (moveCounter == 16/speed) {
                    stopMoving();
                }
            }
            else if (inputDirection == Direction.RIGHT && !moveRight) {
                moveCounter++;
                if (moveCounter == (16/speed)+1) {
                    stopMoving();
                }
            }
        }
    }

    private void stopMoving() {
        vx = 0;
        vy = 0;
        isMoving = false;
    }

    public void checkCollision() {

        Group g = (Group) this.getParent();
        ArrayList<Shape> listOfObjects = new ArrayList<>();
        Node helper;

        for (int i = 0; i < g.getChildren().size(); i++) {
            helper = g.getChildren().get(i);
            if (helper instanceof Dot || helper instanceof Tablet || helper instanceof Wall)  {
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

                if (n instanceof Wall) {
                    if (!moveLeft && pacmanDirection == Direction.UP) {
                        stopMoving();
                        setLayoutY(getLayoutY()+1);
                    }
                    if (!moveRight && pacmanDirection == Direction.UP) {
                        stopMoving();
                        setLayoutY(getLayoutY()+1);
                    }
                    if (!moveLeft && pacmanDirection == Direction.DOWN) {
                        stopMoving();
                        setLayoutY(getLayoutY()-1);
                    }
                    if (!moveRight && pacmanDirection == Direction.DOWN) {
                        stopMoving();
                        setLayoutY(getLayoutY()-1);
                    }
                    if (!moveUp && pacmanDirection == Direction.RIGHT) {
                        stopMoving();
                        setLayoutX(getLayoutX()-1);
                    }
                    if (!moveDown && pacmanDirection == Direction.RIGHT) {
                        stopMoving();
                        setLayoutX(getLayoutX()-1);
                    }
                    if (!moveUp && pacmanDirection == Direction.LEFT) {
                        stopMoving();
                        setLayoutX(getLayoutX()+1);
                    }
                    if (!moveDown && pacmanDirection == Direction.LEFT) {
                        stopMoving();
                        setLayoutX(getLayoutX()+1);
                    }
                }
            }
        }
    }
}







