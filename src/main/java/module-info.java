module com.password.manager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires java.logging;
    requires javafx.base;

    opens com.password.manager to javafx.fxml;
    exports com.password.manager;


    exports com.password.manager.auth.controllers;
    opens com.password.manager.auth.controllers to javafx.fxml;

    exports com.password.manager.credentials.controllers;
    opens com.password.manager.credentials.controllers to javafx.fxml;

    exports com.password.manager.dashboard.controllers;
    opens com.password.manager.dashboard.controllers to javafx.fxml;

}
