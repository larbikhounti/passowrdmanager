package com.password.manager.dashboard.services;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.entities.CreditCard;
import com.password.manager.credentials.entities.Email;
import com.password.manager.credentials.entities.Note;
import com.password.manager.credentials.services.CredentialsService;
import com.password.manager.credentials.services.RenderService;
import com.password.manager.utils.Helpers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class DashboardService {
    private final Label emailsCredentialCount;
    private final Label notesCredentialCount;
    private final Label creditCardCredentialCount;
    private final Label totalCredentialsCount;
    private final RenderService renderService;
    private  final CredentialsService credentialsService;
    private ObservableList<Entity> credentials = FXCollections.observableArrayList();

    public DashboardService(VBox credentialsContainer,
                            VBox credentialContainer,
                            Label emailsCredentialCount,
                            Label notesCredentialCount,
                            Label creditCardCredentialCount,
                            Label totalCredentialsCount) {
        this.emailsCredentialCount = emailsCredentialCount;
        this.notesCredentialCount = notesCredentialCount;
        this.creditCardCredentialCount = creditCardCredentialCount;
        this.totalCredentialsCount = totalCredentialsCount;
        this.renderService = new RenderService(credentialsContainer, credentialContainer);
        this.credentialsService = new CredentialsService();

    }


    private void loadCredentials() throws SQLException {
        credentials.setAll(credentialsService.getCredentials());
    }

    private void refreshUI() {

        for (Entity credential : credentials) {
            credential.renderMany(renderService);
        }

        emailsCredentialCount.setText(
                String.valueOf(credentials.stream().filter(c -> c instanceof Email).count())
        );

        notesCredentialCount.setText(
                String.valueOf(credentials.stream().filter(c -> c instanceof Note).count())
        );

        creditCardCredentialCount.setText(
                String.valueOf(credentials.stream().filter(c -> c instanceof CreditCard).count())
        );

        totalCredentialsCount.setText(String.valueOf(credentials.size()));
    }


    public void initializeDashboard() {
        try {
            loadCredentials();
            refreshUI();
        } catch (NullPointerException e) {
            Helpers.Logger("No credential service found: " + e.getMessage(), "ERROR");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
