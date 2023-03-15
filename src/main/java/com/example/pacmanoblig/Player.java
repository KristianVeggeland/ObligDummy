package com.example.pacmanoblig;


import com.example.pacmanoblig.GameObjects.Dot;
import com.example.pacmanoblig.GameObjects.PacManArc;
import com.example.pacmanoblig.GameObjects.Tablet;
import com.example.pacmanoblig.GameObjects.Wall;
import com.example.pacmanoblig.Ghosts.Ghost;
import com.example.pacmanoblig.UI.Lives;
import com.example.pacmanoblig.UI.Score;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.pacmanoblig.UI.Score.score;

public class Player extends Circle {

    // Int array that gets a reference to the cells instance in GameMap
    int[][] cells = GameMap.getCells();
    Direction pacmanDirection, inputDirection;
    private boolean startDirection, moveDown, moveUp, moveLeft, moveRight = true;
    boolean isMoving = false;
    double x,y;
    private double velocityX, velocityY;
    private final double speed = 2;
    private double moveCounter = 0;
    private boolean resetting;
    private PacManArc arc;



    // Class constructor.
    public Player(double x, double y) {
        this.x = x;
        this.y = y;

        setLayoutX(x);
        setLayoutY(y);
        setRadius(16);
        setVisible(false);
        setFill(Color.YELLOW);

        startDirection = true;
    }

    // Method that is keeps track of the player.
    public void update() {

        // 5 seconds timer after every death
        if (resetting) {
            Timer resetTimer = new Timer();
            inputDirection = null;
            arc.setStartAngle(-135);
            stopMoving();
            setLayoutX(9*32 + 32 / 2);
            setLayoutY(15*32 + 32 / 2);
            resetTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    startDirection = true;
                    resetting = false;
                }
            }, 5000);
        }

        updateArc();
        checkCollision();
        checkDirection();

        // Sets the position after velocity has been modified
        setLayoutY(getLayoutY() - velocityY);
        setLayoutX(getLayoutX() + velocityX);
    }

    public void handleKeyEvent(KeyEvent e) {
        if (e.getCode() == KeyCode.W) {
            inputDirection = Direction.UP;
            isMoving = true;
        } else if (e.getCode() == KeyCode.A) {
            inputDirection = Direction.LEFT;
            isMoving = true;

        } else if (e.getCode() == KeyCode.S) {
            inputDirection = Direction.DOWN;
            isMoving = true;

        } else if (e.getCode() == KeyCode.D) {
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

        // Gets the current row and col of the player
        int row = (int) (getLayoutY()/ 32);
        int col = (int) (getLayoutX()/ 32);


        // Checks if the player is on an edge cell
        if (col <= 0) {
            if (col == -1 && pacmanDirection == Direction.LEFT) {
                velocityX = -speed;
                // Teleports the player to the opposite side
                setLayoutX(18*32);
            }
            return;
        }

        if (col >= 18) {
            if (col == 19 && pacmanDirection == Direction.RIGHT) {
                velocityX = speed;
                setLayoutX(0);
            }
            return;
        }

        // Checks in each direction if there is a wall
        moveUp = cells[row-1][col]!= 35;
        moveDown = cells[row+1][col] != 35;
        moveLeft = cells[row][col-1] != 35;
        moveRight = cells[row][col+1] != 35;

        // Only accepts movement if the player is in the center of the grid
        if (getLayoutX() - getRadius() == col * 32) {
            if (inputDirection == Direction.UP && moveUp) {
                // Applies the movement to the actual direction of the player
                pacmanDirection = inputDirection;
                arc.setStartAngle(-225);
                velocityX = 0;
                velocityY = speed;
                moveCounter = 0;
            }
            else if (inputDirection == Direction.DOWN && moveDown) {
                pacmanDirection = inputDirection;
                arc.setStartAngle(-45);
                velocityX = 0;
                velocityY = -speed;
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
                arc.setStartAngle(45);
                velocityX = speed;
                velocityY = 0;
                moveCounter = 0;
            }
            else if (inputDirection == Direction.LEFT && moveLeft) {
                pacmanDirection = inputDirection;
                if (arc.getStartAngle() != -135){
                    arc.setStartAngle(-135);
                }
                velocityX = -speed;
                velocityY = 0;
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

    // Stops movement
    private void stopMoving() {
        velocityX = 0;
        velocityY = 0;
        isMoving = false;
    }

    // Checks for overlapping objects
    public void checkCollision() {

        Pane g = (Pane) this.getParent();
        List<Ghost> ghosts = Ghost.getAllInstances();

        ArrayList<Shape> listOfObjects = new ArrayList<>();
        Node helper;

        for (int i = 0; i < g.getChildren().size(); i++) {
            helper = g.getChildren().get(i);
            if (helper instanceof Dot || helper instanceof Tablet || helper instanceof Wall)  {
                listOfObjects.add((Shape) helper);
            }
        }

        for (Ghost ghost : ghosts) {
            Shape intersects = Shape.intersect(this, ghost);

            // Checks if any ghosts are overlapping
            if (intersects.getBoundsInLocal().getWidth() != -1) {
                if (ghost.isBlueMode()) {
                    ghost.respawn();
                    score=score+200;
                } else {
                    Lives.lives--;
                    ((GameMap) this.getParent()).resetMap();
                    resetting = true;
                    Ghost.resetting = true;
                }
            }
        }
        for (Shape n: listOfObjects) {
            Shape intersects = Shape.intersect(this, n);
            // Checks if any GameObjects are overlapping
            if (intersects.getBoundsInLocal().getWidth() != -1) {
                if (n instanceof Dot) {
                    g.getChildren().remove(n);
                    score=score+10;
                } else if (n instanceof Tablet) {
                    ghosts.forEach(Ghost::blueMode);
                    g.getChildren().remove(n);
                }
                // Wall collision (used for edge cases where our grid collision check does not work)
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

    // Updates the overlaying arc to be the same position as player
    public void updateArc(){
        Pane g = (Pane) this.getParent();
        arc = PacManArc.getInstance();
        g.getChildren().remove(arc);
        arc.setLayoutX(getLayoutX());
        arc.setLayoutY(getLayoutY());
        g.getChildren().add(arc);
    }


}