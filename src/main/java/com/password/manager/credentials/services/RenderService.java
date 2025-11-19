package com.password.manager.credentials.services;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.factories.CredentialsFactory;
import com.password.manager.utils.Helpers;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

public class RenderService {


    private final VBox credentialsContainer;
    private final VBox credentialContainer;
    public RenderService(VBox credentialsContainer, VBox credentialContainer) {
        this.credentialsContainer = credentialsContainer;
        this.credentialContainer = credentialContainer;
    }

    public void renderCredentialMany(int id, String title, String subtitle, String iconText) {

        Helpers.Logger("Adding credential: site=" + title + ", username=" + subtitle, "INFO");

        HBox item = new HBox();
        item.setAlignment(Pos.CENTER_LEFT);
        item.setSpacing(15);
        item.setStyle("-fx-background-color: #252525; -fx-padding: 12; -fx-cursor: hand;");
        item.setId(String.valueOf(id));

        item.setOnMouseClicked(e -> {
            Entity credential = Entity.getCredentials().stream()
                    .filter(c -> c.getId() == id)
                    .findFirst()
                    .orElse(null);

            if (credential != null) {
                renderCredentialsOne(credential);
            } else {
                Helpers.Logger("Credential not found: " + id, "WARN");
            }
        });

        Label icon = new Label(iconText);
        icon.setStyle("-fx-font-size: 24px;");

        VBox textBox = new VBox(2);
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");

        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.setStyle("-fx-text-fill: #888888; -fx-font-size: 12px;");

        textBox.getChildren().addAll(titleLabel, subtitleLabel);
        item.getChildren().addAll(icon, textBox);

        this.credentialsContainer.getChildren().add(item);
        this.credentialsContainer.getChildren().add(new Separator());
    }

    public void renderCredentialsOne(Entity credential) {
        Helpers.Logger("Rendering credential details for id: " + credential.getId(), "INFO");
        // we need to render the credential details in the credentialContainer but first we need to clear it
        // and we need to show the in textFields the data from the credential
        // we have 3 types of credentials: email, note and credit card
        this.credentialContainer.getChildren().clear();
        switch (credential.getCredentialType()) {
            case EMAIL -> renderEmailCredential(credential);
            case NOTE -> renderNoteCredential(credential);
            case CREDIT_CARD -> renderCreditCardCredential(credential);
            default -> Helpers.Logger("Unknown credential type: " + credential.getCredentialType(), "ERROR");
        }

        Button updateButton = new Button("Update Credential");
        updateButton.setStyle("-fx-background-color: #3a3a3a; -fx-text-fill: white; -fx-padding: 10 20; -fx-cursor: hand;");
        updateButton.setOnAction(e -> {
           // Objects.requireNonNull(CredentialsFactory.getCredentialService(credential)).editCredential(credential.getId() ,credential);
        });

        this.credentialContainer.getChildren().add(updateButton);

    }

