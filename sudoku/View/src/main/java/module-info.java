module View {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires Model;
    requires slf4j.log4j12;
    requires slf4j.api;

    exports JavaFX to javafx.graphics, javafx.fxml;
}