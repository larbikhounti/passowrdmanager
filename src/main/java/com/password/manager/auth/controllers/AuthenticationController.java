package com.password.manager.auth.controllers;

import java.io.IOException;

import com.password.manager.auth.services.AuthenticationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AuthenticationController {

    private final AuthenticationService authenticationService ;

    public AuthenticationController() {
        this.authenticationService = new AuthenticationService();
    }

    @FXML
    private TextField passwordField;

    
    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException {
       var password = passwordField.getText();
       boolean isAuthenticated = authenticationService.login(password);
       if (isAuthenticated) {
           System.out.println("Login successful");
           // TODO Navigate to the main application screen
       } else {
           System.out.println("Login failed");
           // TODO Show error message
       }
    }
}
