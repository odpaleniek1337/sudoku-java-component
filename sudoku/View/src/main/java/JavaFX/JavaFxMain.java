package JavaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class JavaFxMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Pane mainPne = FXMLLoader.load(getClass().getResource("/MainView.fxml"));
        Scene scene = new Scene(mainPne);
        stage.setScene(scene);
        stage.setTitle("sudoku");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
