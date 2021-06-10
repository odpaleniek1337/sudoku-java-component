package gui;

import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class JavaFxMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ResourceBundle bundle = StageController.setBundle();
        Pane mainPne = FXMLLoader.load(getClass().getResource("/MainView.fxml"), bundle);
        mainPne.setStyle("-fx-background-image: url('mainViewBG.png')");
        Scene scene = new Scene(mainPne);
        stage.setScene(scene);
        stage.setTitle("Sudoku Game");
        stage.getIcons().add(new Image("sudokuGame_icon.png"));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
