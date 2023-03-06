package com.example.pacmanoblig;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Player extends Circle {

    double b, targetX, targetY, velocityX, velocityY, x, y;
    boolean hasNoValue = true;
    boolean moveDown, moveUp, moveLeft, moveRight, moveNone;

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


    // Class constructor.
    public Player(double x, double y) {
        this.x = x;
        this.y = y;

        setLayoutX(x);
        setLayoutY(y);
        setFill(Color.YELLOW);
        setRadius(16);
    }

    // Method that is keeps track of the player.
    public void update()  {
        checkDirection();
        setLayoutY(getLayoutY() + velocityY);
        setLayoutX(getLayoutX() + velocityX);
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
        g.getChildren().add(testCircle );

        moveUp = cells[row-1][col]!= 35;
        moveDown = cells[row+1][col] != 35;
        moveLeft = cells[row][col-1] != 35;
        moveRight = cells[row][col+1] != 35;

        int pacmanOffset = 16;
        double speed = 0.5;

        // Moving right checker
        if (inputDirection == Direction.RIGHT && moveRight) {
            if (pacmanDirection == Direction.UP) {
                if (hasNoValue) {
                    b = this.getLayoutY() - pacmanOffset;
                    hasNoValue = false;
                }
                if (this.getLayoutY() == b) {
                    pacmanDirection = inputDirection;
                    hasNoValue = true;
                }
            }

            if (pacmanDirection == Direction.DOWN) {
                if (hasNoValue) {
                    b = this.getLayoutY() + pacmanOffset;
                    hasNoValue = false;
                }
                if (this.getLayoutY() == b) {
                    pacmanDirection = inputDirection;
                    hasNoValue = true;
                }
            }

            if (pacmanDirection == Direction.LEFT) {
                pacmanDirection = inputDirection;
            }

            if (pacmanDirection == null) {
                pacmanDirection = inputDirection;
            }
        }

        //Moving Left checker
        else if (inputDirection == Direction.LEFT && moveLeft) {
            if (pacmanDirection == Direction.UP) {
                if (hasNoValue) {
                    b = this.getLayoutY() - pacmanOffset;
                    hasNoValue = false;
                }
                if (this.getLayoutY() == b) {
                    pacmanDirection = inputDirection;
                    hasNoValue = true;
                }
            }

            if (pacmanDirection == Direction.DOWN) {
                if (hasNoValue) {
                    b = this.getLayoutY() + pacmanOffset;
                    hasNoValue = false;
                }
                if (this.getLayoutY() == b) {
                    pacmanDirection = inputDirection;
                    hasNoValue = true;
                }
            }

            if (pacmanDirection == Direction.RIGHT) {
                pacmanDirection = inputDirection;
            }

            if (pacmanDirection == null) {
                pacmanDirection = inputDirection;
            }
        }

        // Moving UP checker
        else if (inputDirection == Direction.UP && moveUp) {
            if (pacmanDirection == Direction.RIGHT) {
                if (hasNoValue) {
                    b = this.getLayoutX() + pacmanOffset;
                    hasNoValue = false;
                }
                if (this.getLayoutX() == b) {
                    pacmanDirection = inputDirection;
                    hasNoValue = true;
                }
            }

            if (pacmanDirection == Direction.LEFT) {
                if (hasNoValue) {
                    b = this.getLayoutX() - pacmanOffset;
                    hasNoValue = false;
                }
                if (this.getLayoutX() == b) {
                    pacmanDirection = inputDirection;
                    hasNoValue = true;
                }
            }

            if (pacmanDirection == Direction.DOWN) {
                pacmanDirection = inputDirection;
            }

            if (pacmanDirection == null) {
                pacmanDirection = inputDirection;
            }
        }

        // Moving Down checker
        else if (inputDirection == Direction.DOWN && moveDown) {
            if (pacmanDirection == Direction.RIGHT) {
                if (hasNoValue) {
                    b = this.getLayoutX() + pacmanOffset;
                    hasNoValue = false;
                }
                if (this.getLayoutX() == b) {
                    pacmanDirection = inputDirection;
                    hasNoValue = true;
                }
            }

            if (pacmanDirection == Direction.LEFT) {
                if (hasNoValue) {
                    b = this.getLayoutX() - pacmanOffset;
                    hasNoValue = false;
                }
                if (this.getLayoutX() == b) {
                    pacmanDirection = inputDirection;
                    hasNoValue = true;
                }
            }

            if (pacmanDirection == Direction.UP) {
                pacmanDirection = inputDirection;
            }

            if (pacmanDirection == null) {
                pacmanDirection = inputDirection;
            }
        }

        if (pacmanDirection == Direction.RIGHT && moveRight) {
            velocityX = speed;
            velocityY = 0;
            moveNone = true;
        } else if (pacmanDirection == Direction.LEFT && moveLeft) {
            velocityX = -speed;
            velocityY = 0;
            moveNone = true;
        } else if (pacmanDirection == Direction.UP && moveUp) {
            velocityX = 0;
            velocityY = -speed;
            moveNone = true;
        } else if (pacmanDirection == Direction.DOWN && moveDown) {
            velocityX = 0;
            velocityY = speed;
            moveNone = true;
        } else if (pacmanDirection == Direction.LEFT && !moveLeft) {
            if (moveNone) {
                allowMovement();
                targetX = getLayoutX()- pacmanOffset;
            }
            if (getLayoutX() == targetX) {
                velocityX = 0;
                velocityY = 0;
            }
            if (velocityX == 0 && velocityY == 0) {
                pacmanDirection = null;
            }
        } else if (pacmanDirection == Direction.RIGHT && !moveRight) {
            if (moveNone) {
                allowMovement();
                targetX = getLayoutX() + pacmanOffset;
            }
            if (getLayoutX() == targetX) {
                velocityX = 0;
                velocityY = 0;
            }
            if (velocityX == 0 && velocityY == 0) {
                pacmanDirection = null;
            }
        }else if (pacmanDirection == Direction.UP && !moveUp) {
            if (moveNone) {
                allowMovement();
                targetY = getLayoutY() - pacmanOffset;
            }
            if (getLayoutY() == targetY) {
                velocityX = 0;
                velocityY = 0;
            }
            if (velocityX == 0 && velocityY == 0) {
                pacmanDirection = null;
            }
        }else if (pacmanDirection == Direction.DOWN && !moveDown) {
            if (moveNone) {
                allowMovement();
                targetY = getLayoutY() + pacmanOffset;
            }
            if (getLayoutY() == targetY) {
                velocityX = 0;
                velocityY = 0;
            }
            if (velocityX == 0 && velocityY == 0) {
                pacmanDirection = null;
            }

        }
    }

    public void allowMovement() {
        moveNone = false;
    }
}
