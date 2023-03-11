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
    private static final int MAX_ITERATIONS = 10;
    double x, y;
    private static boolean inBlueMode;
    private String originalImagePath;
    private Timer blueTimer;
    private Timer transitionTimer;

    int index = 0;
    private boolean hasReachedTargetPosition = true;
    private Timer changeDirectionTimer = new Timer();

    private double velocityX, velocityY;
    private double speed = 1;

    int moveCounter =0;
    int stillCounter = 0;

    private static List<Ghost> instances = new ArrayList<>();
    private boolean movingRight, movingLeft, movingUp, movingDown;
    private boolean moveDown, moveUp, moveLeft, moveRight;
    int[][] cells = GameMap.getCells();


    String imagePath;

    boolean isTaskComplete = true;
    boolean timerStart = true;

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


        if (originalImagePath == null) {
            originalImagePath = this.imagePath;
        }

        setImageFromPath("src/images/BlueMode.gif");

        inBlueMode = true; // legges til for å senere sjekke om pacman kan spise den på collision

        if (blueTimer != null) {
            blueTimer.cancel();
        }
        if (transitionTimer != null) {
            transitionTimer.cancel();
        }

        blueTimer = new Timer();
        transitionTimer = new Timer();
        blueTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                setImageFromPath("src/images/transition.gif");

                transitionTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        setImageFromPath(originalImagePath);
                        inBlueMode = false;
                    }
                }, 3500);
            }
        }, 5000);
    }

    public void setImageFromPath(String imagePath){
        try {
            FileInputStream invStream = new FileInputStream(imagePath);
            Image invImage = new Image(invStream);
            setFill(new ImagePattern(invImage));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<Ghost> getAllInstances(){
        return instances;
    }

    public static boolean isBlueMode(){
        return inBlueMode;
    }


    public void update() {
        movement();

        setLayoutX(getLayoutX() + velocityX);
        setLayoutY(getLayoutY() - velocityY);

    }

    public void movement() {

        if (index >= plan.length-1) {
            index = 0;
        }


        currentDirection = plan[index];


        System.out.println(currentDirection);
        System.out.println(moveCounter);

        int row = (int) (getLayoutY() / 32);
        int col = (int) (getLayoutX() / 32);


        moveUp = cells[row-1][col] != 35;
        moveDown = cells[row+1][col] != 35;
        moveLeft = cells[row][col-1] != 35;
        moveRight = cells[row][col+1] != 35;

        if (getLayoutY() == row * 32) {
            if (currentDirection == Direction.UP && moveUp) {
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


    public void fillPlan(Direction[] pl) {
        this.plan = pl;
    }
    
    public void stopMoving() {
        moveCounter = 0;
        velocityX = 0;
        velocityY = 0;
        index++;
    }

//    public Direction[] randomDirection() {
//        int amount = 1000;
//        Direction[] directions = new Direction[amount];
//        Random random = new Random();
//        for (int i = 0; i < amount; i++) {
//            directions[i] = Direction.values()[random.nextInt(Direction.values().length)];
//
//
//        }
//
//        return directions;
//    }

}

