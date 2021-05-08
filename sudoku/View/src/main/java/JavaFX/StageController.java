package JavaFX;

import compprog.sudoku.SudokuDifficulty;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import compprog.sudoku.SudokuLanguage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StageController {

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Pane mainPane;
    @FXML
    private ToggleGroup languageGroup;
    @FXML
    private RadioMenuItem englishItem;
    @FXML
    private RadioMenuItem polskiItem;
    @FXML
    private ToggleGroup diffGroup;
    @FXML
    private ToggleButton easyBtn;
    @FXML
    private ToggleButton mediumBtn;
    @FXML
    private ToggleButton hardBtn;

    static SudokuDifficulty diff;
    static SudokuLanguage language = SudokuLanguage.ENGLISH;
    static boolean loadingGame = false;
    static Locale locale;

    public static ResourceBundle setBundle() {
        switch (language) {
            case ENGLISH -> locale = new Locale("en_UK");
            case POLSKI -> locale = new Locale("pl_PL");
        }
        ResourceBundle bundle = ResourceBundle.getBundle("SudokuResource", locale);
        return bundle;
    }

    @FXML
    public void handleBtn1(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/SudokuGameView.fxml"), setBundle());
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void loadingGame(ActionEvent event) throws IOException {
        loadingGame = true;
        root = FXMLLoader.load(getClass().getResource("/SudokuGameView.fxml"), setBundle());
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void reloadStage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MainView.fxml"), setBundle());
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void setDifficultyBtn() {
        diffGroup.getSelectedToggle();

        if (easyBtn.isSelected()) {
            diff = SudokuDifficulty.EASY;
        }
        if (mediumBtn.isSelected()) {
            diff = SudokuDifficulty.MEDIUM;
        }
        if (hardBtn.isSelected()) {
            diff = SudokuDifficulty.HARD;
        }
    }

    @FXML
    public void setLanguage() {
        languageGroup.getSelectedToggle();

        if (englishItem.isSelected()) {
            language = SudokuLanguage.ENGLISH;
        }
        if (polskiItem.isSelected()) {
            language = SudokuLanguage.POLSKI;
        }
    }

    public boolean getLoadState() {
        return loadingGame;
    }
}