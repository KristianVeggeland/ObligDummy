package com.example.pacmanoblig.Ghosts;

import com.example.pacmanoblig.Direction;
import com.example.pacmanoblig.GameMap;
import com.example.pacmanoblig.GameObjects.Dot;
import com.example.pacmanoblig.GameObjects.Tablet;
import com.example.pacmanoblig.GameObjects.Wall;
import com.example.pacmanoblig.Score;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Ghost extends Rectangle {

    double x, y;
    private static boolean inBlueMode;
    private Image originalImage;
    private Timer blueTimer;
    private Timer transitionTimer;
    private Timer changeDirectionTimer;

    private double velocityX, velocityY;
    private double speed = 2;
    int index = 0;

    String adresse;

    private static List<Ghost> instances = new ArrayList<>();
    private boolean movingRight, movingLeft, movingUp, movingDown;
    private boolean moveDown, moveUp, moveLeft, moveRight;
    int[][] cells = GameMap.getCells();
    private double moveCounter = 0;

    String imagePath;

    protected Direction[] plan;

    public Ghost(double x, double y, String imagePath){
        this.x = x;
        this.y = y;
        this.imagePath = imagePath;

        setLayoutX(x);
        setLayoutY(y);
        setWidth(32);
        setHeight(32);

        instances.add(this);
    }


    public void blueMode () {


        if (originalImage == null) {
            originalImage = new Image(this.imagePath);
        }

        setImageFromPath("src/images/bluemode.gif");

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
                try {
                    FileInputStream invStream = new FileInputStream("src/images/transition.gif");
                    Image transistion = new Image(invStream);
                    setFill(new ImagePattern(transistion));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                transitionTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        setFill(new ImagePattern(originalImage));
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
        int[] controller = new int[plan.length + 1];
        int filler = 1;
        for (int i = 0; i < plan.length +1  ; i++) {
            controller[i] = filler;
            filler++;
        }


        int row = (int) (getLayoutY()/ 32);
        int col = (int) (getLayoutX()/ 32);

        moveUp = cells[row-1][col]!= 35;
        moveDown = cells[row+1][col] != 35;
        moveLeft = cells[row][col-1] != 35;
        moveRight = cells[row][col+1] != 35;


        changeDirectionTimer = new Timer();



        if (index == controller.length - 1){
            index = 0;
        }


        if (getLayoutX() == col * 32) {
            if (plan[index] == Direction.UP && moveUp) {
                ghostDirection = plan[index];
                velocityX = 0;
                velocityY = speed;
                moveCounter = 0;
            }
            else if (plan[index] == Direction.DOWN && moveDown) {
                ghostDirection = plan[index];
                velocityX = 0;
                velocityY = -speed;
                moveCounter = 0;
            }

            if (plan[index] == Direction.UP && !moveUp) {
                System.out.println(1);
                moveCounter++;
                if (index < controller.length-1) {
                    index++;
                }else if (moveCounter == (16/speed) +1  ) {
                    stopMoving();
                }
                else {
                    index = 0;
                }


            }
            else if (plan[index] == Direction.DOWN && !moveDown) {
                System.out.println(2);
                moveCounter++;
                if (index < controller.length-1) {
                    index ++;
                } else if (moveCounter == (16/speed) +1  ) {
                    stopMoving();
                }
                else {
                    index = 0;
                }



            }
        }

        if (getLayoutY() == row * 32) {
            if (plan[index] == Direction.RIGHT && moveRight) {
                ghostDirection = plan[index];
                velocityX = speed;
                velocityY = 0;
                moveCounter = 0;
            }
            else if (plan[index] == Direction.LEFT && moveLeft) {
                ghostDirection = plan[index];
                velocityX = -speed;
                velocityY = 0;
                moveCounter = 0;
            }

            if (plan[index] == Direction.LEFT && !moveLeft) {
                System.out.println(3);
                moveCounter++;
                if (index <= plan.length-1) {
                    index ++;
                }else if (moveCounter == (16/speed) +1  ) {
                    stopMoving();
                }
                else {
                    index = 0;
                }

            }
            else if (plan[index] == Direction.RIGHT && !moveRight) {
                System.out.println(4);
                moveCounter++;
                if (index <= plan.length-1) {
                    index ++;
                } else if (moveCounter == (16/speed) +1  ) {
                    stopMoving();
                } else {
                    index = 0;
                }
            }
        }

    }

    private void stopMoving(){
        velocityX = 0;
        velocityY = 0;
    }

    protected void fillPlan(Direction[] l) {
        this.plan = l;
    }

    private void index(){
        index++;
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

                if (n instanceof Wall) {
                    if (!moveLeft && ghostDirection == Direction.UP) {
                        stopMoving();
                        setLayoutY(getLayoutY()+1);
                    }
                    if (!moveRight && ghostDirection == Direction.UP) {
                        stopMoving();
                        setLayoutY(getLayoutY()+1);
                    }
                    if (!moveLeft && ghostDirection == Direction.DOWN) {
                        stopMoving();
                        setLayoutY(getLayoutY()-1);
                    }
                    if (!moveRight && ghostDirection == Direction.DOWN) {
                        stopMoving();
                        setLayoutY(getLayoutY()-1);
                    }
                    if (!moveUp && ghostDirection == Direction.RIGHT) {
                        stopMoving();
                        setLayoutX(getLayoutX()-1);
                    }
                    if (!moveDown && ghostDirection == Direction.RIGHT) {
                        stopMoving();
                        setLayoutX(getLayoutX()-1);
                    }
                    if (!moveUp && ghostDirection == Direction.LEFT) {
                        stopMoving();
                        setLayoutX(getLayoutX()+1);
                    }
                    if (!moveDown && ghostDirection == Direction.LEFT) {
                        stopMoving();
                        setLayoutX(getLayoutX()+1);
                    }
                }
            }
        }
    }
}
