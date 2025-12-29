package com.password.manager.auth.controllers;

import java.io.IOException;

import com.password.manager.auth.services.AuthenticationService;
import com.password.manager.utils.Helpers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;


public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController() {
        this.authenticationService = new AuthenticationService();
    }

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label feedbackLabel;


    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException {
        if (authenticationService.login(passwordField.getText())) {
            //Helpers.labelHandler(feedbackLabel, "Login successful! Redirecting...", "GREEN", true);
            Helpers.showAlert("Login Successful", "You have been logged in successfully.", AlertType.INFORMATION);
            Helpers.switchScene(event, "/com/password/manager/auth/dashboard_view.fxml");
//            Helpers.delayer(2, () -> {
//                try {
//                    Helpers.switchScene(event, "/com/password/manager/auth/dashboard_view.fxml");
//                } catch (IOException e) {
//                    Helpers.Logger("Scene switch error: " + e.getMessage(), "ERROR");
//                }
//            });

        } else {
            Helpers.showAlert("Login Failed", "Login failed. Please try again.", AlertType.ERROR);
           // Helpers.labelHandler(feedbackLabel, "Login failed. Please try again.", "RED", true);
        }
    }
}
