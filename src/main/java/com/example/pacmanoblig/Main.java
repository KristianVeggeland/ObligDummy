package com.example.pacmanoblig;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    GameMap map;
    long lastTime = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        map = new GameMap("src/pacman-map.txt");
        Scene scene = new Scene(map.createMap(), Color.BLACK);
        scene.setOnKeyPressed(this::actionEvents);
        primaryStage.setTitle("Pac-Man");
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                double frameTime = (now - lastTime) / 1e9;
                update(frameTime);
                lastTime = now;
            }
        };
        timer.start();

    }

    private void update(double frameTime) {
        map.getPlayer().update();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void actionEvents(KeyEvent e) {
        map.getPlayer().keyPressed(e);
    }

}