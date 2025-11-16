package com.password.manager.utils;


import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

public class Helpers {
    public static void switchScene(ActionEvent event, String fxml) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Helpers.class.getResource(fxml)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void labelHandler(Label label, String text, String color,boolean isVisible) throws IOException {
        try {
            label.setText(text);
            label.setTextFill(Paint.valueOf(color));
            label.setVisible(isVisible);
        }catch (Exception e){
            throw new IOException("Label handling error: " + e.getMessage());
        }
    }

    public static void delayer(int seconds , Runnable action) throws  IOException {
        PauseTransition delay = new PauseTransition(Duration.seconds(seconds));
        delay.setOnFinished(e -> {
            action.run();
        });
        delay.play();
    }

    public static void Logger(String message , String level) {
        String logMessage = "[" + level.toUpperCase() + "] " + message;
        System.out.println(logMessage);
    }

    public static boolean isStringsEqual(String str1, String str2) {
        return str1 != null && str1.equals(str2);
    }
}
