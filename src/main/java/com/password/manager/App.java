package com.password.manager;

import com.password.manager.auth.passwordHandler.PasswordStorage;
import com.password.manager.credentials.seeds.CredentialSeeder;
import com.password.manager.utils.DbConnector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        if (!DbConnector.checkConnection()) {
            System.out.println("Unable to connect to the database. Exiting application.");
            System.exit(1);
        }else {
            System.out.println("Database connection established successfully.");
            CredentialSeeder.seedCredentialTypes();
        }
        if(PasswordStorage.isMasterPasswordSet()){
            scene = new Scene(loadFXML("auth/authentication_view"), 640, 480);
        }else{
            scene = new Scene(loadFXML("auth/setPassword_view"), 640, 480);
        }
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}