    private void renderEmailCredential(Entity credential) {
        Helpers.Logger("Rendering email credential", "INFO");

        VBox container = new VBox(15);
        container.setStyle("-fx-padding: 20;");

        // URL field
        Label urlLabel = new Label("URL");
        urlLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
        TextField urlField = new TextField(credential.getUrl());
        urlField.setStyle("-fx-background-color: #252525; -fx-text-fill: white; -fx-padding: 10;");
        urlField.setEditable(true);

        // Email field
        Label emailLabel = new Label("Email");
        emailLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
        TextField emailField = new TextField(credential.getEmail());
        emailField.setStyle("-fx-background-color: #252525; -fx-text-fill: white; -fx-padding: 10;");
        emailField.setEditable(true);

        // Password field with toggle
        Label passwordLabel = new Label("Password");
        passwordLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

        PasswordField passwordField = new PasswordField();
        passwordField.setText(credential.getPassword());
        passwordField.setStyle("-fx-background-color: #252525; -fx-text-fill: white; -fx-padding: 10;");
        passwordField.setEditable(true);

        TextField passwordTextField = new TextField(credential.getPassword());
        passwordTextField.setStyle("-fx-background-color: #252525; -fx-text-fill: white; -fx-padding: 10;");
        passwordTextField.setEditable(true);
        passwordTextField.setVisible(false);
        passwordTextField.setManaged(false);

        // Toggle visibility on click
        passwordField.setOnMouseClicked(e -> {
            passwordTextField.setText(passwordField.getText());
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            passwordTextField.setVisible(true);
            passwordTextField.setManaged(true);
        });

        passwordTextField.setOnMouseClicked(e -> {
            passwordField.setText(passwordTextField.getText());
            passwordTextField.setVisible(false);
            passwordTextField.setManaged(false);
            passwordField.setVisible(true);
            passwordField.setManaged(true);
        });




        container.getChildren().addAll(
                urlLabel, urlField,
                emailLabel, emailField,
                passwordLabel, passwordField, passwordTextField
        );

        this.credentialContainer.getChildren().add(container);

    }
    private void renderNoteCredential(Entity credential) {
        Helpers.Logger("Rendering note credential", "INFO");

        VBox container = new VBox(3);

        // Title field
        Label titleLabel = new Label("Title");
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
        TextField titleField = new TextField(credential.getTitle());
        titleField.setStyle("-fx-background-color: #252525; -fx-text-fill: white;");
        titleField.setEditable(true);

        // Note field
        Label noteLabel = new Label("Note");
        TextArea  noteTextArea = new TextArea();
        noteTextArea.setText(credential.getNote());
        noteTextArea.setPrefWidth(520);
        noteTextArea.setPrefHeight(200);
        noteTextArea.setWrapText(true);
        noteTextArea.setStyle("-fx-background-color: #1e1e1e; -fx-text-fill: white; " +
                "-fx-prompt-text-fill: #666666; -fx-background-radius: 5; " +
                "-fx-control-inner-background: #1e1e1e;");
        noteTextArea.setEditable(true);

        container.getChildren().addAll(
                titleLabel, titleField,
                noteLabel, noteTextArea
        );

        this.credentialContainer.getChildren().add(container);
    }

    private void renderCreditCardCredential(Entity credential) {
        Helpers.Logger("Rendering credit card credential", "INFO");

        VBox container = new VBox(15);
        container.setStyle("-fx-padding: 20;");

        // Card Holder Name field
        Label holderLabel = new Label("Cardholder Name");
        holderLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
        TextField holderField = new TextField(credential.getcreditCardHolderName());
        holderField.setStyle("-fx-background-color: #252525; -fx-text-fill: white; -fx-padding: 10;");
        holderField.setEditable(true);

        // Card Number field
        Label numberLabel = new Label("Card Number");
        numberLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
        TextField numberField = new TextField(credential.getcreditCardNumber());
        numberField.setStyle("-fx-background-color: #252525; -fx-text-fill: white; -fx-padding: 10;");
        numberField.setEditable(true);

        // Expiry Date field
        Label expiryLabel = new Label("Expiry Date");
        expiryLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
        TextField expiryField = new TextField(credential.getcreditCardExpiry());
        expiryField.setStyle("-fx-background-color: #252525; -fx-text-fill: white; -fx-padding: 10;");
        expiryField.setEditable(true);

        // CVV field with toggle
        Label cvvLabel = new Label("CVV");
        cvvLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

        PasswordField cvvField = new PasswordField();
        cvvField.setText(credential.getcreditCardCVV());
        cvvField.setStyle("-fx-background-color: #252525; -fx-text-fill: white; -fx-padding: 10;");
        cvvField.setEditable(true);

        TextField cvvTextField = new TextField(credential.getcreditCardCVV());
        cvvTextField.setStyle("-fx-background-color: #252525; -fx-text-fill: white; -fx-padding: 10;");
        cvvTextField.setEditable(true);
        cvvTextField.setVisible(false);
        cvvTextField.setManaged(false);

        // Toggle CVV visibility on click
        cvvField.setOnMouseClicked(e -> {
            cvvTextField.setText(cvvField.getText());
            cvvField.setVisible(false);
            cvvField.setManaged(false);
            cvvTextField.setVisible(true);
            cvvTextField.setManaged(true);
        });

        cvvTextField.setOnMouseClicked(e -> {
            cvvField.setText(cvvTextField.getText());
            cvvTextField.setVisible(false);
            cvvTextField.setManaged(false);
            cvvField.setVisible(true);
            cvvField.setManaged(true);
        });

        container.getChildren().addAll(
                holderLabel, holderField,
                numberLabel, numberField,
                expiryLabel, expiryField,
                cvvLabel, cvvField, cvvTextField
        );

        this.credentialContainer.getChildren().add(container);
    }

}