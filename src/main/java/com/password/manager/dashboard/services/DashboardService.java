package com.password.manager.dashboard.services;

import com.password.manager.credentials.base.Entity;
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

    public DashboardService(VBox credentialsContainer, Label emailsCredentialCount) {
        this.credentialsContainer = credentialsContainer;
        this.emailsCredentialCount = emailsCredentialCount;

    }

    public void initializeDashboard() {
        ArrayList<Entity> credentials;
        try {
            credentials = Objects.requireNonNull(CredentialsFactory.getCredentialService("EMAIL")).getAllCredentials();
            for (Entity credential : credentials) {
                addCredential(credential.getUrl(), credential.getEmail());
            }
            emailsCredentialCount.setText(String.valueOf(credentials.size()));
        }catch (NullPointerException e){
            Helpers.Logger("No credential service found: " + e.getMessage(), "ERROR");
        }
    }

    public void addCredential(String site, String username) {

        Helpers.Logger("Adding credential: site=" + site + ", username=" + username, "INFO");

        HBox item = new HBox();
        item.setAlignment(Pos.CENTER_LEFT);
        item.setSpacing(15);
        item.setStyle("-fx-background-color: #252525; -fx-padding: 12; -fx-cursor: hand;");

        Label icon = new Label("\uD83D\uDD17");
        icon.setStyle("-fx-font-size: 24px;");

        VBox textBox = new VBox(2);

        Label title = new Label(site);
        title.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");

        Label subtitle = new Label(username);
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
