package com.lame.jnotify.ui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;

public class Jnotify extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new Group());
        primaryStage.setTitle("文件同步");
        primaryStage.setWidth(420);
        primaryStage.setHeight(180);

        FileChooser fileChooser = new FileChooser();
        Button bt1 = new Button("选择文件");
//        bt1.addActionListener();


        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
