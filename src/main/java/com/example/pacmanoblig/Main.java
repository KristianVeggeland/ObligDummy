package com.example.pacmanoblig;
//Imports
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    GameMap map;
    long lastTime = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        map = GameMap.getMap();

        Scene scene = new Scene(map.createMap(), Color.BLACK);
        scene.setOnKeyPressed(this::actionEvents);

        primaryStage.setTitle("Pac-Man");
        primaryStage.setScene(scene);
        primaryStage.show();

        int targetFps = 80;
        long interval = (long) (1e9 / targetFps);

        AnimationTimer timer = new AnimationTimer() {
            long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                long elapsedNanos = now - lastTime;
                if (elapsedNanos < interval) {
                    return;
                }
                double frameTime = elapsedNanos / 1e9;
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
        map.getPlayer().handleKeyEvent(e);
    }

}