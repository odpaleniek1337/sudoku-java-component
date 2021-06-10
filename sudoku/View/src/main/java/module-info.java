module View {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires Model;
    requires org.slf4j;

    opens gui to javafx.fxml;
    exports gui to javafx.graphics, javafx.fxml;
}