package JavaFX;

import compprog.sudoku.SudokuDifficulty;

import java.io.File;
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
import javafx.scene.control.*;
import javafx.stage.Stage;

public class StageController {

    @FXML
    private Stage stage;
    private Scene scene;
    @FXML
    private Parent root;
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
    @FXML
    private Label aboutLabel;
    @FXML
    private Label notFoundLabel;

    static SudokuDifficulty diff;
    static SudokuLanguage language = SudokuLanguage.ENGLISH;
    static boolean loadingGame = false;
    static Locale locale;
    static Locale listLocale;

    @FXML
    public void initialize() {
        switch (language) {
            case POLSKI -> {
                polskiItem.setSelected(true);
                listLocale = new Locale("pl", "PL");
            }
            case ENGLISH -> {
                englishItem.setSelected(true);
                listLocale = new Locale("en", "EN");
            }
        }
        ResourceBundle listBundle = ResourceBundle.getBundle("JavaFX.i18n.Bundle", listLocale);
        aboutLabel.setText(listBundle.getObject("aboutText") + "\n" + listBundle.getObject("authors") + " " + listBundle.getObject("author1") +
                ", " +listBundle.getObject("author2") + "\n" + listBundle.getObject("version") + " " + listBundle.getObject("versionNumber"));
    }

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
        root.setStyle("-fx-background-image: url('SudokuGameViewBG.png')");
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void loadingGame(ActionEvent event) throws IOException {
        File tmpDir = new File("./SudokuState.txt");
        if (tmpDir.exists()) {
            loadingGame = true;
            root = FXMLLoader.load(getClass().getResource("/SudokuGameView.fxml"), setBundle());
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            notFoundLabel.setVisible(true);
        }
    }

    @FXML
    public void closeNotFoundLabel() {
        notFoundLabel.setVisible(false);
    }

    @FXML
    public void showAboutLabel() {
        aboutLabel.setVisible(true);
    }

    @FXML
    public void closeAboutLabel() {
        aboutLabel.setVisible(false);
    }

    public void reloadStage() throws IOException {
        stage = (Stage) root.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/MainView.fxml"), setBundle());
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
    public void setLanguage() throws IOException {
        languageGroup.getSelectedToggle();

        if (englishItem.isSelected()) {
            language = SudokuLanguage.ENGLISH;
            reloadStage();
        }
        if (polskiItem.isSelected()) {
            language = SudokuLanguage.POLSKI;
            reloadStage();
        }
    }
}