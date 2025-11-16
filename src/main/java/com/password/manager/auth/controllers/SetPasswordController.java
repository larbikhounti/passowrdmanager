package com.password.manager.auth.controllers;

import com.password.manager.auth.passwordHandler.PasswordStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import com.password.manager.utils.Helpers;

public class SetPasswordController {

    @FXML
    private Button setPasswordBtn;

    @FXML
    private TextField password;

    @FXML
    private TextField retypedPassword;

    @FXML
    private Label feedbackLabel;


    public void savePassword(ActionEvent event) throws Exception {
        String passwordText = password.getText();
        String retypedPasswordText = retypedPassword.getText();

        boolean isMatched = Helpers.isStringsEqual(passwordText, retypedPasswordText);

        if (isMatched && !passwordText.isEmpty()) {
            PasswordStorage.savePassword(passwordText);
            Helpers.labelHandler(feedbackLabel, "Password set successfully!", "GREEN", true);

            Helpers.delayer(2, () -> {
                try {
                    Helpers.switchScene(event, "/com/password/manager/auth/authentication_view.fxml");
                } catch (IOException e) {
                    Helpers.Logger("Scene switch error: " + e.getMessage(), "ERROR");
                }
            });
        } else {
            Helpers.labelHandler(feedbackLabel, "Passwords do not match!", "RED", true);
        }
    }
}
