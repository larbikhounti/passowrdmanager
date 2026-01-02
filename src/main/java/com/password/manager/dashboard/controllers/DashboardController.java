package com.password.manager.dashboard.controllers;

import com.password.manager.credentials.services.CredentialsService;
import com.password.manager.dashboard.services.DashboardService;
import com.password.manager.utils.Helpers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class DashboardController {
    @FXML
    public VBox credentialContainer; //single credential container
    @FXML
    private VBox credentialsContainer; //all credentials container
    @FXML
    private Label emailsCredentialCount;
    @FXML
    private Label noteCredentialCount;
    @FXML
    private Label creditCardCredentialCount;

    @FXML
    private Label totalCredentialsCount;

    @FXML
    private TextField searchTextField;

    private DashboardService dashboardService;

    @FXML
    public void initialize() {
        CredentialsService credentialsService = new CredentialsService();
        dashboardService = new DashboardService(
                credentialsContainer,
                credentialContainer,
                emailsCredentialCount,
                noteCredentialCount,
                creditCardCredentialCount,
                totalCredentialsCount,
                credentialsService);
        dashboardService.initializeDashboard();
    }

    @FXML
    public void handleSearch() {
        String searchQuery = searchTextField.getText();
        dashboardService.searchCredentials(searchQuery);
    }

    public void showCreateScene(ActionEvent actionEvent) throws IOException {
        Helpers.switchScene(actionEvent, "/com/password/manager/auth/addCredential_view.fxml");
    }
}
