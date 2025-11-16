package com.password.manager.dashboard.controllers;

import com.password.manager.dashboard.services.DashboardService;
import com.password.manager.utils.Helpers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class DashboardController {
    @FXML
    private VBox credentialsContainer;
    @FXML
    private Label emailsCredentialCount;
    @FXML
    public void initialize() {
        DashboardService dashboardService = new DashboardService(credentialsContainer, emailsCredentialCount);
        dashboardService.initializeDashboard();
    }

    public void showCreateScene(ActionEvent actionEvent) throws IOException {
        Helpers.switchScene(actionEvent, "/com/password/manager/auth/addCredential_view.fxml");
    }
}
