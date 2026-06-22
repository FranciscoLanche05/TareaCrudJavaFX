module com.crudjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.crudjavafx.app;
    opens com.crudjavafx.app to javafx.fxml;
    opens com.crudjavafx.controller to javafx.fxml;
    opens com.crudjavafx.modelo to javafx.base;
}