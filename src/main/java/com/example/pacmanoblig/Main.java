package com.example.pacmanoblig;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    GameMap map;

    long lastTime = 0;

    Top top = new Top();

    BorderPane borderPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        map = GameMap.getMap();

        borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        borderPane.setTop(top);
        borderPane.setCenter(map.createMap());

        Scene scene = new Scene(borderPane, Color.BLACK);
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
        top.getS().checkScore();
    }

    public static void main(String[] args) {
        launch(args);
    }


    public void actionEvents(KeyEvent e) {
        map.getPlayer().handleKeyEvent(e);
    }

}