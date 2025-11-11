module com.password.manager {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.password.manager to javafx.fxml;
    exports com.password.manager;
    exports com.password.manager.auth.controllers;
    opens com.password.manager.auth.controllers to javafx.fxml;
}
