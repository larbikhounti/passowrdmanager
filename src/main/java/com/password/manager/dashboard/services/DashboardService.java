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
import java.util.List;

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
                            Label totalCredentialsCount,
                            CredentialsService credentialsService) {
        this.emailsCredentialCount = emailsCredentialCount;
        this.notesCredentialCount = notesCredentialCount;
        this.creditCardCredentialCount = creditCardCredentialCount;
        this.totalCredentialsCount = totalCredentialsCount;
        this.credentialsService = credentialsService;

        this.renderService = new RenderService(credentialsContainer, credentialContainer, this, credentialsService);

    }


    private void loadCredentials() throws SQLException {
        credentials.setAll(credentialsService.getCredentials());
    }

    private void refreshUI() {
        // Clear both containers before re-rendering
        renderService.clearCredentialsContainer();
        renderService.clearCredentialContainer();

        // Filter out credentials with null/empty required fields
        List<Entity> validCredentials = credentials.stream()
                .filter(this::hasValidData)
                .toList();

        // dkhel ga3 credentials f render service w 3ayet l render many 3la kol wa7ed fihom
        for (Entity credential : validCredentials) {
            credential.renderMany(renderService);
        }

        // update l labe b 3adad l credentials b kol type (use filtered list)
        emailsCredentialCount.setText(
                String.valueOf(validCredentials.stream().filter(c -> c instanceof Email).count())
        );

        notesCredentialCount.setText(
                String.valueOf(validCredentials.stream().filter(c -> c instanceof Note).count())
        );

        creditCardCredentialCount.setText(
                String.valueOf(validCredentials.stream().filter(c -> c instanceof CreditCard).count())
        );

        totalCredentialsCount.setText(String.valueOf(validCredentials.size()));
    }

    private boolean hasValidData(Entity credential) {
        if (credential instanceof Email email) {
            return email.getEmail() != null && !email.getEmail().isEmpty();
        } else if (credential instanceof Note note) {
            return note.getTitle() != null && !note.getTitle().isEmpty();
        } else if (credential instanceof CreditCard card) {
            return card.getCreditCardNumber() != null && !card.getCreditCardNumber().isEmpty();
        }
        return false;
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
