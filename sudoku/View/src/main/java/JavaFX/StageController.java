package JavaFX;

import compprog.sudoku.SudokuDifficulty;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import compprog.sudoku.SudokuLanguage;
import exceptions.FileException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    @FXML
    private TextField filenameTextArea;
    static String filename;

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
    public void handleBtn1(ActionEvent event) throws Exception {
        try {
            root = FXMLLoader.load(getClass().getResource("/SudokuGameView.fxml"), setBundle());
            root.setStyle("-fx-background-image: url('SudokuGameViewBG.png')");
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            throw new Exception("Error occurred during changing stage!!", exception);
        }
    }

    @FXML void loadGame(ActionEvent event) throws Exception {
        try {
            filename = "./" + filenameTextArea.getText() + ".sudoku";
            File tmpDir = new File(filename);
            if (!tmpDir.exists()) {
                notFoundLabel.setVisible(true);
                throw new FileException();
            } else {
                launchLoadedGame(event);
            }
        } catch(IOException exception) {
            Logger logger = LoggerFactory.getLogger(StageController.class);
            logger.error("Cannot load game with given name!! - " + filename);
        } catch (FileException exception) {
            Logger logger = LoggerFactory.getLogger(StageController.class);
            logger.warn("There is no such file: " + filename);
        }
    }


    @FXML
    public void launchLoadedGame(ActionEvent event) throws Exception {
        try {
            loadingGame = true;
            root = FXMLLoader.load(getClass().getResource("/SudokuGameView.fxml"), setBundle());
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            throw new Exception("Error occurred during launching loaded game!!", exception);
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

    public void reloadStage() throws Exception {
        try {
            stage = (Stage) root.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/MainView.fxml"), setBundle());
            root.setStyle("-fx-background-image: url('mainViewBG.png')");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            throw new Exception("Error occurred during reloading stage!!", exception);
        }
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
    public void setLanguage() throws Exception {
        try {
            languageGroup.getSelectedToggle();
            if (englishItem.isSelected()) {
                language = SudokuLanguage.ENGLISH;
                reloadStage();
            }
            if (polskiItem.isSelected()) {
                language = SudokuLanguage.POLSKI;
                reloadStage();
            }
        } catch (Exception exception) {
            throw new Exception("Error occurred during setting language of the game!!", exception);
        }
    }
}