package com.password.manager.dashboard.services;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.enums.CredentialEnum;
import com.password.manager.credentials.factories.CredentialsFactory;
import com.password.manager.utils.Helpers;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Objects;

public class DashboardService {
    private final VBox credentialsContainer;
    private final Label emailsCredentialCount;
    private final Label notesCredentialCount;
    private final Label creditCardCredentialCount;
    private final Label totalCredentialsCount;
    public DashboardService(VBox credentialsContainer, Label emailsCredentialCount, Label notesCredentialCount, Label creditCardCredentialCount, Label totalCredentialsCount) {
        this.credentialsContainer = credentialsContainer;
        this.emailsCredentialCount = emailsCredentialCount;
        this.notesCredentialCount = notesCredentialCount;
        this.creditCardCredentialCount = creditCardCredentialCount;
        this.totalCredentialsCount = totalCredentialsCount;

    }

    public void initializeDashboard() {
        ArrayList<Entity> credentials = Entity.getCredentials();
        try {
            for (Entity credential : credentials) {
                switch (credential.getCredentialType()) {
                    case CredentialEnum.EMAIL ->
                            addCredential(credential.getUrl(), credential.getEmail(), "\uD83D\uDD17");
                    case CredentialEnum.NOTE ->
                            addCredential(credential.getTitle(), credential.getNote(), "\uD83D\uDCDD");
                    case CredentialEnum.CREDIT_CARD ->
                            addCredential(credential.getcreditCardHolderName(), credential.getcreditCardNumber(), "\uD83D\uDCB3");
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

    public void addCredential(String _title, String _subtitle, String _Icon) {

        Helpers.Logger("Adding credential: site=" + _title + ", username=" + _subtitle, "INFO");

        HBox item = new HBox();
        item.setAlignment(Pos.CENTER_LEFT);
        item.setSpacing(15);
        item.setStyle("-fx-background-color: #252525; -fx-padding: 12; -fx-cursor: hand;");

        Label icon = new Label(_Icon);
        icon.setStyle("-fx-font-size: 24px;");

        VBox textBox = new VBox(2);

        Label title = new Label(_title);
        title.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");

        Label subtitle = new Label(_subtitle);
        subtitle.setStyle("-fx-text-fill: #888888; -fx-font-size: 12px;");

        textBox.getChildren().addAll(title, subtitle);

        item.getChildren().addAll(icon, textBox);


        // Add item
        this.credentialsContainer.getChildren().add(item);
        // Add separator
        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #1e1e1e;");
        this.credentialsContainer.getChildren().add(sep);
    }

}
