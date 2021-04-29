package JavaFX;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class JavaFxMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane mainPne = FXMLLoader.load(getClass().getResource("/MainView.fxml"));
        Scene scene = new Scene(mainPne);
        stage.setScene(scene);
        stage.setTitle("sudoku");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
