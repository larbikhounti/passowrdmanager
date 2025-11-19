package com.password.manager.credentials.controllers;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.enums.CredentialEnum;
import com.password.manager.credentials.factories.CredentialsFactory;
import com.password.manager.utils.Helpers;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CredentialController implements Initializable {

    @FXML
    private ComboBox<String> credentialTypeComboBox;

    @FXML
    private VBox formContainer;

    @FXML
    private Button saveButton;

    // Password fields
    private TextField urlField;
    private TextField emailField;
    private PasswordField passwordField;

    // Credit card fields
    private TextField cardNumberField;
    private TextField cardHolderField;
    private TextField expiryDateField;
    private TextField cvvField;

    // Note field
    private TextArea noteTextArea;
    private TextField noteTitleField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate ComboBox with credential types
        credentialTypeComboBox.setItems(FXCollections.observableArrayList(
                "Password", "CreditCard", "Note"
        ));

        // Set default selection
        credentialTypeComboBox.getSelectionModel().selectFirst();
        onCredentialTypeChanged(null);
    }

    @FXML
    public void onCredentialTypeChanged(ActionEvent event) {
        String selectedType = credentialTypeComboBox.getValue();

        if (selectedType == null) return;

        // Clear previous form
        formContainer.getChildren().clear();

        switch (selectedType) {
            case "Password":
                loadPasswordForm();
                break;
            case "CreditCard":
                loadCreditCardForm();
                break;
            case "Note":
                loadNoteForm();
                break;
        }
    }

    private void loadPasswordForm() {
        // URL Field
        Label urlLabel = new Label("URL");
        urlLabel.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 14px;");
        urlField = new TextField();
        urlField.setPromptText("https://example.com");
        urlField.setPrefWidth(520);
        urlField.setStyle("-fx-background-color: #1e1e1e; -fx-text-fill: white; " +
                "-fx-prompt-text-fill: #666666; -fx-background-radius: 5; -fx-padding: 10;");

        // email/Email Field
        Label emailLabel = new Label("email or Email");
        emailLabel.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 14px;");
        emailField = new TextField();
        emailField.setPromptText("user@example.com");
        emailField.setPrefWidth(520);
        emailField.setStyle("-fx-background-color: #1e1e1e; -fx-text-fill: white; " +
                "-fx-prompt-text-fill: #666666; -fx-background-radius: 5; -fx-padding: 10;");

        // Password Field
        Label passwordLabel = new Label("Password");
        passwordLabel.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 14px;");
        passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        passwordField.setPrefWidth(520);
        passwordField.setStyle("-fx-background-color: #1e1e1e; -fx-text-fill: white; " +
                "-fx-prompt-text-fill: #666666; -fx-background-radius: 5; -fx-padding: 10;");

        // Add to container
        VBox urlBox = new VBox(5, urlLabel, urlField);
        VBox emailBox = new VBox(5, emailLabel, emailField);
        VBox passwordBox = new VBox(5, passwordLabel, passwordField);

        formContainer.getChildren().addAll(urlBox, emailBox, passwordBox);
    }

    private void loadCreditCardForm() {
        // Card Number Field
        Label cardNumberLabel = new Label("Card Number");
        cardNumberLabel.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 14px;");
        cardNumberField = new TextField();
        cardNumberField.setPromptText("1234 5678 9012 3456");
        cardNumberField.setPrefWidth(520);
        cardNumberField.setStyle("-fx-background-color: #1e1e1e; -fx-text-fill: white; " +
                "-fx-prompt-text-fill: #666666; -fx-background-radius: 5; -fx-padding: 10;");

        // Card Holder Field
        Label cardHolderLabel = new Label("Card Holder Name");
        cardHolderLabel.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 14px;");
        cardHolderField = new TextField();
        cardHolderField.setPromptText("John Doe");
        cardHolderField.setPrefWidth(520);
        cardHolderField.setStyle("-fx-background-color: #1e1e1e; -fx-text-fill: white; " +
                "-fx-prompt-text-fill: #666666; -fx-background-radius: 5; -fx-padding: 10;");

        // Expiry Date Field
        Label expiryLabel = new Label("Expiry Date");
        expiryLabel.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 14px;");
        expiryDateField = new TextField();
        expiryDateField.setPromptText("MM/YY");
        expiryDateField.setPrefWidth(250);
        expiryDateField.setStyle("-fx-background-color: #1e1e1e; -fx-text-fill: white; " +
                "-fx-prompt-text-fill: #666666; -fx-background-radius: 5; -fx-padding: 10;");

        // CVV Field
        Label cvvLabel = new Label("CVV");
        cvvLabel.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 14px;");
        cvvField = new TextField();
        cvvField.setPromptText("123");
        cvvField.setPrefWidth(250);
        cvvField.setStyle("-fx-background-color: #1e1e1e; -fx-text-fill: white; " +
                "-fx-prompt-text-fill: #666666; -fx-background-radius: 5; -fx-padding: 10;");

        // Add to container
        VBox cardNumberBox = new VBox(5, cardNumberLabel, cardNumberField);
        VBox cardHolderBox = new VBox(5, cardHolderLabel, cardHolderField);
        VBox expiryBox = new VBox(5, expiryLabel, expiryDateField);
        VBox cvvBox = new VBox(5, cvvLabel, cvvField);

        formContainer.getChildren().addAll(cardNumberBox, cardHolderBox, expiryBox, cvvBox);
    }

    private void loadNoteForm() {
        // Note Label
        Label noteLabel = new Label("Note");
        noteLabel.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 14px;");

        Label titleLabel = new Label("Title");
        titleLabel.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 14px;");

        // Note Title Field
        noteTitleField = new TextField();
        noteTitleField.setPromptText("Enter note title...");
        noteTitleField.setPrefWidth(520);
        noteTitleField.setStyle("-fx-background-color: #1e1e1e; -fx-text-fill: white; " +
                "-fx-prompt-text-fill: #666666; -fx-background-radius: 5; -fx-padding: 10;");
        // Note TextArea
        noteTextArea = new TextArea();
        noteTextArea.setPromptText("Enter your note here...");
        noteTextArea.setPrefWidth(520);
        noteTextArea.setPrefHeight(200);
        noteTextArea.setWrapText(true);
        noteTextArea.setStyle("-fx-background-color: #1e1e1e; -fx-text-fill: white; " +
                "-fx-prompt-text-fill: #666666; -fx-background-radius: 5; " +
                "-fx-control-inner-background: #1e1e1e;");

        // Add to container
        VBox titleBox = new VBox(5, titleLabel, noteTitleField);
        VBox noteBox = new VBox(5, noteLabel, noteTextArea);

        formContainer.getChildren().add(titleBox);
        formContainer.getChildren().add(noteBox);
    }

    @FXML
    public void onSave(ActionEvent event) {
        String selectedType = credentialTypeComboBox.getValue();

        if (selectedType == null) {
            Helpers.showAlert("Error", "Please select a credential type", Alert.AlertType.ERROR);
            return;
        }

        switch (selectedType) {
            case "Password":
                saveEmail();
                break;
            case "CreditCard":
                saveCreditCard();
                break;
            case "Note":
                saveNote();
                break;
        }
    }

    private void saveEmail() {
        String url = urlField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (url.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Helpers.showAlert("Error", "All fields are required", Alert.AlertType.ERROR);
            return;
        }

        Entity emailEntity = new Entity.Builder()
                .setCredentialType(CredentialEnum.EMAIL)
                .setUrl(url)
                .setEmail(email)
                .setPassword(password)
                .build();

        try {
            Objects.requireNonNull(CredentialsFactory.getCredentialService(emailEntity).addCredential(emailEntity));
            Helpers.showAlert("Success", "Password saved successfully!", Alert.AlertType.INFORMATION);
        } catch (NullPointerException e) {
            Helpers.showAlert("Error", "Failed to save credential", Alert.AlertType.ERROR);
        }
    }

    private void saveCreditCard() {
        String cardNumber = cardNumberField.getText().trim();
        String cardHolder = cardHolderField.getText().trim();
        String expiryDate = expiryDateField.getText().trim();
        String cvv = cvvField.getText().trim();

        if (cardNumber.isEmpty() || cardHolder.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
            Helpers.showAlert("Error", "All fields are required", Alert.AlertType.ERROR);
            return;
        }
        Entity creditCardEntity = new Entity.Builder()
                .setCredentialType(CredentialEnum.CREDIT_CARD)
                .setNumber(cardNumber)
                .setHolderName(cardHolder)
                .setExpiry(expiryDate)
                .setCVV(cvv)
                .build();
        System.out.printf(" Saving credit card: %s, %s, %s, %s \n", creditCardEntity.getcreditCardNumber()
                , cardHolder, expiryDate, cvv);
        try {
            Objects.requireNonNull(CredentialsFactory.getCredentialService(creditCardEntity)).addCredential(creditCardEntity);
        } catch (NullPointerException e) {
            Helpers.showAlert("Error", "Failed to save credential", Alert.AlertType.ERROR);
            return;
        }
        Helpers.showAlert("Success", "Credit card saved successfully!", Alert.AlertType.INFORMATION);
    }

    private void saveNote() {
        String note = noteTextArea.getText().trim();
        String title = noteTitleField.getText().trim();
        Helpers.Logger("Saving note with title: " + title + " and content: " + note, "INFO");

        if (note.isEmpty() || title.isEmpty()) {
            Helpers.showAlert("Error", "Note  or title cannot be empty", Alert.AlertType.ERROR);
            return;
        }


        Entity noteEntity = new Entity.Builder()
                .setCredentialType(CredentialEnum.NOTE)
                .setNote(note)
                .setTitle(title)
                .build();

        try {
            Objects.requireNonNull(CredentialsFactory.getCredentialService(noteEntity)).addCredential(noteEntity);
            Helpers.showAlert("Success", "nOTE saved successfully!", Alert.AlertType.INFORMATION);
        } catch (NullPointerException e) {
            Helpers.showAlert("Error", "Failed to save credential", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onCancel(ActionEvent event) {
        try {
            Helpers.switchScene(event, "/com/password/manager/auth/dashboard_view.fxml");

        } catch (Exception e) {
            Helpers.Logger("Error closing window: " + e.getMessage(), "ERROR");
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}