package com.example.pacmanoblig;
//Imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import com.example.pacmanoblig.GameObjects.Dot;
import com.example.pacmanoblig.GameObjects.PacManArc;
import com.example.pacmanoblig.GameObjects.Tablet;
import com.example.pacmanoblig.GameObjects.Wall;
import com.example.pacmanoblig.Ghosts.Blinky;
import com.example.pacmanoblig.Ghosts.Clyde;
import com.example.pacmanoblig.Ghosts.Inky;
import com.example.pacmanoblig.Ghosts.Pinky;
import javafx.scene.Group;


public class GameMap {

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
        Group root = new Group();

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int ch = cells[row][col];
                if (ch == '#') {
                    Wall wall = new Wall(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    root.getChildren().add(wall);
                }
                if (ch == '-') {
                    Dot dot = new Dot(col * CELL_SIZE + CELL_SIZE / 2, row * CELL_SIZE + CELL_SIZE / 2);
                    root.getChildren().add(dot);
                }
                if (ch == '=') {
                    Tablet energy = new Tablet(col * CELL_SIZE + CELL_SIZE / 2, row * CELL_SIZE + CELL_SIZE / 2);
                    root.getChildren().add(energy);
                }
                if (ch == '0'){
                    player = new Player(col * CELL_SIZE + CELL_SIZE / 2, row * CELL_SIZE + CELL_SIZE / 2);

                    player.setViewOrder(-1000);
                    PacManArc arc = new PacManArc();
                    root.getChildren().add(arc);
                    root.getChildren().add(player);
                }
                if (ch == '1'){
                    Blinky blinky = new Blinky(col * CELL_SIZE , row * CELL_SIZE);
                    root.getChildren().add(blinky);
                }
                if (ch == '2'){
                    Inky inky = new Inky(col * CELL_SIZE , row * CELL_SIZE);
                    root.getChildren().add(inky);
                }
                if (ch == '3'){
                    Pinky pinky = new Pinky(col * CELL_SIZE , row * CELL_SIZE);
                    root.getChildren().add(pinky);
                }
                if (ch == '4'){
                    Clyde clyde = new Clyde(col * CELL_SIZE , row * CELL_SIZE);
                    root.getChildren().add(clyde);
                }
            }
        }
        return root;
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


}