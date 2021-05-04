module View {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires Model;

    opens JavaFX to javafx.fxml;
    exports JavaFX to javafx.graphics, javafx.fxml;
}