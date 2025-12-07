package com.password.manager.dashboard.services;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.entities.CreditCard;
import com.password.manager.credentials.entities.Email;
import com.password.manager.credentials.entities.Note;
import com.password.manager.credentials.enums.CredentialEnum;
import com.password.manager.credentials.services.RenderService;
import com.password.manager.dashboard.repositories.DashboardRepository;
import com.password.manager.utils.Helpers;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.util.ArrayList;

public class DashboardService {
    private final Label emailsCredentialCount;
    private final Label notesCredentialCount;
    private final Label creditCardCredentialCount;
    private final Label totalCredentialsCount;
    private final RenderService renderService;
    private DashboardRepository dashboardRepository;
    private ArrayList<Entity> credentials;
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
        this.dashboardRepository = new DashboardRepository();

    }

    public void initializeDashboard() {
        try {
            this.credentials = this.dashboardRepository.getAllCredentials();
            for (Entity credential : this.credentials) {
                credential.renderMany(renderService);
            }
            emailsCredentialCount.setText(
                    String.valueOf(
                            this.credentials.stream()
                                    .filter(c -> c instanceof Email)
                                    .count()
                    )
            );
            notesCredentialCount.setText(
                    String.valueOf(
                            this.credentials.stream()
                                    .filter(c -> c instanceof Note)
                                    .count()
                    )
            );

            creditCardCredentialCount.setText(
                    String.valueOf(
                            this.credentials.stream()
                                    .filter(c -> c instanceof CreditCard)
                                    .count()
                    )
            );

            totalCredentialsCount.setText(String.valueOf(this.credentials.size()));

        } catch (NullPointerException e) {
            Helpers.Logger("No credential service found: " + e.getMessage(), "ERROR");
        }
    }

}
