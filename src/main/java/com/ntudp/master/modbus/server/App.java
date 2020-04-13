package com.ntudp.master.modbus.server;

import javafx.application.Application;
/*    */
/*    */ import javafx.event.EventHandler;
/*    */ import javafx.fxml.FXMLLoader;
/*    */ import javafx.scene.Parent;
/*    */ import javafx.scene.Scene;
/*    */ import javafx.scene.input.MouseEvent;
/*    */ import javafx.scene.paint.Color;
/*    */ import javafx.stage.Stage;
/*    */ import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    private double xOffset = 0.0D;
    private double yOffset = 0.0D;
    private Parent root;

    public App() {
        root = null;
    }


    public void start(final Stage primaryStage) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("server/server_ui.fxml")));
        } catch (IOException e) {
            System.out.println(("File with UI not found!"));

        }
        initMouseEvents(root, primaryStage);

        Scene mainScene = new Scene(root);
        mainScene.setFill(Color.TRANSPARENT);
        primaryStage.setTitle("SERVER");
        primaryStage.setScene(mainScene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    private void initMouseEvents(Parent root, Stage primaryStage) {
        root.setOnMousePressed(event -> {
            xOffset = primaryStage.getX() - event.getScreenX();
            yOffset = primaryStage.getY() - event.getScreenY();
            primaryStage.setOpacity(0.9D);
        });
        root.setOnMouseReleased(event -> {
            primaryStage.setOpacity(1.0D);
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() + App.this.xOffset);

            primaryStage.setY(event.getScreenY() + App.this.yOffset);

        });


    }

    public static void main(String[] args) {
        launch(args);
    }
}
