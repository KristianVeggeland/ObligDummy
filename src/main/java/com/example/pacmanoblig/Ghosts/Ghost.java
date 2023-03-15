package com.example.pacmanoblig.Ghosts;

import com.example.pacmanoblig.Direction;
import com.example.pacmanoblig.GameMap;
import com.example.pacmanoblig.GameObjects.Dot;
import com.example.pacmanoblig.GameObjects.Tablet;
import com.example.pacmanoblig.GameObjects.Wall;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Ghost extends Rectangle {
    double x, y;
    private boolean inBlueMode;
    private String originalImagePath;
    private Timer blueTimer, transitionTimer, respawnTimer;
    int index = 0;
    private double velocityX, velocityY;
    private double speed = 1;
    private int moveCounter = 0;
    private int stillCounter = 0;
    private boolean respawning;
    public static boolean resetting = false;
    private static List<Ghost> instances = new ArrayList<>();
    private boolean moveDown, moveUp, moveLeft, moveRight;
    int[][] cells = GameMap.getCells();

    String imagePath;

    protected Direction[] plan;
    Direction currentDirection;

    public Ghost(double x, double y, String imagePath){
        this.x = x;
        this.y = y;
        this.imagePath = imagePath;

        setLayoutX(x);
        setLayoutY(y);
        setWidth(32);
        setHeight(32);
        setViewOrder(-999);

        instances.add(this);
    }


    public void blueMode () {
        // Saves the original image path of the ghost instance
        if (originalImagePath == null) {
            originalImagePath = this.imagePath;
        }

        // Sets ghost image to blue mode
        setImageFromPath("src/images/BlueMode.gif");

        inBlueMode = true;

        // Checks if timers are already active
        if (blueTimer != null) {
            blueTimer.cancel();
        }
        if (transitionTimer != null) {
            transitionTimer.cancel();
        }

        // Lowers speed while in blue mode
        speed = speed*0.5;

        blueTimer = new Timer();
        transitionTimer = new Timer();

        // Two timers so we can have a transition phase
        blueTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                setImageFromPath("src/images/transition.gif");

                transitionTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // Returns ghost to its normal state
                        setImageFromPath(originalImagePath);
                        speed = speed * 2;
                        inBlueMode = false;
                    }
                }, 3500);
            }
        }, 5000);
    }

    // Method to set the current image of the ghost from an image path
    public void setImageFromPath(String imagePath){
        try {
            FileInputStream invStream = new FileInputStream(imagePath);
            Image invImage = new Image(invStream);
            setFill(new ImagePattern(invImage));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Gets all instances of ghosts
    public static List<Ghost> getAllInstances(){
        return instances;
    }

    public boolean isBlueMode(){
        return inBlueMode;
    }


    public void update() {
        // Checks if the ghost is currently respawning
        if (respawning){
            return;
        }

        // 5 seconds timer standing still after player dies
        if (resetting) {
            Timer resetTimer = new Timer();
            stopMoving();
            currentDirection = null;

            resetTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    currentDirection = plan[index];
                    resetting = false;
                }
            }, 5000);
        } else {
            currentDirection = plan[index];
        }

        movement();

        setLayoutX(getLayoutX() + velocityX);
        setLayoutY(getLayoutY() + velocityY);
    }

    public void movement() {

        // Makes it so index cant be out of bounds
        if (index >= plan.length-2) {
            index = 0;
        }

        // Current position of the ghost's row and col
        int row = (int) (getLayoutY() / 32);
        int col = (int) (getLayoutX() / 32);

        // Edge cell checker
        if (col <= 0) {
            if (col == -1 && currentDirection == Direction.LEFT) {
                velocityX = -speed;
                setLayoutX(18*32);
            }
            return;
        }
        if (col >= 18) {
            if (col == 19 && currentDirection == Direction.RIGHT) {
                velocityX = speed;
                setLayoutX(0);
            }
            return;
        }

        // Checks in each direction if there is a wall
        moveUp = cells[row-1][col] != 35;
        moveDown = cells[row+1][col] != 35;
        moveLeft = cells[row][col-1] != 35;
        moveRight = cells[row][col+1] != 35;

        // Only accepts movement if the ghost is in the center of the grid
        if (getLayoutY() == row * 32) {
            if (currentDirection == Direction.UP && moveUp) {
                // moveCounter makes sure it does not immediately switch direction once index is increased
                moveCounter++;
                velocityX = 0;
                velocityY = -speed;
            } else if (currentDirection == Direction.DOWN && moveDown) {
                moveCounter++;
                velocityX = 0;
                velocityY = speed;
            }
            if (currentDirection == Direction.UP && !moveUp) {
                stopMoving();
            } else if (currentDirection == Direction.DOWN && !moveDown) {
                stopMoving();
            }

            if (moveCounter >= 2) {
                if (moveLeft && currentDirection == Direction.DOWN && plan[index+1] == Direction.LEFT) {
                    stopMoving();
                    setScaleX(1);
                    velocityX = -speed;
                    velocityY = 0;
                } else if (moveLeft && currentDirection == Direction.UP && plan[index+1] == Direction.LEFT) {
                    stopMoving();
                    setScaleX(1);
                    velocityX = -speed;
                    velocityY = 0;
                }else if (moveRight && currentDirection == Direction.DOWN && plan[index+1] == Direction.RIGHT ) {
                    stopMoving();
                    setScaleX(-1);
                    velocityX = speed;
                    velocityY = 0;
                } else if (moveRight && currentDirection == Direction.UP && plan[index+1] == Direction.RIGHT ) {
                    stopMoving();
                    setScaleX(-1);
                    velocityX = speed;
                    velocityY = 0;
                }
            }
        }

        if (getLayoutX() == col * 32) {
            if (currentDirection == Direction.LEFT && moveLeft) {
                moveCounter++;
                setScaleX(1);
                velocityX = -speed;
                velocityY = 0;
            }
            if (currentDirection == Direction.RIGHT && moveRight) {
                moveCounter++;
                setScaleX(-1);
                velocityX = speed;
                velocityY = 0;
            }

            if (currentDirection == Direction.LEFT && !moveLeft) {
                stopMoving();
            } else if (currentDirection == Direction.RIGHT && !moveRight) {
                stopMoving();
            }

            if (moveCounter >= 2) {
                if (moveUp && currentDirection == Direction.RIGHT && plan[index+1] == Direction.UP ) {
                    stopMoving();
                    setScaleX(-1);
                    velocityX = 0;
                    velocityY = -speed;
                } else if (moveUp && currentDirection == Direction.LEFT && plan[index+1] == Direction.UP) {
                    stopMoving();
                    setScaleX(-1);
                    velocityX = 0;
                    velocityY = -speed;
                }  else if (moveDown && currentDirection == Direction.RIGHT && plan[index+1] == Direction.DOWN) {
                    stopMoving();
                    setScaleX(-1);
                    velocityX = 0;
                    velocityY = speed;
                }   else if (moveDown && currentDirection == Direction.LEFT && plan[index+1] == Direction.DOWN) {
                    stopMoving();
                    setScaleX(-1);
                    velocityX = 0;
                    velocityY = speed;
                }
            }
        }


        // If ghost is standing still for some reason it will start moving again
        if (velocityX == 0 && velocityY == 0) {
            stillCounter++;
            if (stillCounter >= 3) {
                setLayoutX(col*32);
                setLayoutY(row*32);
                stillCounter = 0;
                index++;
            }
        } else {
            stillCounter = 0;
        }

    }

    // Fills direction array pl from the subclasses
    public void fillPlan(Direction[] pl) {
        this.plan = pl;
    }

    // Stops movement
    public void stopMoving() {
        moveCounter = 0;
        velocityX = 0;
        velocityY = 0;
        index++;
    }

    // Used to respawn ghost
    public void respawn() {
        respawnTimer = new Timer();
        this.setVisible(false);
        setLayoutX(9 * 32);
        setLayoutY(9 * 32);
        stopMoving();
        currentDirection = null;
        respawning = true;
        respawnTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                respawning = false;
                setVisible(true);
            }
        }, 10000);
    }
}

