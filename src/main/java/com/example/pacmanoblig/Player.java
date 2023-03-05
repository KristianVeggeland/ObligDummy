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

    // Directions that store which way pacman is going and the last direction the player gave regardless if it was valid.
    Direction pacmanDirection, inputDirection;

    boolean moveDown, moveUp, moveLeft, moveRight;

    // Attributes that are used to show pacmans location.
    double x,y;
    // Attributes determening the players velocity.
    private double vx, vy;
    private final double speed = 1;

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


        Circle testCircle = new Circle(col * 32 + 32 / 2, row* 32 + 32 / 2, 3, Color.RED);
        g.getChildren().add(testCircle );




        if (cells[row+1][col] == 35) {
            System.out.println("1");
            moveDown = false;
        }

        if (cells[row-1][col] == 35) {
            System.out.println("1");
            moveUp = false;
        }

        if (cells[row][col-1] == 35) {
            System.out.println("1");
            moveLeft = false;
        }

        if (cells[row][col+1] == 35) {
            System.out.println("1");
            moveRight = false;
        }


        if (inputDirection == Direction.RIGHT && cells[row][(int)((getLayoutX() - 16)/32)+1] != 35) {
            vx = speed;
            vy = 0;
        } else if (inputDirection == Direction.LEFT && cells[row][(int)((getLayoutX() + 16)/32)-1] != 35) {
            vx = -speed;
            vy = 0;
        } else if (inputDirection == Direction.UP && cells[(int)((getLayoutY() + 16)/32)-1][col] != 35) {
            vx = 0;
            vy = speed;
        } else if (inputDirection == Direction.DOWN && cells[(int)((getLayoutY() - 16)/32)+1][col] != 35) {
            vx = 0;
            vy = -speed;
        } else {
            vx = 0;
            vy = 0;
        }



    }
}
