module com.alessi {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.alessi to javafx.fxml;
    opens com.alessi.controllers to javafx.fxml;

    exports com.alessi;
}