package com.example.pacmanoblig;


import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

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

    // Directions that store which way pacman is going and the last direction the player gave regardless if it was valid.
    Direction pacmanDirection, inputDirection;

    private final Duration animationDuration = Duration.ofMillis(256);
    private boolean isAnimationFinished = false;
    private long startTime;

    boolean moveDown, moveUp, moveLeft, moveRight, moveNone;
    long lastTime = 0;
    ArrayList<Circle> testCircles = new ArrayList<>();

    // Attributes that are used to show pacmans location.
    double x,y;
    // Attributes determening the players velocity.
    private double vx, vy;
    private final double speed = 0.5;

    // Class constructor.
    public Player(double x, double y) {
        this.x = x;
        this.y = y;

        setLayoutX(x);
        setLayoutY(y);
        setRadius(16);
        setFill(Color.YELLOW);
        setScaleX(1);
        setScaleY(1);

        moveNone = true;
        d=true;
        bb=true;

    }

    // Method that is keeps track of the player.
    public void update() {
        checkDirection();

        setLayoutY(getLayoutY() + vy);
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

        int row = (int) (getLayoutY() / 32);
        int col = (int) (getLayoutX() / 32);


        Circle testCircle = new Circle(col * 32 + 32 / 2, row* 32 + 32 / 2, 3, Color.RED);

        testCircles.add(testCircle);
        g.getChildren().add(testCircle );

        moveUp = cells[row-1][col]!= 35;
        moveDown = cells[row+1][col] != 35;
        moveLeft = cells[row][col-1] != 35;
        moveRight = cells[row][col+1] != 35;

        // Moving right checker
        if (inputDirection == Direction.RIGHT && moveRight) {

            if (pacmanDirection == Direction.UP) {
                System.out.println("a");
                if (bb) {
                    b = this.getLayoutY() - 15;
                    bb = false;
                }
                if (this.getLayoutY() == b) {
                    pacmanDirection = inputDirection;
                    bb = true;
                }
            }

            if (pacmanDirection == Direction.DOWN) {
                if (bb) {
                    b = this.getLayoutY() + 15;
                    bb = false;
                }
                if (this.getLayoutY() == b) {
                    pacmanDirection = inputDirection;
                    bb = true;
                }
            }

            if (pacmanDirection == Direction.LEFT) {
                pacmanDirection = inputDirection;
            }

            if (pacmanDirection == null) {
                pacmanDirection = inputDirection;
            }

            System.out.println(pacmanDirection);


        }

        //Moving Left checker
        else if (inputDirection == Direction.LEFT && moveLeft) {
            if (pacmanDirection == Direction.UP) {
                System.out.println("a");
                if (bb) {
                    b = this.getLayoutY() - 15;
                    bb = false;
                }
                if (this.getLayoutY() == b) {
                    pacmanDirection = inputDirection;
                    bb = true;
                }
            }

            if (pacmanDirection == Direction.DOWN) {
                if (bb) {
                    b = this.getLayoutY() + 15;
                    bb = false;
                }
                if (this.getLayoutY() == b) {
                    pacmanDirection = inputDirection;
                    bb = true;
                }
            }

            if (pacmanDirection == Direction.RIGHT) {
                pacmanDirection = inputDirection;
            }

            if (pacmanDirection == null) {
                pacmanDirection = inputDirection;
            }
            System.out.println(pacmanDirection);

        }

        // Moving UP checker
        else if (inputDirection == Direction.UP && moveUp) {
            if (pacmanDirection == Direction.RIGHT) {
                System.out.println("a");
                if (bb) {
                    b = this.getLayoutX() + 15;
                    bb = false;
                }
                if (this.getLayoutX() == b) {
                    pacmanDirection = inputDirection;
                    bb = true;
                }
            }

            if (pacmanDirection == Direction.LEFT) {
                if (bb) {
                    b = this.getLayoutX() - 15;
                    bb = false;
                }
                if (this.getLayoutX() == b) {
                    pacmanDirection = inputDirection;
                    bb = true;
                }
            }

            if (pacmanDirection == Direction.DOWN) {
                pacmanDirection = inputDirection;
            }

            if (pacmanDirection == null) {
                pacmanDirection = inputDirection;
            }
            System.out.println(pacmanDirection);

        }

        // Moving Down checker
        else if (inputDirection == Direction.DOWN && moveDown) {
            if (pacmanDirection == Direction.RIGHT) {
                System.out.println("a");
                if (bb) {
                    b = this.getLayoutX() + 15;
                    bb = false;
                }
                if (this.getLayoutX() == b) {
                    pacmanDirection = inputDirection;
                    bb = true;
                }
            }

            if (pacmanDirection == Direction.LEFT) {
                if (bb) {
                    b = this.getLayoutX() - 15;
                    bb = false;
                }
                if (this.getLayoutX() == b) {
                    pacmanDirection = inputDirection;
                    bb = true;
                }
            }

            if (pacmanDirection == Direction.UP) {
                pacmanDirection = inputDirection;
            }

            if (pacmanDirection == null) {
                pacmanDirection = inputDirection;
            }
            System.out.println(pacmanDirection);

        }

        if (pacmanDirection == Direction.RIGHT && moveRight) {
            vx = speed;
            vy = 0;
            moveNone = true;
        } else if (pacmanDirection == Direction.LEFT && moveLeft) {
            vx = -speed;
            vy = 0;
            moveNone = true;
        } else if (pacmanDirection == Direction.UP && moveUp) {
            vx = 0;
            vy = -speed;
            moveNone = true;
        } else if (pacmanDirection == Direction.DOWN && moveDown) {
            vx = 0;
            vy = speed;
            moveNone = true;
        } else if (pacmanDirection == Direction.LEFT && !moveLeft) {
            if (moveNone) {
                changeD();
                xr = getLayoutX()-15;
            }
            if (getLayoutX() == xr) {

                vx = 0;
                vy = 0;
            }
            if (vx == 0 && vy == 0) {
                pacmanDirection = null;
            }
        } else if (pacmanDirection == Direction.RIGHT && !moveRight) {
            if (moveNone) {
                changeD();
                xr = getLayoutX() + 15;
            }
            if (getLayoutX() == xr) {

                vx = 0;
                vy = 0;
            }
            if (vx == 0 && vy == 0) {
                pacmanDirection = null;
            }
        }else if (pacmanDirection == Direction.UP && !moveUp) {
            if (moveNone) {
                changeD();
                xr = getLayoutY() - 15;
            }
            if (getLayoutY() == xr) {


                vx = 0;
                vy = 0;
            }
            if (vx == 0 && vy == 0) {
                pacmanDirection = null;
            }
        }else if (pacmanDirection == Direction.DOWN && !moveDown) {
            if (moveNone) {
                changeD();
                xr = getLayoutY() + 15;
            }
            if (getLayoutY() == xr) {

                vx = 0;
                vy = 0;


            }
            if (vx == 0 && vy == 0) {
                pacmanDirection = null;
            }

        }

       /* if (pacmanDirection == Direction.LEFT && !moveLeft && moveNone) {
            System.out.println(this.getCenterX());
            System.out.println();
            moveNone = false;

        } else if (pacmanDirection == Direction.RIGHT && !moveRight && moveNone){
            setLayoutX(getLayoutX() +16);
            moveNone = false;
        } else if (pacmanDirection == Direction.UP && !moveUp && moveNone) {
            setLayoutY(getLayoutY() -16);
            moveNone = false;

        } else if (pacmanDirection == Direction.DOWN && !moveDown && moveNone) {
            setLayoutY(getLayoutY() +16);
            moveNone = false;
        }
        */

    }

    public void smoothOffset(double time){
        setLayoutX(getLayoutX() -1);
    }

    public void changeD() {
        moveNone = false;

        System.out.println(xr);
    }
}
