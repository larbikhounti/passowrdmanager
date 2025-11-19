package com.password.manager.dashboard.services;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.enums.CredentialEnum;
import com.password.manager.credentials.services.RenderService;
import com.password.manager.utils.Helpers;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class DashboardService {
    private final Label emailsCredentialCount;
    private final Label notesCredentialCount;
    private final Label creditCardCredentialCount;
    private final Label totalCredentialsCount;
    private final RenderService renderService;
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
    }

    public void initializeDashboard() {
        ArrayList<Entity> credentials = Entity.getCredentials();
        try {
            for (Entity credential : credentials) {
                switch (credential.getCredentialType()) {
                    case CredentialEnum.EMAIL ->
                            this.renderService.renderCredentialMany(credential.getId(), credential.getUrl(), credential.getEmail(), "\uD83D\uDD17");
                    case CredentialEnum.NOTE ->
                            this.renderService.renderCredentialMany(credential.getId(), credential.getTitle(), credential.getNote(), "\uD83D\uDCDD");
                    case CredentialEnum.CREDIT_CARD ->
                            this.renderService.renderCredentialMany(credential.getId(), credential.getcreditCardHolderName(), credential.getcreditCardNumber(), "\uD83D\uDCB3");
                }
            }
            emailsCredentialCount.setText(
                    String.valueOf(
                            credentials.stream()
                                    .filter(c -> c.getCredentialType() == CredentialEnum.EMAIL)
                                    .count()
                    )
            );
            notesCredentialCount.setText(
                    String.valueOf(
                            credentials.stream()
                                    .filter(c -> c.getCredentialType() == CredentialEnum.NOTE)
                                    .count()
                    )
            );

            creditCardCredentialCount.setText(
                    String.valueOf(
                            credentials.stream()
                                    .filter(c -> c.getCredentialType() == CredentialEnum.CREDIT_CARD)
                                    .count()
                    )
            );

            totalCredentialsCount.setText(String.valueOf(credentials.size()));

        } catch (NullPointerException e) {
            Helpers.Logger("No credential service found: " + e.getMessage(), "ERROR");
        }
    }

}
