package com.example.pacmanoblig;
//Imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.example.pacmanoblig.GameObjects.*;
import com.example.pacmanoblig.Ghosts.Blinky;
import com.example.pacmanoblig.Ghosts.Clyde;
import com.example.pacmanoblig.Ghosts.Inky;
import com.example.pacmanoblig.Ghosts.Pinky;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Shape;


public class GameMap extends Group {

    private static final int CELL_SIZE = 32;

    private int numRows;
    private int numCols;
    private static int[][] cells = null;

    private Player player;



    private static GameMap map;

    static {
        try {
            map = new GameMap();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public GameMap() throws FileNotFoundException {
        map = this;

        File file = new File("src/pacman-map.txt");
        Scanner scanner = new Scanner(file);
        numRows = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            numCols = line.length();
            numRows++;
        }
        scanner.close();

        cells = new int[numRows][numCols];

        scanner = new Scanner(file);
        int row = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int col = 0; col < line.length(); col++) {
                char ch = line.charAt(col);
                cells[row][col] = ch;

            }
            row++;
        }
        scanner.close();

    }


    public Group createMap() {

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int ch = cells[row][col];
                if (ch == '#') {
                    Wall wall = new Wall(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    this.getChildren().add(wall);
                }
                if (ch == '-') {
                    Dot dot = new Dot(col * CELL_SIZE + CELL_SIZE / 2, row * CELL_SIZE + CELL_SIZE / 2);
                    this.getChildren().add(dot);
                }
                if (ch == '=') {
                    Tablet energy = new Tablet(col * CELL_SIZE + CELL_SIZE / 2, row * CELL_SIZE + CELL_SIZE / 2);
                    this.getChildren().add(energy);
                }
                if (ch == '0'){
                    player = new Player(col * CELL_SIZE + CELL_SIZE / 2, row * CELL_SIZE + CELL_SIZE / 2);

                    player.setViewOrder(-1000);
                    PacManArc arc = new PacManArc();
                    this.getChildren().add(arc);
                    this.getChildren().add(player);
                }
                if (ch == '1'){
                    Blinky blinky = new Blinky(col * CELL_SIZE , row * CELL_SIZE);
                    this.getChildren().add(blinky);
                }
                if (ch == '2'){
                    Inky inky = new Inky(col * CELL_SIZE , row * CELL_SIZE);
                    this.getChildren().add(inky);
                }
                if (ch == '3'){
                    Pinky pinky = new Pinky(col * CELL_SIZE , row * CELL_SIZE);
                    this.getChildren().add(pinky);
                }
                if (ch == '4'){
                    Clyde clyde = new Clyde(col * CELL_SIZE , row * CELL_SIZE);
                    this.getChildren().add(clyde);
                }
            }
        }
        return this;
    }

    public static int[][] getCells(){
        return cells;
    }

    public Player getPlayer(){
        return player;
    }

    public static GameMap getMap(){
        return map;
    }

    public void resetMap() {

        ArrayList<Shape> listOfObjects = new ArrayList<>();
        Node helper;

        for (int i = 0; i < this.getChildren().size(); i++) {
            helper = this.getChildren().get(i);
            if (helper instanceof Player || helper instanceof Blinky || helper instanceof Clyde || helper instanceof Pinky || helper instanceof Inky) {
                listOfObjects.add((Shape) helper);
            }
        }

        for (Shape n : listOfObjects) {
            if (n instanceof Player) {
                n.setLayoutX(9 * CELL_SIZE + CELL_SIZE / 2);
                n.setLayoutY(15 * CELL_SIZE + CELL_SIZE / 2);
            }
            if (n instanceof Blinky) {
                n.setLayoutX(9 * CELL_SIZE);
                n.setLayoutY(7 * CELL_SIZE);
            }
            if (n instanceof Inky) {
                n.setLayoutX(8 * CELL_SIZE);
                n.setLayoutY(9 * CELL_SIZE);
            }
            if (n instanceof Pinky) {
                n.setLayoutX(9 * CELL_SIZE);
                n.setLayoutY(9 * CELL_SIZE);
            }
            if (n instanceof Clyde) {
                n.setLayoutX(10 * CELL_SIZE);
                n.setLayoutY(9 * CELL_SIZE);
            }
        }
    }
}

